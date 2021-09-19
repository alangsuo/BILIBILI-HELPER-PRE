package top.misec.push.impl;

import top.misec.push.model.PushMetaInfo;

/**
 * TG推送 .自定义URL的
 *
 * @author itning
 * @since 2021/3/22 17:55
 */
public class TelegramCustomUrlPush extends TelegramPush {

    @Override
    protected String generatePushUrl(PushMetaInfo metaInfo) {
        return metaInfo.getToken() + "/sendMessage";
    }
}
