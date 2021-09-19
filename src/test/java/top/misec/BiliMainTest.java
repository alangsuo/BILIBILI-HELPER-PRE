package top.misec;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonSyntaxException;

import lombok.extern.slf4j.Slf4j;
import top.misec.config.ConfigLoader;
import top.misec.task.DailyTask;
import top.misec.task.ServerPush;
import top.misec.utils.ReadFileUtils;
import top.misec.utils.VersionInfo;

@Slf4j
public class BiliMainTest {

    @Test
    public void testMainHandler() {
        String config = System.getProperty("config");
        //serverless 本地测试
        config = ReadFileUtils.readFile("/tmp/config.json");
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