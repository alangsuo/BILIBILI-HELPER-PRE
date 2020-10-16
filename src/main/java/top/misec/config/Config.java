package top.misec.config;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * Auto-generated: 2020-10-13 17:10:40
 *
 * @author Junzhou Liu
 * @create 2020/10/13 17:11
 */


public class Config {

    static Logger logger = (Logger) LogManager.getLogger(Config.class.getName());

    private int numberOfCoins;
    private int selectLike;
    private int watchAndShare;
    private int monthEndAutoCharge;

    public int getMonthEndAutoCharge() {
        return monthEndAutoCharge;
    }

    public void setMonthEndAutoCharge(int monthEndAutoCharge) {
        this.monthEndAutoCharge = monthEndAutoCharge;
    }

    private static Config CONFIG = new Config();

    public static Config getInstance() {
        return CONFIG;
    }

    public Config() {
    }

    public int isWatchShare() {
        return watchAndShare;
    }

    public void setWatchAndShare(int watchAndShare) {
        this.watchAndShare = watchAndShare;
    }

    public void setNumberOfCoins(int numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }

    public int getNumberOfCoins() {
        return numberOfCoins;
    }

    public void setSelectLike(int selectLike) {
        this.selectLike = selectLike;
    }

    public int getSelectLike() {
        return selectLike;
    }

    @Override
    public String toString() {
        return "Config{" +
                "numberOfCoins=" + numberOfCoins +
                ", select_like=" + selectLike +
                ", watch_share=" + watchAndShare +
                '}';
    }

    public String outputConfig() {
        String outputConfig = "----您设置的每日投币数量为: ";
        outputConfig += numberOfCoins;

        if (selectLike == 0) {
            outputConfig += " 投币时不点赞 ";
        } else {
            outputConfig += " 投币时点赞 ";
        }

        if (watchAndShare == 1) {
            outputConfig += " 观看时分享----";
        } else {
            outputConfig += " 观看时不分享----";
        }

        return outputConfig;
    }

    /**
     * 读取配置文件 src/main/resources/config.json
     */
    public void configInit() {
        try {
            FileInputStream in = new FileInputStream("src/main/resources/config.json");
            Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
            Config.CONFIG = new Gson().fromJson(reader, Config.class);
            logger.info("----Init ConfigFile Successful----");
            logger.debug(Config.getInstance().outputConfig());
        } catch (FileNotFoundException e) {
            logger.debug(e);
            e.printStackTrace();
        }
    }

}