package top.misec.task;

import lombok.extern.log4j.Log4j2;
import top.misec.login.ServerVerify;
import top.misec.push.Push;
import top.misec.push.PushHelper;
import top.misec.push.impl.DingTalkPush;
import top.misec.push.impl.PushPlusPush;
import top.misec.push.impl.ServerChanTurboPush;
import top.misec.push.impl.TelegramPush;
import top.misec.push.impl.WeiXinPush;
import top.misec.push.model.PushMetaInfo;
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
        PushMetaInfo.PushMetaInfoBuilder builder = PushMetaInfo.builder().numberOfRetries(3);
        Push push = null;
        String ftKey = ServerVerify.getFtKey();
        String chatId = ServerVerify.getChatId();
        if (ftKey != null && chatId == null) {
            builder = builder.token(ftKey);
            // 临时解决方案
            if (ftKey.startsWith("https://oapi.dingtalk.com")) {
                push = new DingTalkPush();
                log.info("本次执行推送日志到钉钉");
            } else if (ftKey.startsWith("SCT")) {
                push = new ServerChanTurboPush();
                log.info("本次执行推送日志到Server酱Turbo版本");
            } else if (ftKey.length() == PushPlusPush.PUSH_PLUS_CHANNEL_TOKEN_DEFAULT_LENGTH) {
                push = new PushPlusPush();
                log.info("本次执行推送日志到Push Plus");
            } else if (ftKey.length() == WeiXinPush.WEIXIN_CHANNEL_TOKEN_DEFAULT_LENGTH) {
                push = new WeiXinPush();
                log.info("本次执行推送日志到企业微信");
            }
        } else if (ftKey != null) {
            builder = builder.token(ftKey).chatId(chatId);
            push = new TelegramPush();
            log.info("本次执行推送日志到Telegram");
        }

        if (null != push) {
            PushHelper.push(push, builder.build(), LoadFileResource.loadFile("/tmp/bili-helper.log"));
        } else {
            log.info("未配置正确的ftKey和chatId,本次执行将不推送日志");
        }
    }
}
