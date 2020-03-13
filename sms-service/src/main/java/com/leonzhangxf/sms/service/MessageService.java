package com.leonzhangxf.sms.service;

import com.leonzhangxf.sms.domain.dto.MessageV1DTO;
import com.leonzhangxf.sms.exception.SingleMessageException;
import com.leonzhangxf.sms.message.MessageResponse;

/**
 * 短信发送service
 *
 * @author leonzhangxf 20181219
 */
public interface MessageService {

    /**
     * 短信单发
     *
     * @param message 短信发送请求参数，保证参数基本正确性
     * @return 短信发送响应
     */
    MessageResponse singleMessageV1(MessageV1DTO message) throws SingleMessageException;
}
