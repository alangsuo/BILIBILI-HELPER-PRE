package top.misec.config;


import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * HelperConfig.
 *
 * @author JunzhouLiu
 */
@Data
@Log4j2
public class HelperConfig {
    private TaskConfig taskConfig;
    private PushConfig pushConfig;
    private BiliVerify biliVerify;
}
