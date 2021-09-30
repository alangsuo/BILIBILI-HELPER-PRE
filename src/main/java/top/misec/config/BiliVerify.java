package top.misec.config;

import java.util.Arrays;
import java.util.HashMap;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * verify .
 *
 * @author JunzhouLiu
 */
@Slf4j
@ToString
@Data
public class BiliVerify {

    private HashMap<String, Object> cookieMap = new HashMap<>();
    private String biliCookies;

    public void initCookiesMap() {
        String[] sourceArray = biliCookies.split(";");
        for (String s : sourceArray) {
            String[] target = s.split("=");
            if (Arrays.stream(target).count() == 2) {
                cookieMap.put(target[0].trim(), target[1].trim());
            } else {
                cookieMap.put(target[0].trim(), "");
            }
        }
        //log.info("init cookies successfully");
    }

    public String getBiliJct() {
        return cookieMap.get(Constant.BILI_JCT).toString();
    }

    public String getDedeUserId() {
        return cookieMap.get(Constant.BILI_USER_ID).toString();
    }
}
