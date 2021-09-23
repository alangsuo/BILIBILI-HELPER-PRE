package top.misec.task;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import top.misec.api.ApiList;
import top.misec.utils.GsonUtils;
import top.misec.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

import static top.misec.task.TaskInfoHolder.STATUS_CODE_STR;

/**
 * 漫画阅读.
 *
 * @author Junzhou Liu
 * @since 2021/1/13 17:50
 */
@Slf4j
public class MangaRead implements Task {

    @Override
    public void run() {
        Map<String, String> map = new HashMap<>(4);
        map.put("device", "pc");
        map.put("platform", "web");
        map.put("comic_id", "27355");
        map.put("ep_id", "381662");

        JsonObject result = HttpUtils.doPost(ApiList.MANGA_READ, GsonUtils.toJson(map));
        int code = result.get(STATUS_CODE_STR).getAsInt();

        if (code == 0) {
            log.info("本日漫画自动阅读1章节成功！，阅读漫画为：堀与宫村");
        } else {
            log.warn("阅读失败,错误信息为\n```json\n{}\n```", result);
        }

    }

    @Override
    public String getName() {
        return "每日漫画阅读";
    }
}
