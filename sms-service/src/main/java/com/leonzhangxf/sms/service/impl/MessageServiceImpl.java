package com.leonzhangxf.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.leonzhangxf.sms.constant.SmsConstant;
import com.leonzhangxf.sms.domain.SendLogDO;
import com.leonzhangxf.sms.enumeration.ChannelConfigParam;
import com.leonzhangxf.sms.exception.SingleMessageException;
import com.leonzhangxf.sms.message.*;
import com.leonzhangxf.sms.service.ChannelConfigParamsService;
import com.leonzhangxf.sms.service.MessageService;
import com.leonzhangxf.sms.service.SendLogService;
import com.leonzhangxf.sms.service.TemplateService;
import com.leonzhangxf.sms.domain.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信发送service
 *
 * @author leonzhangxf 20181219
 */
@Service
public class MessageServiceImpl implements MessageService {

    private Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    private TemplateService templateService;

    private ChannelConfigParamsService channelConfigParamsService;

    private SendLogService sendLogService;

    @Override
    @Transactional
    // 忽略JSON解析类型转换警告
    @SuppressWarnings("unchecked")
    public MessageResponse singleMessageV1(MessageV1DTO message) throws SingleMessageException {

        TemplateDTO templateDTO = templateService.templateByTemplateId(message.getTemplateId());
        if (null == templateDTO || null == templateDTO.getClient() || null == templateDTO.getClient().getKey()) {
            throw new SingleMessageException(HttpStatus.BAD_REQUEST, "根据模板ID参数未找到对应模板或对接方!");
        }

        // 1.构造短信发送上下文
        MessageContextBuilder builder = MessageContextBuilder.newMessageContextBuilder();

        // 1.1 渠道类型
        ChannelConfigDTO channelConfig = templateDTO.getChannelTemplate().getChannelConfig();
        builder.channelConfigType(channelConfig.getType());

        // 1.2 渠道配置参数
        List<ChannelConfigParamsDTO> channelConfigParamsList =
            channelConfigParamsService.configParams(channelConfig.getId());
        if (CollectionUtils.isEmpty(channelConfigParamsList)) {
            throw new SingleMessageException(HttpStatus.INTERNAL_SERVER_ERROR, "根据模板ID参数未找到对应支持渠道方!");
        }
        Map<ChannelConfigParam, String> configParams = new HashMap<>();
        for (ChannelConfigParamsDTO configParam : channelConfigParamsList) {
            configParams.put(configParam.getKey(), configParam.getValue());
        }
        builder.configParams(configParams);

        // 1.3 签名
        ChannelSignatureDTO channelSignature = templateDTO.getChannelSignature();
        if (null == channelSignature) {
            throw new SingleMessageException(HttpStatus.INTERNAL_SERVER_ERROR, "根据模板ID参数未找到对应支持渠道方签名!");
        }
        builder.signName(templateDTO.getChannelSignature().getSignature());

        // 1.4 短信模板
        ChannelTemplateDTO channelTemplate = templateDTO.getChannelTemplate();
        if (null == channelTemplate) {
            throw new SingleMessageException(HttpStatus.INTERNAL_SERVER_ERROR, "根据模板ID参数未找到对应支持渠道方模板!");
        }
        builder.templateId(templateDTO.getChannelTemplate().getCode());
        builder.templateContent(templateDTO.getChannelTemplate().getContent());

        // 1.5 模板参数
        String serverParams = channelTemplate.getParams();
        String params = message.getParams();
        if (StringUtils.hasText(serverParams) && !StringUtils.hasText(params)) {
            logger.debug("服务端有参数，而请求没有参数，无法处理。");
            throw new SingleMessageException(HttpStatus.INTERNAL_SERVER_ERROR, "对应模板需要模板参数!");
        }
        if (!StringUtils.hasText(serverParams)) {
            logger.debug("服务端不需要参数，则不管请求有无参数，都可以执行。");
            MessageRequestMobileUnit unit = new MessageRequestMobileUnit();
            unit.setMobile(message.getMobile());
            builder.mobileUnit(unit);
        } else {
            logger.debug("服务端需要参数，请求也存在参数，解析并装载参数。");
            try {
                String[] serverParamList = serverParams.split(SmsConstant.CHANNEL_TEMPLATE_PARAMS_DELIMITER);
                Map<String, String> paramsMap = JSON.parseObject(params, Map.class);
                for (String serverParam : serverParamList) {
                    if (null == paramsMap.get(serverParam)) {
                        throw new SingleMessageException(HttpStatus.BAD_REQUEST, "请求参数列表不匹配!");
                    }
                }
                builder.mobileAndParams(message.getMobile(), paramsMap);
            } catch (Exception ex) {
                logger.error("服务端参数或请求参数解析异常！请求参数为：{}，服务端参数为：{}", params, serverParams, ex);
                throw new SingleMessageException(HttpStatus.BAD_REQUEST, "请求参数中模板参数解析异常，请重试!");
            }
        }

        // 2.短信发送并获取响应
        MessageContext context = builder.build();
        Date sendDate = new Date();
        MessageResponse response = MessageProcessor.message(context);
        logger.debug("请求短信渠道方响应：{}", response);

        // 3.异步保存发送日志
        SendLogDO sendLog = new SendLogDO();
        sendLog.setTemplateId(templateDTO.getId());
        sendLog.setMobile(message.getMobile());
        sendLog.setParams(message.getParams());
        sendLog.setStatus(response.getResponseStatus().value());
        sendLog.setMessage(response.getResponseMessage());
        sendLog.setSendTime(sendDate);
        // 这里是异步操作
        sendLogService.saveSendLog(sendLog);

        return response;
    }

    @Autowired
    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }

    @Autowired
    public void setChannelConfigParamsService(ChannelConfigParamsService channelConfigParamsService) {
        this.channelConfigParamsService = channelConfigParamsService;
    }

    @Autowired
    public void setSendLogService(SendLogService sendLogService) {
        this.sendLogService = sendLogService;
    }
}
