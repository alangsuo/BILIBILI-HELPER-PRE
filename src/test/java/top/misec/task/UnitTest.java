package top.misec.task;

import java.io.File;

import lombok.extern.log4j.Log4j2;
import top.misec.config.ConfigLoader;
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

        if (args.length > 0) {
            log.info("使用自定义目录的配置文件");
            ConfigLoader.configInit(args[0]);
        } else {
            log.info("使用同目录下的config.json文件");
            String currentPath = System.getProperty("user.dir") + File.separator + "config.json";
            ConfigLoader.configInit(currentPath);
        }
        new UserCheck().run();

        new CoinAdd().run();



    }
}
