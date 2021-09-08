package top.misec.config;

import java.util.HashMap;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @author JunzhouLiu
 */

@Data
@Log4j2
public class HelperConfig {
    private TaskConfig taskConfig;
    private HashMap<String, Object> cookieMap = new HashMap<>();
    private String biliCookies;
    private PushConfig pushConfig;

    public void initCookiesMap() {
        String[] sourceArray = biliCookies.split(";");
        for (String s : sourceArray) {
            String[] target = s.split("=");
            cookieMap.put(target[0].trim(), target[1].trim());
        }
        log.info("inti cookies map ");
    }

    public String getBiliJct() {
        return cookieMap.get("bili_jct").toString();
    }

    public String getDedeUserId() {
        return cookieMap.get("DedeUserID").toString();
    }
}
