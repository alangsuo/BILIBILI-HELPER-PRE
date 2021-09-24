package top.misec.task;

import static top.misec.task.TaskInfoHolder.STATUS_CODE_STR;
import static top.misec.task.TaskInfoHolder.queryVipStatusType;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.google.gson.JsonObject;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import top.misec.api.ApiList;
import top.misec.api.OftenApi;
import top.misec.utils.GsonUtils;
import top.misec.utils.HttpUtils;

/**
 * 漫画权益领取.
 *
 * @author @JunzhouLiu @Kurenai @happy888888
 * @since 2020-11-22 5:48
 */
@Slf4j
@Data
public class GetVipPrivilege implements Task {

    /**
     * 权益号，由https://api.bilibili.com/x/vip/privilege/my.
     * 得到权益号数组，取值范围为数组中的整数.
     * 为方便直接取1，为领取漫读劵，暂时不取其他的值.
     */
    private int reasonId = 1;

    @Override
    public void run() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        int day = cal.get(Calendar.DATE);

        /*
           根据userInfo.getVipStatus() ,如果是1 ，会员有效，0会员失效。
           @JunzhouLiu: fixed query_vipStatusType()现在可以查询会员状态，以及会员类型了 2020-10-15
         */
        int vipType = queryVipStatusType();

        if (vipType == 0) {
            log.info("非大会员，跳过领取大会员权益");
            return;
        }

        if (vipType == 1 && day == 1) {
            log.info("开始领取大会员漫画权益");
            Map<String, Object> map = new HashMap<>(1);
            map.put("reason_id", reasonId);
            JsonObject jsonObject = HttpUtils.doPost(ApiList.MANGA_GET_VIP_REWARD, GsonUtils.toJson(map));
            if (jsonObject.get(STATUS_CODE_STR).getAsInt() == 0) {
                /*
                  @happy888888:好像也可以getAsString或,getAsShort
                  @JunzhouLiu:Int比较好判断
                 */
                int num = jsonObject.get("data").getAsJsonObject().get("amount").getAsInt();
                log.info("大会员成功领取{}张漫读劵", num);
            } else {
                log.info("大会员领取漫读劵失败，原因为:{}", jsonObject.get("msg").getAsString());
            }
        } else {
            log.info("本日非领取大会员漫画执行日期");
        }

        if (day == 1 || day % 3 == 0) {
            if (vipType == 2) {
                log.info("开始领取年度大会员权益");
                OftenApi.getVipPrivilege(1);
                OftenApi.getVipPrivilege(2);
            }
        } else {
            log.info("本日非领取年度大会员权益执行日期");
        }
    }

    @Override
    public String getName() {
        return "漫画权益领取";
    }
}
