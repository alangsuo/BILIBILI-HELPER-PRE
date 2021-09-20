package top.misec.task;

import org.apache.commons.lang3.RandomStringUtils;
import top.misec.push.impl.DingTalkSecretPush;
import top.misec.push.model.PushMetaInfo;
import top.misec.push.model.PushResult;

import java.nio.charset.StandardCharsets;

/**
 * push test .
 *
 * @author itning
 * @since 2021/3/28 16:06
 */
public class PushTest {
    //@Test
    public void test1() {
        String s = RandomStringUtils.randomAlphanumeric(19926);
        System.out.println(s.getBytes(StandardCharsets.UTF_8).length);

        PushMetaInfo pushMetaInfo = PushMetaInfo.builder()
                .token("")
                .secret("")
                .build();
        PushResult pushResult = new DingTalkSecretPush().doPush(pushMetaInfo, s);
        System.out.println(pushResult.isSuccess());
    }
}
