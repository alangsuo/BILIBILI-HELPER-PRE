package top.misec;

import lombok.Data;

/**
 * 外部配置
 *
 * @author itning
 * @since 2021/4/29 17:55
 */
@Data
public class KeyValueClass {
    private String dedeuserid;
    private String sessdata;
    private String biliJct;
    private String serverpushkey;
    private String telegrambottoken;
    private String telegramchatid;

    private int numberOfCoins;
    private int reserveCoins;
    private int selectLike;
    private boolean monthEndAutoCharge;
    private boolean giveGift;
    private String upLive;
    private String chargeForLove;
    private String devicePlatform;
    private int coinAddPriority;
    private boolean skipDailyTask;
    private String userAgent;
}
