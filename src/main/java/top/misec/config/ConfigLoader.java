package top.misec.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import top.misec.utils.GsonUtils;
import top.misec.utils.HttpUtil;
import top.misec.utils.LoadFileResource;

/**
 * Auto-generated: 2020-10-13 17:10:40.
 *
 * @author Junzhou Liu
 * @since 2020/10/13 17:11
 */
@Log4j2
@Data
public class ConfigLoader {
    public static HelperConfig helperConfig;
    private static String defaultHelperConfig;

    static {
        defaultHelperConfig = LoadFileResource.loadJsonFromAsset("config.json");
        helperConfig = buildHelperConfig(defaultHelperConfig);
    }

    /**
     * 云函数初始化配置 .
     *
     * @param json config json
     */
    public static void configInit(String json, boolean isSCF) {
        helperConfig = buildHelperConfig(json);
        HttpUtil.setUserAgent(helperConfig.getTaskConfig().getUserAgent());
        log.info(helperConfig.getPushConfig().toString());
    }

    /**
     * 优先从jar包同级目录读取.
     */
    public static void configInit(String filename) {
        String customConfig = LoadFileResource.loadConfigFile(filename);
        if (customConfig != null) {
            mergeConfig(GsonUtils.fromJson(customConfig, HelperConfig.class));
            log.info("读取外部自定义配置文件成功,若部分配置项不存在则会采用默认配置,合并后的配置为\n{}", helperConfig.toString());
        }
        validationConfig();
        helperConfig.initCookiesMap();
        HttpUtil.setUserAgent(helperConfig.getTaskConfig().getUserAgent());
    }

    /**
     * 使用自定义文件时校验相关值.
     */
    private static void validationConfig() {

        if (helperConfig.getBiliCookies().length() < 1) {
            log.info("未在config.json中配置ck");
            return;
        }

        TaskConfig taskConfig = helperConfig.getTaskConfig();

        taskConfig.setChargeDay(taskConfig.getChargeDay() > 28 || taskConfig.getChargeDay() < 1 ? 28 : taskConfig.getChargeDay())
                .setTaskIntervalTime(taskConfig.getTaskIntervalTime() <= 0 ? 1 : taskConfig.getTaskIntervalTime())
                .setPredictNumberOfCoins(taskConfig.getPredictNumberOfCoins() > 10 ? 10 : taskConfig.getPredictNumberOfCoins() <= 0 ? 1 : taskConfig.getPredictNumberOfCoins());

        helperConfig.setTaskConfig(taskConfig);
    }

    /**
     * override config .
     *
     * @param sourceConfig sourceConfig
     */
    private static void mergeConfig(HelperConfig sourceConfig) {
        BeanUtil.copyProperties(sourceConfig, helperConfig, new CopyOptions().setIgnoreNullValue(true));
    }

    private static HelperConfig buildHelperConfig(String configJson) {
        return GsonUtils.fromJson(configJson, HelperConfig.class);
    }
}