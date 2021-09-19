package top.misec.task;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import top.misec.api.ApiList;
import top.misec.api.OftenApi;
import top.misec.config.ConfigLoader;
import top.misec.utils.HttpUtils;

import java.util.Random;

import static top.misec.task.DailyTask.getDailyTaskStatus;
import static top.misec.task.TaskInfoHolder.STATUS_CODE_STR;
import static top.misec.task.TaskInfoHolder.getVideoId;

/**
 * 观看分享视频.
 *
 * @author @JunzhouLiu @Kurenai
 * @since 2020-11-22 5:13
 */
@Slf4j
public class VideoWatch implements Task {

    @Override
    public void run() {
        JsonObject dailyTaskStatus = getDailyTaskStatus();
        String bvid = getVideoId.getRegionRankingVideoBvid();
        if (!dailyTaskStatus.get("watch").getAsBoolean()) {
            watchVideo(bvid);
        } else {
            log.info("本日观看视频任务已经完成了，不需要再观看视频了");
        }

        if (!dailyTaskStatus.get("share").getAsBoolean()) {
            dailyAvShare(bvid);
        } else {
            log.info("本日分享视频任务已经完成了，不需要再分享视频了");
        }
    }

    @Override
    public String getName() {
        return "观看分享视频";
    }

    public void watchVideo(String bvid) {
        int playedTime = new Random().nextInt(90) + 1;
        String postBody = "bvid=" + bvid
                + "&played_time=" + playedTime;
        JsonObject resultJson = HttpUtils.doPost(ApiList.VIDEO_HEARTBEAT, postBody);
        String videoTitle = OftenApi.getVideoTitle(bvid);
        int responseCode = resultJson.get(STATUS_CODE_STR).getAsInt();
        if (responseCode == 0) {
            log.info("视频: {}播放成功,已观看到第{}秒", videoTitle, playedTime);
        } else {
            log.debug("视频: {}播放失败,原因: {}", videoTitle, resultJson.get("message").getAsString());
        }
    }

    /**
     * @param bvid 要分享的视频bvid.
     */
    public void dailyAvShare(String bvid) {
        String requestBody = "bvid=" + bvid + "&csrf=" + ConfigLoader.helperConfig.getBiliVerify().getBiliJct();
        JsonObject result = HttpUtils.doPost((ApiList.AV_SHARE), requestBody);

        String videoTitle = OftenApi.getVideoTitle(bvid);

        if (result.get(STATUS_CODE_STR).getAsInt() == 0) {
            log.info("视频: {} 分享成功", videoTitle);
        } else {
            log.debug("视频分享失败，原因: {}", result.get("message").getAsString());
            log.debug("如果是csrf校验失败请检查BILI_JCT参数是否正确或者失效");
        }
    }
}
