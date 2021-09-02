package top.misec.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @author Junzhou Liu
 * @create 2020/11/21 15:22
 */
@Log4j2
@Data
public class VersionInfo {
    private static String releaseVersion = "";
    private static String releaseDate = "";
    private static String projectRepo = "https://github.com/JunzhouLiu/BILIBILI-HELPER-PRE";
    private static String releaseInfo = "";

    public static void initInfo() {
        String release = LoadFileResource.loadJsonFromAsset("release.json");
        JsonObject jsonObject = new JsonParser().parse(release).getAsJsonObject();
        releaseVersion = jsonObject.get("tag_main").getAsString();
        releaseDate = jsonObject.get("release_date").getAsString();
        releaseInfo = LoadFileResource.loadJsonFromAsset("release.info");
    }

    public static void printVersionInfo() {
        initInfo();
        log.debug("-----版本信息-----");
        log.debug("当前版本: " + releaseVersion);
        log.debug("版本更新内容: " + releaseInfo);
        log.debug("最后更新日期: " + releaseDate);
        log.debug("项目开源地址: " + projectRepo);
        log.debug("-----版本信息-----\n");
    }
}
