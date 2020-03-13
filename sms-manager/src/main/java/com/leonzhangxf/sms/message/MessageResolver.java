package com.leonzhangxf.sms.message;

/**
 * 短信发送器
 *
 * @author leonzhangxf 20181115
 */
public interface MessageResolver {

    /**
     * 发送短信
     *
     * @param context 短信请求上线文 {@link MessageContext}
     */
    void message(MessageContext context);
}
