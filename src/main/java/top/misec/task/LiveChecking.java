package top.misec.task;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import top.misec.api.ApiList;
import top.misec.utils.HttpUtils;

import static top.misec.task.TaskInfoHolder.STATUS_CODE_STR;

/**
 * 直播签到.
 *
 * @author @JunzhouLiu @Kurenai
 * @since 2020-11-22 5:42
 */
@Slf4j
public class LiveChecking implements Task {
    @Override
    public void run() {
        JsonObject liveCheckInResponse = HttpUtils.doGet(ApiList.LIVE_CHECKING);
        int code = liveCheckInResponse.get(STATUS_CODE_STR).getAsInt();
        if (code == 0) {
            JsonObject data = liveCheckInResponse.get("data").getAsJsonObject();
            log.info("直播签到成功，本次签到获得{},{}", data.get("text").getAsString(), data.get("specialText").getAsString());
        } else {
            String message = liveCheckInResponse.get("message").getAsString();
            log.warn("直播签到失败: {}", message);
        }
    }

    @Override
    public String getName() {
        return "直播签到";
    }
}
