package top.misec.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * version info.
 *
 * @author Junzhou Liu
 * @since 2020/11/21 15:22
 */
@Slf4j
@Data
public class VersionInfo {
    private static String releaseVersion = "";
    private static String releaseDate = "";
    private static String projectRepo = "https://github.com/JunzhouLiu/BILIBILI-HELPER-PRE";
    private static String releaseInfo = "";

    public static void initInfo() {
        String release = ReadFileUtils.loadJsonFromAsset("release.json");
        JsonObject jsonObject = new Gson().fromJson(release, JsonObject.class);
        releaseVersion = jsonObject.get("tag_main").getAsString();
        releaseDate = jsonObject.get("release_date").getAsString();
        releaseInfo = ReadFileUtils.loadJsonFromAsset("release.info");
    }

    public static void printVersionInfo() {
        initInfo();
        JsonObject jsonObject = HttpUtils.doGet("https://api.github.com/repos/JunzhouLiu/BILIBILI-HELPER-PRE/releases/latest");
        log.info("-----版本信息-----");
        log.info("当前版本: {}", releaseVersion);
        try {
            log.info("最新版本为: {}", jsonObject.get("tag_name").getAsString().replaceAll("v", ""));
            log.info("-----最新版本更新内容-----\n{}", jsonObject.get("body").getAsString().replaceAll("\"", ""));
            log.info("最近更新时间: {}", HelpUtil.utcTime(jsonObject.get("created_at").getAsString()));
            log.info("项目开源地址: {}", projectRepo);
            log.info("-----版本信息-----\n");
        } catch (Exception e) {
            log.warn("网络问题，未请求到新版本", e);
        }

    }
}
