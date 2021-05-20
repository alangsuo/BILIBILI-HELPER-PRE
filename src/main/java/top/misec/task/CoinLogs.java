package top.misec.task;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import top.misec.apiquery.ApiList;
import top.misec.utils.HttpUtil;

/**
 * @author junzhou
 */
@Log4j2
public class CoinLogs implements Task {
    @Override
    public void run() {
        JsonObject jsonObject = HttpUtil.doGet(ApiList.getCionLog);
        if (jsonObject.get("code").getAsInt() == 0) {
            JsonObject data = jsonObject.getAsJsonObject("data");
            log.info("最近一周共计{}条硬币记录", data.get("count").getAsInt());
            JsonArray coinList = data.getAsJsonArray("list");

            double income = 0.0;
            double expend = 0.0;
            for (JsonElement jsonElement : coinList) {
                double delta = jsonElement.getAsJsonObject().get("delta").getAsDouble();
                String reason = jsonElement.getAsJsonObject().get("reason").getAsString();
                if (delta > 0) {
                    income += delta;
                }else {
                    expend+=delta;
                }
            }
            log.info("最近一周收入{}个硬币",income);
            log.info("最近一周支出{}个硬币",expend);
        }

    }

    @Override
    public String getName() {
        return "硬币情况统计";
    }
}
