package top.misec.task;

import lombok.extern.log4j.Log4j2;
import top.misec.config.ConfigLoader;
import top.misec.login.ServerVerify;
import top.misec.login.Verify;
import top.misec.utils.VersionInfo;

/**
 * util test.
 *
 * @author Junzhou Liu
 * @create 2021/1/15 23:16
 */
@Log4j2
public class UnitTest {

    public static void main(String[] args) {

        VersionInfo.printVersionInfo();
        //每日任务65经验
        //初始化配置
        log.info(args[0]);
        ConfigLoader.configInit(args[0]);
        new UserCheck().run();
        new GiveGift().run();
        ServerPush.doServerPush();


    }
}
