package top.misec.task;

import org.junit.jupiter.api.Test;
import top.misec.push.impl.*;
import top.misec.push.model.PushMetaInfo;
import top.misec.push.model.PushResult;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * push test .
 *
 * @author itning
 * @since 2021/3/28 16:06
 */

public class PushTest {

    @Test
    public void testServerChanTurboPush() {

        PushMetaInfo pushMetaInfo = PushMetaInfo.builder()
                .chatId("")
                .token("")
                .secret("")
                .build();
        PushResult pushResult = new ServerChanTurboPush().doPush(pushMetaInfo, "testServerChanTurboPush");
        assertTrue(pushResult.isSuccess());
    }

    @Test
    public void testDingTalkPush() {

        PushMetaInfo pushMetaInfo = PushMetaInfo.builder()
                .chatId("")
                .token("")
                .secret("")
                .build();
        PushResult pushResult = new DingTalkPush().doPush(pushMetaInfo, "testDingTalkPush");
        assertTrue(pushResult.isSuccess());
    }

    @Test
    public void testDingTalkSecretPush() {

        PushMetaInfo pushMetaInfo = PushMetaInfo.builder()
                .chatId("")
                .token("")
                .secret("")
                .build();
        PushResult pushResult = new DingTalkSecretPush().doPush(pushMetaInfo, "testDingTalkSecretPush");
        assertTrue(pushResult.isSuccess());
    }

    @Test
    public void testPushPlusPush() {

        PushMetaInfo pushMetaInfo = PushMetaInfo.builder()
                .chatId("")
                .token("")
                .secret("")
                .build();
        PushResult pushResult = new PushPlusPush().doPush(pushMetaInfo, "testPushPlusPush");
        assertTrue(pushResult.isSuccess());
    }

    @Test
    public void testServerChanPush() {

        PushMetaInfo pushMetaInfo = PushMetaInfo.builder()
                .chatId("")
                .token("")
                .secret("")
                .build();
        PushResult pushResult = new ServerChanPush().doPush(pushMetaInfo, "testServerChanPush");
        assertTrue(pushResult.isSuccess());
    }

    @Test
    public void testTelegramCustomUrlPush() {

        PushMetaInfo pushMetaInfo = PushMetaInfo.builder()
                .chatId("")
                .token("")
                .secret("")
                .build();
        PushResult pushResult = new TelegramCustomUrlPush().doPush(pushMetaInfo, "testTelegramCustomUrlPush");
        assertTrue(pushResult.isSuccess());
    }

    @Test
    public void testTelegramPush() {

        PushMetaInfo pushMetaInfo = PushMetaInfo.builder()
                .chatId("")
                .token("")
                .secret("")
                .build();
        PushResult pushResult = new TelegramPush().doPush(pushMetaInfo, "testTelegramPush");
        assertTrue(pushResult.isSuccess());
    }

    @Test
    public void testWeComPush() {

        PushMetaInfo pushMetaInfo = PushMetaInfo.builder()
                .chatId("")
                .token("")
                .secret("")
                .build();
        PushResult pushResult = new WeComPush().doPush(pushMetaInfo, "testWeComPush");
        assertTrue(pushResult.isSuccess());
    }

    @Test
    void testWeComAppPush() {
        PushMetaInfo pushMetaInfo = PushMetaInfo.builder()
                .token("")
                .secret("")
                .agentId(0)
                .build();
        PushResult pushResult = new WeComAppPush().doPush(pushMetaInfo, "testWeComAppPush");
        assertTrue(pushResult.isSuccess());
    }
}
