package top.misec.task;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import top.misec.api.ApiList;
import top.misec.api.OftenApi;
import top.misec.config.ConfigLoader;
import top.misec.utils.HelpUtil;
import top.misec.utils.HttpUtils;

import java.util.Calendar;
import java.util.TimeZone;

import static top.misec.task.TaskInfoHolder.*;

/**
 * 给自己充电.
 * <p/>
 * 月底自动给自己充电。仅充会到期的B币券，低于2的时候不会充.
 *
 * @author @JunzhouLiu @Kurenai
 * @since 2020-11-22 5:43
 */
@Slf4j
public class ChargeMe implements Task {

    @Override
    public void run() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        int day = cal.get(Calendar.DATE);
        //被充电用户的userID
        String userId = ConfigLoader.helperConfig.getTaskConfig().getChargeForLove();

        String userName = OftenApi.queryUserNameByUid(userId);

        //B币券余额
        double couponBalance;
        //大会员类型
        int vipType = queryVipStatusType();

        if (vipType == 0 || vipType == 1) {
            log.info("普通会员和月度大会员每月不赠送B币券，所以没法给自己充电哦");
            return;
        }

        if (!Boolean.TRUE.equals(ConfigLoader.helperConfig.getTaskConfig().getMonthEndAutoCharge())) {
            log.info("未开启月底给自己充电功能");
            return;
        }

        if ("0".equals(userId) || "".equals(userId)) {
            log.info("充电对象uid配置错误，请参考最新的文档");
            return;
        }

        if (day < ConfigLoader.helperConfig.getTaskConfig().getChargeDay()) {
            log.info("今天是本月的第: {}天，还没到充电日子呢", day);
            return;
        }


        log.info("月底自动充电对象是: {}", HelpUtil.userNameEncode(userName));

        if (userInfo != null) {
            couponBalance = userInfo.getWallet().getCoupon_balance();
        } else {
            JsonObject queryJson = HttpUtils.doGet(ApiList.CHARGE_QUERY + "?mid=" + userId);
            couponBalance = queryJson.getAsJsonObject("data").getAsJsonObject("bp_wallet").get("coupon_balance").getAsDouble();
        }

        /*
          判断条件 是月底&&是年大会员&&b币券余额大于2&&配置项允许自动充电.
         */
        if (day == ConfigLoader.helperConfig.getTaskConfig().getChargeDay() && couponBalance >= 2) {
            String requestBody = "bp_num=" + couponBalance
                    + "&is_bp_remains_prior=true"
                    + "&up_mid=" + userId
                    + "&otype=up"
                    + "&oid=" + userId
                    + "&csrf=" + ConfigLoader.helperConfig.getBiliVerify().getBiliJct();

            JsonObject jsonObject = HttpUtils.doPost(ApiList.AUTO_CHARGE, requestBody);

            int resultCode = jsonObject.get(STATUS_CODE_STR).getAsInt();
            if (resultCode == 0) {
                JsonObject dataJson = jsonObject.get("data").getAsJsonObject();
                int statusCode = dataJson.get("status").getAsInt();
                if (statusCode == 4) {
                    log.info("月底了，自动充电成功啦，送的B币券没有浪费哦");
                    log.info("本次充值使用了: {}个B币券", couponBalance);
                    //获取充电留言token
                    String orderNo = dataJson.get("order_no").getAsString();
                    chargeComments(orderNo);
                } else {
                    log.warn("充电失败了啊 原因: {}", jsonObject);
                }

            } else {
                log.warn("充电失败了啊 原因: {}", jsonObject);
            }
        }
    }

    private void chargeComments(String token) {

        String requestBody = "order_id=" + token
                + "&message=" + "期待up主的新作品！"
                + "&csrf=" + ConfigLoader.helperConfig.getBiliVerify().getBiliJct();
        JsonObject jsonObject = HttpUtils.doPost(ApiList.CHARGE_COMMENT, requestBody);

        if (jsonObject.get(STATUS_CODE_STR).getAsInt() == 0) {
            log.info("充电留言成功");
        } else {
            log.warn(jsonObject.get("message").getAsString());
        }
    }

    @Override
    public String getName() {
        return "大会员月底B币券充电和月初大会员权益领取";
    }
}
