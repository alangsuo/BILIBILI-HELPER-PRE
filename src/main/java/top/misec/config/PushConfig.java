package top.misec.config;

import lombok.Data;

/**
 * @author JunzhouLiu
 */
@Data
public class PushConfig {

    private String SCT_KEY;
    private String TG_BOT_TOKEN;
    private String TG_USER_ID;

}
