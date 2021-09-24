package top.misec.task;


import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;
import top.misec.api.ApiList;
import top.misec.api.OftenApi;
import top.misec.config.ConfigLoader;
import top.misec.utils.HttpUtils;
import top.misec.utils.SleepTime;

/**
 * 赛事预测.
 *
 * @author junzhou
 */
@Slf4j
public class MatchGame implements Task {

    @Override
    public void run() {

        if (!ConfigLoader.helperConfig.getTaskConfig().getMatchGame()) {
            log.info("赛事预测未开启");
            return;
        }

        double currentCoin = OftenApi.getCoinBalance();

        if (currentCoin < ConfigLoader.helperConfig.getTaskConfig().getMinimumNumberOfCoins()) {
            log.info("{}个硬币都没有，参加什么预测呢？任务结束", ConfigLoader.helperConfig.getTaskConfig().getMinimumNumberOfCoins());
            return;
        }
        JsonObject resultJson = queryContestQuestion(getTime(), 1, 50);
        JsonObject jsonObject = resultJson.get("data").getAsJsonObject();
        if (resultJson.get("code").getAsInt() == 0) {
            JsonArray list = jsonObject.get("list").getAsJsonArray();
            JsonObject pageinfo = jsonObject.get("page").getAsJsonObject();
            if (pageinfo.get("total").getAsInt() == 0) {
                log.info("今日无赛事或者本日赛事已经截止预测");
                return;
            }
            if (list != null) {
                int coinNumber = ConfigLoader.helperConfig.getTaskConfig().getPredictNumberOfCoins();
                int contestId;
                String contestName;
                int questionId;
                String questionTitle;
                int teamId;
                String teamName;
                //   int seasonId;
                String seasonName;
                for (JsonElement listinfo : list) {
                    log.info("-----预测开始-----");

                    if (currentCoin < ConfigLoader.helperConfig.getTaskConfig().getMinimumNumberOfCoins()) {
                        log.info("仅剩{}个硬币，低于最低保留硬币数量，后续预测不再执行", currentCoin);
                        break;
                    }

                    JsonObject contestJson = listinfo.getAsJsonObject().getAsJsonObject("contest");
                    JsonObject questionJson = listinfo.getAsJsonObject().getAsJsonArray("questions").get(0).getAsJsonObject();
                    contestId = contestJson.get("id").getAsInt();
                    contestName = contestJson.get("game_stage").getAsString();
                    questionId = questionJson.get("id").getAsInt();
                    questionTitle = questionJson.get("title").getAsString();
                    //seasonId = contestJson.get("season").getAsJsonObject().get("id").getAsInt();
                    seasonName = contestJson.get("season").getAsJsonObject().get("title").getAsString();

                    log.info("{} {}:{}", seasonName, contestName, questionTitle);

                    if (questionJson.get("is_guess").getAsInt() == 1) {
                        log.info("此问题已经参与过预测了，无需再次预测");
                        continue;
                    }
                    JsonObject teamA = questionJson.get("details").getAsJsonArray().get(0).getAsJsonObject();
                    JsonObject teamB = questionJson.get("details").getAsJsonArray().get(1).getAsJsonObject();
                    log.info("当前赔率为:  {}:{}", teamA.get("odds").getAsDouble(), teamB.get("odds").getAsDouble());

                    if (ConfigLoader.helperConfig.getTaskConfig().getShowHandModel()) {
                        if (teamA.get("odds").getAsDouble() <= teamB.get("odds").getAsDouble()) {
                            teamId = teamB.get("detail_id").getAsInt();
                            teamName = teamB.get("option").getAsString();
                        } else {
                            teamId = teamA.get("detail_id").getAsInt();
                            teamName = teamA.get("option").getAsString();
                        }
                    } else {
                        if (teamA.get("odds").getAsDouble() >= teamB.get("odds").getAsDouble()) {
                            teamId = teamB.get("detail_id").getAsInt();
                            teamName = teamB.get("option").getAsString();
                        } else {
                            teamId = teamA.get("detail_id").getAsInt();
                            teamName = teamA.get("option").getAsString();
                        }
                    }
                    log.info("拟预测的队伍是:{},预测硬币数为:{}", teamName, coinNumber);
                    currentCoin -= coinNumber;
                    doPrediction(contestId, questionId, teamId, coinNumber);
                    new SleepTime().sleepDefault();
                }
            }
        } else {
            log.info("获取赛事信息失败");
        }
    }


    private JsonObject queryContestQuestion(String today, int pn, int ps) {
        String gid = "";
        String sids = "";
        String urlParam = "";
        try {
            urlParam = "?pn=" + pn
                    + "&ps=" + ps
                    + "&gid=" + gid
                    + "&sids=" + sids
                    + "&stime=" + today + URLEncoder.encode(" 00:00:00", "UTF-8")
                    + "&etime=" + today + URLEncoder.encode(" 23:59:59", "UTF-8");
        } catch (Exception ignored) {

        }

        return HttpUtils.doGet(ApiList.QUERY_QUESTIONS + urlParam);
    }

    private void doPrediction(int oid, int main_id, int detail_id, int count) {
        String requestbody = "oid=" + oid
                + "&main_id=" + main_id
                + "&detail_id=" + detail_id
                + "&count=" + count
                + "&is_fav=0"
                + "&csrf=" + ConfigLoader.helperConfig.getBiliVerify().getBiliJct();

        JsonObject result = HttpUtils.doPost(ApiList.DO_MATCH_ADD, requestbody);

        if (result.get("code").getAsInt() != 0) {
            log.info(result.get("message").getAsString());
        } else {
            log.info("预测成功");
        }

    }

    private String getTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }

    @Override
    public String getName() {
        return "赛事预测";
    }
}