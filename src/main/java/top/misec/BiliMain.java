package top.misec;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import top.misec.API.apiList;
import top.misec.Login.Verify;
import top.misec.Task.DailyTask;
import top.misec.UserInfo.Data;
import top.misec.Utils.HttpUnit;


/**
 * @author Junzhou Liu
 * @create 2020/10/11 2:29
 */
public class BiliMain {

    static Logger logger = (Logger) LogManager.getLogger(BiliMain.class.getName());

    public static void main(String[] args) {
        // userid,ssesdata,bilijct
        Verify verify = new Verify(args[0], args[1], args[2]);

        Data userInfo = new Gson().fromJson(HttpUnit.Get(apiList.Login)
                .getAsJsonObject("data"), Data.class);

        logger.info("----user----  :" + userInfo.getUname() + "------top.misec.Login successful------");
        logger.info("----coin----  :" + userInfo.getMoney());
        logger.info("----becomeLv" + (userInfo.getLevel_info().getCurrent_level() + 1) + "----  :" +
                (userInfo.getLevel_info().getNext_exp() - userInfo.getLevel_info().getCurrent_exp()) / 65 + " day");


        //daily exp task
        DailyTask dailyTask = new DailyTask();
        //share video  exp +5;
        dailyTask.avShare();
        //manga sing-in
        dailyTask.mangaSign("ios");
    }

}
