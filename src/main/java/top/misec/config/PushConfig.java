package top.misec.config;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import lombok.Getter;
import top.misec.push.Push;
import top.misec.push.impl.DingTalkPush;
import top.misec.push.impl.PushPlusPush;
import top.misec.push.impl.ServerChanPush;
import top.misec.push.impl.ServerChanTurboPush;
import top.misec.push.impl.TelegramPush;
import top.misec.push.impl.WeComPush;
import top.misec.push.model.PushMetaInfo;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * pushconfig ddd
 *
 * @author JunzhouLiu itning
 */
@SuppressWarnings("all")
@Data
public class PushConfig {

    /**
     * 电报
     */
    private String TG_BOT_TOKEN;

    /**
     * 电报
     */
    private String TG_USER_ID;

    /**
     * 钉钉
     */
    private String DING_TALK_URL;

    /**
     * push plus++
     */
    private String PUSH_PLUS_TOKEN;

    /**
     * Server酱
     */
    private String SCT_KEY;

    /**
     * Server酱
     */
    private String SC_KEY;

    /**
     * 企业微信，群消息非应用消息
     */
    private String WE_COM_TOKEN;

    /**
     * 推送代理 代表高级协议（如 HTTP 或 FTP）的代理。
     */
    private String PROXY_HTTP_HOST;

    /**
     * 推送代理 代表 SOCKS（V4 或 V5）代理。
     */
    private String PROXY_SOCKET_HOST;

    /**
     * 推送代理 代表 端口
     */
    private Integer PROXY_PORT;

    public PushInfo getPushInfo() {
        if (StringUtils.isNoneBlank(TG_BOT_TOKEN, TG_USER_ID)) {
            return new PushInfo(new TelegramPush(), TG_BOT_TOKEN, TG_USER_ID);
        } else if (StringUtils.isNotBlank(DING_TALK_URL)) {
            return new PushInfo(new DingTalkPush(), DING_TALK_URL);
        } else if (StringUtils.isNotBlank(PUSH_PLUS_TOKEN)) {
            return new PushInfo(new PushPlusPush(), PUSH_PLUS_TOKEN);
        } else if (StringUtils.isNotBlank(SCT_KEY)) {
            return new PushInfo(new ServerChanTurboPush(), SCT_KEY);
        } else if (StringUtils.isNotBlank(WE_COM_TOKEN)) {
            return new PushInfo(new WeComPush(), WE_COM_TOKEN);
        } else if (StringUtils.isNotBlank(SC_KEY)) {
            return new PushInfo(new ServerChanPush(), SC_KEY);
        } else {
            return null;
        }
    }

    public Proxy getProxy() {
        if (null == PROXY_PORT || PROXY_PORT.equals(0)) {
            return Proxy.NO_PROXY;
        }

        if (StringUtils.isNotBlank(PROXY_HTTP_HOST)) {
            InetSocketAddress address = new InetSocketAddress(PROXY_HTTP_HOST, PROXY_PORT);
            return new Proxy(Proxy.Type.HTTP, address);
        }

        if (StringUtils.isNotBlank(PROXY_SOCKET_HOST)) {
            InetSocketAddress address = new InetSocketAddress(PROXY_HTTP_HOST, PROXY_PORT);
            return new Proxy(Proxy.Type.SOCKS, address);
        }

        return Proxy.NO_PROXY;
    }

    @Getter
    public static class PushInfo {
        private final Push target;
        private final PushMetaInfo metaInfo;

        public PushInfo(Push target, String token) {
            this.target = target;
            this.metaInfo = PushMetaInfo.builder().numberOfRetries(3).token(token).build();
        }

        public PushInfo(Push target, String token, String chatId) {
            this.target = target;
            this.metaInfo = PushMetaInfo.builder().numberOfRetries(3).token(token).chatId(chatId).build();
        }
    }
}
