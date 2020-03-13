package com.leonzhangxf.sms.message.resolver;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.leonzhangxf.sms.enumeration.ChannelConfigParam;
import com.leonzhangxf.sms.enumeration.SendResponseStatus;
import com.leonzhangxf.sms.message.*;
import com.leonzhangxf.sms.message.aliyun.AliyunResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 阿里云短信发送器
 *
 * @author leonzhangxf 20181115
 */
public class AliyunMessageResolver implements MessageResolver {

    private static Logger logger = LoggerFactory.getLogger(AliyunMessageResolver.class);

    /**
     * 初始化ascClient需要的几个参数
     * 短信API产品名称（短信产品名固定，无需修改）
     */
    private static final String PRODUCT = "Dysmsapi";

    /**
     * 短信API产品域名（接口地址固定，无需修改）
     */
    private static final String ENDPOINT_NAME = "dysmsapi.aliyuncs.com";

    private static final String REGION_ID = "cn-hangzhou";

    @Override
    public void message(MessageContext context) {
        if (null == context.getRequest()) {
            MessageResponse response = MessageResponse.newMessageResponse(SendResponseStatus.BAD_REQUEST,
                    "短信请求构建失败");
            context.setResponse(response);
            return;
        }
        MessageRequest messageRequest = context.getRequest();
        Map<ChannelConfigParam, String> configParams = messageRequest.getConfigParams();

        // 设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "100000");
        // 初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile(REGION_ID,
                configParams.get(ChannelConfigParam.ACCESS_KEY_ID),
                configParams.get(ChannelConfigParam.ACCESS_KEY_SECRET));
        DefaultProfile.addEndpoint(REGION_ID, PRODUCT, ENDPOINT_NAME);
        IAcsClient acsClient = new DefaultAcsClient(profile);


        List<MessageRequestMobileUnit> mobileUnits = messageRequest.getMobileUnits();
        if (mobileUnits.size() == 1) {
            // 单条
            MessageResponse response = resolveSingleMessage(messageRequest, acsClient);
            context.setResponse(response);
        } else if (mobileUnits.size() > 1) {
            // 批量
            MessageResponse response = resolveBatchMessage(messageRequest, acsClient);
            context.setResponse(response);
        } else {
            //其他不满足条件
            MessageResponse response = MessageResponse.newMessageResponse(SendResponseStatus.BAD_REQUEST,
                    "没有手机号信息");
            context.setResponse(response);
        }
    }

    /**
     * 批量发送
     */
    private MessageResponse resolveBatchMessage(MessageRequest messageRequest, IAcsClient acsClient) {
        List<String> mobileList = new ArrayList<>();
        List<String> signNameList = new ArrayList<>();
        List<Map<String, String>> paramsList = new ArrayList<>();
        for (MessageRequestMobileUnit unit : messageRequest.getMobileUnits()) {
            signNameList.add(messageRequest.getSignName());
            mobileList.add(unit.getMobile());
            if (!unit.getParams().isEmpty()) {
                paramsList.add(unit.getParams());
            }
        }

        // 组装请求对象
        SendBatchSmsRequest request = new SendBatchSmsRequest();
        // 使用post提交
        request.setMethod(MethodType.POST);

        // 必填:待发送手机号。支持JSON格式的批量调用，
        // 批量上限为100个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumberJson(JSON.toJSONString(mobileList));
        // 必填:短信签名-支持不同的号码发送不同的短信签名
        request.setSignNameJson(JSON.toJSONString(signNameList));
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(messageRequest.getTemplateId());
        // 必填:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,
        // 比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        if (paramsList.size() == mobileList.size()) {
            request.setTemplateParamJson(JSON.toJSONString(paramsList));
        } else {
            return MessageResponse.newMessageResponse(SendResponseStatus.BAD_REQUEST,
                    "手机号数量与参数列表数量不一致");
        }

        // 请求失败这里会抛ClientException异常
        SendBatchSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request, true, 3);
        } catch (ClientException e) {
            logger.warn("Message resolver message error. this is a batch request. config type: {} .",
                    messageRequest.getChannelConfigType().getName(), e);
        }

        if (null != sendSmsResponse && null != sendSmsResponse.getCode()) {
            return generateMessageResponse(sendSmsResponse.getCode(), sendSmsResponse.getMessage());
        }
        return MessageResponse.newMessageResponse(SendResponseStatus.BAD_REQUEST,
                "批量短信请求阿里云渠道失败");
    }

    /**
     * 单条发送
     */
    private MessageResponse resolveSingleMessage(MessageRequest messageRequest, IAcsClient acsClient) {
        List<MessageRequestMobileUnit> mobileUnits = messageRequest.getMobileUnits();
        MessageRequestMobileUnit unit = mobileUnits.get(0);

        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        // 使用post提交
        request.setMethod(MethodType.POST);

        // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，
        // 批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；
        // 发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers(unit.getMobile());

        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(messageRequest.getSignName());
        // 必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode(messageRequest.getTemplateId());

        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,
        // 比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        Map<String, String> params = unit.getParams();
        if (!params.isEmpty()) {
            request.setTemplateParam(JSON.toJSONString(params));
        }

        // 请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request, true, 5);
        } catch (ClientException e) {
            logger.warn("Message resolver message error. config type: {} .",
                    messageRequest.getChannelConfigType().getName(), e);
        }

        if (null != sendSmsResponse && null != sendSmsResponse.getCode()) {
            return generateMessageResponse(sendSmsResponse.getCode(), sendSmsResponse.getMessage());
        }
        return MessageResponse.newMessageResponse(SendResponseStatus.BAD_REQUEST,
                "单条短信请求阿里云渠道失败");
    }

    /**
     * 根据阿里云的响应，转换为服务内本身的响应
     */
    private MessageResponse generateMessageResponse(String code, String message) {
        AliyunResponseCode aliyunResponseCode = AliyunResponseCode.getAliyunResponseCode(code);
        switch (aliyunResponseCode) {
            case OK:
                return MessageResponse.newMessageResponse(SendResponseStatus.OK,
                        message);
            case BUSINESS_LIMIT_CONTROL:
                return MessageResponse.newMessageResponse(SendResponseStatus.FORBIDDEN,
                        message);
            default:
                String errorMessage = String.format("%s | %s", code, message);
                return MessageResponse.newMessageResponse(SendResponseStatus.INTERNAL_SERVER_ERROR, errorMessage);
        }
    }
}
