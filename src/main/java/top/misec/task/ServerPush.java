package top.misec.task;

import lombok.extern.slf4j.Slf4j;
import top.misec.config.ConfigLoader;
import top.misec.config.Constant;
import top.misec.config.PushConfig;
import top.misec.utils.ReadFileUtils;

/**
 * 消息推送 .
 *
 * @author @JunzhouLiu @Kurenai
 * @since 2020/10/21 17:39
 */

@Slf4j
public class ServerPush {

    public static void doServerPush() {
        PushConfig.PushInfo pushInfo = ConfigLoader.helperConfig.getPushConfig().getPushInfo();

        if (null != pushInfo) {
            pushInfo.getTarget().doPush(pushInfo.getMetaInfo(), ReadFileUtils.readFile(Constant.LOG_FILE_PATH));
        } else {
            log.info("未配置正确的ftKey和chatId,本次执行将不推送日志");
        }
    }
}
