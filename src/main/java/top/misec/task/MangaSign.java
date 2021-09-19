package top.misec.task;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import top.misec.api.ApiList;
import top.misec.config.ConfigLoader;
import top.misec.utils.HttpUtils;

/**
 * 漫画签到.
 *
 * @author @JunzhouLiu @Kurenai
 * @since 2020-11-22 5:22
 */

@Slf4j
public class MangaSign implements Task {
    @Override
    public void run() {

        String platform = ConfigLoader.helperConfig.getTaskConfig().getDevicePlatform();
        String requestBody = "platform=" + platform;
        JsonObject result = HttpUtils.doPost(ApiList.MANGA, requestBody);

        if (result == null) {
            log.info("哔哩哔哩漫画已经签到过了");
        } else {
            log.info("完成漫画签到");
        }
    }

    @Override
    public String getName() {
        return "漫画签到";
    }
}
