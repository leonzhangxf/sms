package com.leonzhangxf.sms.message;

import com.leonzhangxf.sms.enumeration.ChannelConfigParam;
import com.leonzhangxf.sms.enumeration.ChannelConfigType;
import com.leonzhangxf.sms.enumeration.SendResponseStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;

public class MessageProcessorTest {

    @Test
    public void testMessageWithContext() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("count time");

        MessageContextBuilder builder = MessageContextBuilder.newMessageContextBuilder();
        builder.channelConfigType(ChannelConfigType.ALIYUN);

        Map<ChannelConfigParam, String> configParams = new HashMap<>();
        configParams.put(ChannelConfigParam.ACCESS_KEY_ID, "XXXXX");
        configParams.put(ChannelConfigParam.ACCESS_KEY_SECRET, "XXXXX");
        builder.configParams(configParams);

        builder.signName("XXXX");
        builder.templateId("XXXXX");
        builder.templateContent("您的验证码：${vcode}，有效时间为10分钟，请尽快输入。如非本人操作，请忽略本信息。");

        Map<String, String> params = new HashMap<>();
        params.put("vcode", "123456");
        builder.mobileAndParams("15123279507", params);

        MessageRequestMobileUnit unit = new MessageRequestMobileUnit();
        unit.setMobile("15010193750");
        Map<String, String> params1 = new HashMap<>();
        params1.put("vcode", "654321");
        unit.setParams(params1);
        builder.mobileUnit(unit);

        MessageContext context = builder.build();
        System.out.println(context.getRequest().toString());
        MessageResponse response = MessageProcessor.message(context);

        stopWatch.stop();
        System.out.println("Time total count: " + stopWatch.getTotalTimeMillis());

        Assert.assertNotNull("没有生成响应", response);
        Assert.assertEquals("调用失败:" + response.toString(),
                response.getResponseStatus(), SendResponseStatus.OK);
    }
}
