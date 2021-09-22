package top.misec.push.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import top.misec.api.ApiList;
import top.misec.push.AbstractPush;
import top.misec.push.model.PushMetaInfo;
import top.misec.utils.GsonUtils;
import top.misec.utils.HttpUtils;

import java.io.Serializable;

/**
 * 企业微信应用PUSH
 *
 * @author itning
 * @since 2021/9/22 14:41
 */
@Slf4j
public class WeComAppPush extends AbstractPush {

    @Override
    protected String generatePushUrl(PushMetaInfo metaInfo) {

        JsonObject jsonObject = HttpUtils.doGet(ApiList.WECOM_APP_PUSH_GET_TOKEN + "?corpid=" + metaInfo.getToken() + "&corpsecret=" + metaInfo.getSecret(), proxyClient);
        if (null == jsonObject) {
            throw new RuntimeException("获取企业微信凭证失败，JsonObject Is Null");
        }
        JsonElement element = jsonObject.get("access_token");
        if (null == element) {
            throw new RuntimeException("获取企业微信凭证失败，access_token Is Null");
        }
        String accessToken = element.getAsString();
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("获取企业微信凭证失败，access_token Is Blank");
        }

        return ApiList.WECOM_APP_PUSH + "?access_token=" + accessToken;
    }

    @Override
    protected String generatePushBody(PushMetaInfo metaInfo, String content) {
        WeComMessageSendRequest request = new WeComMessageSendRequest();
        request.setToUser(metaInfo.getToUser());
        request.setMsgType("text");
        request.setAgentId(metaInfo.getAgentId());
        WeComMessageSendRequest.Text text = new WeComMessageSendRequest.Text();
        text.setContent(content);
        request.setText(text);
        return GsonUtils.toJson(request);
    }

    @Override
    protected boolean checkPushStatus(JsonObject jsonObject) {

        if (jsonObject == null) {
            return false;
        }

        JsonElement errcode = jsonObject.get("errcode");
        JsonElement errmsg = jsonObject.get("errmsg");
        if (null == errcode || null == errmsg) {
            return false;
        }

        return errcode.getAsInt() == 0 && "ok".equals(errmsg.getAsString());
    }

    @Data
    public static class WeComMessageSendRequest implements Serializable {
        /**
         * 指定接收消息的成员，成员ID列表（多个接收者用‘|’分隔，最多支持1000个）。
         * 特殊情况：指定为”@all”，则向该企业应用的全部成员发送
         */
        @SerializedName("touser")
        private String toUser;
        /**
         * 指定接收消息的部门，部门ID列表，多个接收者用‘|’分隔，最多支持100个。
         * 当touser为”@all”时忽略本参数
         */
        @SerializedName("toparty")
        private String toParty;
        /**
         * 指定接收消息的标签，标签ID列表，多个接收者用‘|’分隔，最多支持100个。
         * 当touser为”@all”时忽略本参数
         */
        @SerializedName("totag")
        private String toTag;
        /**
         * 消息类型
         */
        @SerializedName("msgtype")
        private String msgType;
        /**
         * 企业应用的id，整型。企业内部开发，可在应用的设置页面查看；第三方服务商，可通过接口 获取企业授权信息 获取该参数值
         */
        @SerializedName("agentid")
        private Integer agentId;
        /**
         * 文本内容
         */
        @SerializedName("text")
        private Text text;
        /**
         * 表示是否是保密消息，0表示否，1表示是，默认0
         */
        @SerializedName("safe")
        private Integer safe;
        /**
         * 表示是否开启id转译，0表示否，1表示是，默认0。仅第三方应用需要用到，企业自建应用可以忽略。
         */
        @SerializedName("enable_id_trans")
        private Integer enableIdTrans;
        /**
         * 表示是否开启重复消息检查，0表示否，1表示是，默认0
         */
        @SerializedName("enable_duplicate_check")
        private Integer enableDuplicateCheck;
        /**
         * 表示是否重复消息检查的时间间隔，默认1800s，最大不超过4小时
         */
        @SerializedName("duplicate_check_interval")
        private Integer duplicateCheckInterval;

        @Data
        public static class Text implements Serializable {
            /**
             * 消息内容，最长不超过2048个字节
             */
            @SerializedName("content")
            private String content;
        }
    }
}
