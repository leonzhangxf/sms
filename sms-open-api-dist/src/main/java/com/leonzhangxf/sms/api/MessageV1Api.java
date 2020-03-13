package com.leonzhangxf.sms.api;

import com.leonzhangxf.sms.domain.dto.MessageV1DTO;
import com.leonzhangxf.sms.exception.SingleMessageException;
import com.leonzhangxf.sms.message.MessageResponse;
import com.leonzhangxf.sms.service.MessageService;
import com.leonzhangxf.sms.enumeration.SendResponseStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "V1版本")
@RestController
@RequestMapping("v1")
public class MessageV1Api {

    private Logger logger = LoggerFactory.getLogger(MessageV1Api.class);

    private MessageService messageService;

    @ApiOperation(value = "短信服务发送短信接口V1版本", notes = "需要根据参数使用HmacSHA256签名算法生成签名。"
        + "将参数拼接为“mobile_templateId_timestamp”，并同时传入下发的key，进行签名。将生成的签名一同加入请求参数。")
    @PostMapping("message")
    public ResponseEntity<String> message(@Validated @ModelAttribute MessageV1DTO message,
                                          BindingResult validateResult) {
        // 1.参数校验
        // 1.1初步校验
        if (validateResult.hasErrors()) {
            FieldError error = validateResult.getFieldError();
            if (null != error) {
                String errorMessage = error.getDefaultMessage();
                return ResponseEntity.badRequest().body(errorMessage);
            }
        }

        // 2.短信发送，并异步保存发送日志
        MessageResponse response;
        try {
            response = messageService.singleMessageV1(message);
        } catch (SingleMessageException ex) {
            logger.error("短信服务商未知错误，参数列表：{}，异常信息：{}", message, ex.getMessage());
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
        if (null == response) {
            logger.error("短信服务商未知错误，参数列表：{}，没有响应。", message);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取短信服务商响应异常，请稍后再试！");
        }

        // 3.处理响应
        ResponseEntity<String> res;
        switch (response.getResponseStatus()) {
            case OK:
                res = ResponseEntity.ok("短信发送成功");
                break;
            case BAD_REQUEST:
                logger.warn("请求短信服务商异常，参数列表：{}，请求响应：{}", message, response);
                res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("请求短信服务商异常，请稍后再试！");
                break;
            case FORBIDDEN:
                logger.info("短信请求渠道限流，参数列表：{}，请求响应：{}", message, response);
                res = ResponseEntity.status(HttpStatus.FORBIDDEN).body("短信渠道限流，请稍后再试！");
                break;
            case INTERNAL_SERVER_ERROR:
                logger.warn("短信服务商响应异常，参数列表：{}，请求响应：{}", message, response);
                res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("短信服务商响应异常，请稍后再试！");
                break;
            default:
                logger.error("短信服务商未知错误，参数列表：{}，请求响应：{}", message, response);
                res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("短信服务商未知错误，请稍后再试！");
        }
        return res;
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }
}
