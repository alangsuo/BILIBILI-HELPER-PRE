package top.misec.utils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import top.misec.config.ConfigLoader;


/**
 * http utils.
 *
 * @author Junzhou Liu
 * @since 2020/10/11 4:03
 */

@Slf4j
@Data
public class HttpUtils {

    private static String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63 Safari/537.36 Edg/93.0.961.38";
    public static final MediaType JSON = MediaType.parse("application/json");
    public static OkHttpClient client;

    static {
        client = new OkHttpClient.Builder()
                // 设置完整的请求过程超时时间。该参数计算的是整个请求过程的时间：从解析DNS、与Server建立连接、发送请求、Server响应处理到读取请求结果。同时，如果请求中包含重定向和失败重连，这两个过程执行时间也包含在callTimeOut计时时间内。
                .callTimeout(20, TimeUnit.SECONDS)
                // 设置ping信号发送时间间隔，该选项一般用于维持Websocket/Http2长连接，发送心跳包。默认值为0表示禁用心跳机制。
                .pingInterval(2, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                // 是否允许OkHttp自动执行失败重连，默认为true。当设置为true时，okhttp会在以下几种可能的请求失败的情况下恢复连接并重新请求：1.IP地址不可达；2.过久的池化连接；3.代理服务器不可达。
                .retryOnConnectionFailure(true)
                .connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES))
                .build();
        client.dispatcher().setMaxRequests(10);
        client.dispatcher().setMaxRequestsPerHost(8);
    }

    public static JsonObject doPost(String url, JsonObject jsonObject) {
        return doPost(url, jsonObject.toString());
    }

    /**
     * post body support json str
     *
     * @param url         url
     * @param requestBody body
     * @return json object
     */
    public static JsonObject doPost(String url, String requestBody) {
        return doPost(url, requestBody, null);
    }

    public static JsonObject doPost(String url, String requestBody, Map<String, String> headers) {

        RequestBody body;
        if (requestBody.startsWith("{")) {
            //java的正则表达式咋写......
            body = RequestBody.create(requestBody, JSON);
        } else {
            FormBody.Builder builder = new FormBody.Builder();
            String[] itemArray = requestBody.split("&");
            for (String item : itemArray) {
                String[] keyValueArray = item.split("=");
                builder.add(keyValueArray[0], keyValueArray[1]);
            }
            body = builder.build();
        }

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .addHeader("cookie", ConfigLoader.helperConfig.getBiliVerify().getBiliCookies())
                .addHeader("User-Agent", userAgent)
                .addHeader("Connection", "keep-alive")
                .post(body);

        if (null != headers && !headers.isEmpty()) {
            headers.forEach(requestBuilder::addHeader);
        } else {
            requestBuilder.addHeader("Referer", "https://www.bilibili.com/");
        }

        Request request = requestBuilder.build();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (null == responseBody) {
                return null;
            }
            String result = responseBody.string();
            return new Gson().fromJson(result, JsonObject.class);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }


    public static JsonObject doGet(String url) {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .addHeader("Connection", "keep-alive")
                .addHeader("User-Agent", userAgent)
                .addHeader("cookie", ConfigLoader.helperConfig.getBiliVerify().getBiliCookies())
                .get();

        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (null == responseBody) {
                return null;
            }
            String result = responseBody.string();
            return new Gson().fromJson(result, JsonObject.class);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    public static void setUserAgent(String userAgent) {
        if (StringUtils.isNotBlank(userAgent)) {
            HttpUtils.userAgent = userAgent;
        }
    }
}
