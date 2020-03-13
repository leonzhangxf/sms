package com.leonzhangxf.sms.message;

import com.leonzhangxf.sms.enumeration.ChannelConfigType;
import com.leonzhangxf.sms.enumeration.SendResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 短信发送处理器
 *
 * @author leonzhangxf 20181115
 */
public class MessageProcessor {

    private static Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    private static Map<ChannelConfigType, MessageResolver> messageResolvers;

    private static final String MESSAGE_PROPS_PATH = "./messager.properties";

    static {
        Properties properties = new Properties();
        try {
            InputStream inputStream = MessageProcessor.class.getResource(MESSAGE_PROPS_PATH).openStream();
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Load message resolver error.", e);
            }
        }

        resolveMessageResolvers(properties);
    }

    /**
     * 传入发送短信请求，以发送短信
     */
    public static MessageResponse message(MessageRequest request) {
        ChannelConfigType configType = request.getChannelConfigType();
        MessageResolver resolver = messageResolvers.get(configType);

        if (null == resolver) {
            return resolveBadRequestResponse(configType);
        }

        MessageContext context = MessageContextBuilder.newMessageContextBuilder().messageRequest(request).build();
        if (null == context || null == context.getRequest()) {
            logger.warn("Message context resolve error. request: {}", request);
            return MessageResponse.newMessageResponse(SendResponseStatus.BAD_REQUEST,
                    "短信请求构建失败");
        }
        //发送短信
        resolver.message(context);
        return context.getResponse();
    }

    /**
     * 传入发送短信请求上下文，以发送短信
     */
    public static MessageResponse message(MessageContext context) {
        if (null == context || null == context.getRequest()) {
            logger.warn("Message context resolve error. The input context is null");
            return MessageResponse.newMessageResponse(SendResponseStatus.BAD_REQUEST,
                    "短信请求构建失败");
        }
        ChannelConfigType configType = context.getRequest().getChannelConfigType();
        MessageResolver resolver = messageResolvers.get(configType);

        if (null == resolver) {
            return resolveBadRequestResponse(configType);
        }
        //发送短信
        resolver.message(context);
        return context.getResponse();
    }

    private static MessageResponse resolveBadRequestResponse(ChannelConfigType configType) {
        String errorMessage;
        if (null == configType) {
            errorMessage = String.format("根据渠道配置类型: %s 未找到对应的短信发送处理器", "未设置渠道类型");
        } else {
            errorMessage = String.format("根据渠道配置类型: %s 未找到对应的短信发送处理器", configType.getName());
        }
        return MessageResponse.newMessageResponse(SendResponseStatus.BAD_REQUEST, errorMessage);
    }

    private static void resolveMessageResolvers(Properties properties) {
        ChannelConfigType[] channelConfigTypes = ChannelConfigType.values();
        if (channelConfigTypes.length > 0) {
            messageResolvers = new HashMap<>();
            for (ChannelConfigType type : channelConfigTypes) {
                String className = properties.getProperty(type.getName());
                findAndInitialize(type, className);
            }
        }
    }

    private static void findAndInitialize(ChannelConfigType type, String className) {
        if (StringUtils.hasText(className)) {
            Class<?> messageClass = null;
            try {
                messageClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Has config type but not found out relative message class implements.", e);
                }
            }

            if (null != messageClass) {
                List<Class<?>> interfaces = Arrays.asList(messageClass.getInterfaces());
                if (!interfaces.contains(MessageResolver.class)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Message class config error, not a implementation of " +
                                "MessageResolver.");
                    }
                    return;
                }

                MessageResolver resolver = null;
                try {
                    resolver = (MessageResolver) messageClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Instantiation message resolver error. " +
                                "the config type is {}", type.getName(), e);
                    }
                }
                if (null != resolver) {
                    messageResolvers.put(type, resolver);
                }
            }
        }
    }
}
