package top.misec.task;

import lombok.extern.log4j.Log4j2;
import top.misec.config.ConfigLoader;
import top.misec.config.PushConfig;
import top.misec.utils.LoadFileResource;

/**
 * 消息推送 .
 *
 * @author @JunzhouLiu @Kurenai
 * @since 2020/10/21 17:39
 */

@Log4j2
public class ServerPush {

    public static void doServerPush() {
        PushConfig.PushInfo pushInfo = ConfigLoader.helperConfig.getPushConfig().getTarget();

        if (null != pushInfo) {
            pushInfo.getTarget().doPush(pushInfo.getMetaInfo(), LoadFileResource.loadFile("/tmp/bili-helper.log"));
        } else {
            log.info("未配置正确的ftKey和chatId,本次执行将不推送日志");
        }
    }
}
