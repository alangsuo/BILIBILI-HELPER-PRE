package top.misec.task;

import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import top.misec.apiquery.ApiList;
import top.misec.utils.HttpUnit;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Junzhou Liu
 * @create 2020/10/21 17:39
 */
public class ServerPush {
    static Logger logger = (Logger) LogManager.getLogger(ServerPush.class.getName());
    public static String SCKEY = null;


    public ServerPush(String sckey) {
        ServerPush.SCKEY = sckey;
    }

    public void pushMsg(String text, String desp) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        desp = String.valueOf(cal.getTime());

        String pushUrl = ApiList.ServerPush + SCKEY + ".send";
        String pushBody = "text=" + text + "&desp=" + desp;

        JsonObject jsonObject = null;
        jsonObject = HttpUnit.doPost(pushUrl, pushBody);

        if (jsonObject.get("errmsg").getAsString().equals("success")) {
            logger.info("任务状态推送成功");
        } else {
            logger.debug(jsonObject);
        }

    }

}
