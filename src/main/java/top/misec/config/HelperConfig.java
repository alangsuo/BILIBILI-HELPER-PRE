package top.misec.config;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * HelperConfig.
 *
 * @author JunzhouLiu
 */
@Data
@Slf4j
public class HelperConfig {
    private TaskConfig taskConfig;
    private PushConfig pushConfig;
    private BiliVerify biliVerify;

    @Override
    public String toString() {
        return "HelperConfig{" +
                "taskConfig=" + taskConfig +
                ", pushConfig=" + "敏感配置不输出" +
                ", biliVerify=" + "敏感配置不输出" +
                '}';
    }
}
