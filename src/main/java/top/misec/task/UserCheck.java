package top.misec.task;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import top.misec.api.ApiList;
import top.misec.pojo.userinfobean.UserData;
import top.misec.utils.HelpUtil;
import top.misec.utils.HttpUtils;

import static top.misec.task.TaskInfoHolder.STATUS_CODE_STR;
import static top.misec.task.TaskInfoHolder.userInfo;

/**
 * 登录检查.
 *
 * @author @JunzhouLiu @Kurenai
 * @since 2020-11-22 4:57
 */
@Slf4j
public class UserCheck implements Task {

    @Override
    public void run() {
        JsonObject userJson = HttpUtils.doGet(ApiList.LOGIN);
        if (userJson == null) {
            log.info("用户信息请求失败，如果是412错误，请在config.json中更换UA，412问题仅影响用户信息确认，不影响任务");
        } else {
            userJson = HttpUtils.doGet(ApiList.LOGIN);
            //判断Cookies是否有效
            if (userJson.get(STATUS_CODE_STR).getAsInt() == 0
                    && userJson.get("data").getAsJsonObject().get("isLogin").getAsBoolean()) {
                userInfo = new Gson().fromJson(userJson
                        .getAsJsonObject("data"), UserData.class);
                log.info("Cookies有效，登录成功");
            } else {
                log.debug(String.valueOf(userJson));
                log.warn("Cookies可能失效了,请仔细检查配置中的DEDEUSERID SESSDATA BILI_JCT三项的值是否正确、过期");
            }

            log.info("用户名称: {}", HelpUtil.userNameEncode(userInfo.getUname()));
            log.info("硬币余额: {}", userInfo.getMoney());
        }

    }

    @Override
    public String getName() {
        return "登录检查";
    }
}
