package top.misec;

import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import top.misec.config.ConfigLoader;
import top.misec.task.DailyTask;
import top.misec.task.ServerPush;
import top.misec.utils.VersionInfo;

import java.io.File;

/**
 * 入口类 .
 *
 * @author JunzhouLiu
 * @since 2020/10/11 2:29
 */

@Slf4j
public class BiliMain {

    public static void main(String[] args) {

        VersionInfo.printVersionInfo();
        //每日任务65经验

        if (args.length > 0) {
            log.info("使用自定义目录的配置文件");
            ConfigLoader.configInit(args[0]);
        } else {
            log.info("使用同目录下的config.json文件");
            String currentPath = System.getProperty("user.dir") + File.separator + "config.json";
            ConfigLoader.configInit(currentPath);
        }

        if (!Boolean.TRUE.equals(ConfigLoader.helperConfig.getTaskConfig().getSkipDailyTask())) {
            DailyTask dailyTask = new DailyTask();
            dailyTask.doDailyTask();
        } else {
            log.info("已开启了跳过本日任务，（不会发起任何网络请求），如果需要取消跳过，请将skipDailyTask值改为false");
            ServerPush.doServerPush();
        }
    }

    /**
     * 用于腾讯云函数触发.
     */
    public static void mainHandler() {
        String config = System.getProperty("config");
        if (null == config) {
            log.error("云函数配置的config参数为空。");
            return;
        }

        try {
            ConfigLoader.serverlessConfigInit(config);
        } catch (JsonSyntaxException e) {
            log.error("配置json格式有误，请检查是否是合法的json串", e);
            return;
        }


        VersionInfo.printVersionInfo();
        //每日任务65经验

        if (!Boolean.TRUE.equals(ConfigLoader.helperConfig.getTaskConfig().getSkipDailyTask())) {
            DailyTask dailyTask = new DailyTask();
            dailyTask.doDailyTask();
        } else {
            log.info("已开启了跳过本日任务，（不会发起任何网络请求），如果需要取消跳过，请将skipDailyTask值改为false");
            ServerPush.doServerPush();
        }
    }

}
