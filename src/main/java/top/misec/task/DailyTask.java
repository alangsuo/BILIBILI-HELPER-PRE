package top.misec.task;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import top.misec.api.ApiList;
import top.misec.utils.HttpUtils;
import top.misec.utils.SleepTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static top.misec.task.TaskInfoHolder.STATUS_CODE_STR;
import static top.misec.task.TaskInfoHolder.calculateUpgradeDays;

/**
 * 日常任务 .
 *
 * @author @JunzhouLiu @Kurenai
 * @since 2020/10/11 20:44
 */
@Slf4j
public class DailyTask {

    private final List<Task> dailyTasks;

    public DailyTask() {
        dailyTasks = new ArrayList<>();
        dailyTasks.add(new VideoWatch());
        dailyTasks.add(new MangaSign());
        dailyTasks.add(new CoinAdd());
        dailyTasks.add(new Silver2Coin());
        dailyTasks.add(new LiveChecking());
        dailyTasks.add(new GiveGift());
        dailyTasks.add(new ChargeMe());
        dailyTasks.add(new GetVipPrivilege());
        dailyTasks.add(new MatchGame());
        dailyTasks.add(new MangaRead());
        Collections.shuffle(dailyTasks);
        dailyTasks.add(0, new UserCheck());
        dailyTasks.add(1, new CoinLogs());
    }

    /**
     * get daily task status.
     *
     * @return jsonObject 返回status对象
     * @value {"login":true,"watch":true,"coins":50,"share":true,"email":true,"tel":true,"safe_question":true,"identify_card":false}
     * @author @srcrs
     */
    public static JsonObject getDailyTaskStatus() {
        JsonObject jsonObject = HttpUtils.doGet(ApiList.REWARD);
        int responseCode = jsonObject.get(STATUS_CODE_STR).getAsInt();
        if (responseCode == 0) {
            log.info("请求本日任务完成状态成功");
            return jsonObject.get("data").getAsJsonObject();
        } else {
            log.warn(jsonObject.get("message").getAsString());
            return HttpUtils.doGet(ApiList.REWARD).get("data").getAsJsonObject();
            //偶发性请求失败，再请求一次。
        }
    }

    public void doDailyTask() {
        try {
            dailyTasks.forEach(task -> {
                log.info("------{}开始------", task.getName());
                try {
                    task.run();
                } catch (Exception e) {
                    log.error("任务[{}]运行失败", task.getName(), e);
                }
                log.info("------{}结束------\n", task.getName());
                new SleepTime().sleepDefault();
            });
            log.info("本日任务已全部执行完毕");
            calculateUpgradeDays();
        } catch (Exception e) {
            log.error("任务运行异常", e);
        } finally {
            ServerPush.doServerPush();
        }
    }
}

