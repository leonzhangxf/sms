package com.leonzhangxf.sms.authentication;

import com.leonzhangxf.sms.constant.MessageV1ParamsConstant;
import com.leonzhangxf.sms.constant.SmsAuthConstant;
import com.leonzhangxf.sms.domain.dto.TemplateDTO;
import com.leonzhangxf.sms.exception.ValidateTimestampException;
import com.leonzhangxf.sms.service.TemplateService;
import com.leonzhangxf.sms.util.SmsUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * V1短信发送接口认证拦截器
 *
 * @author leonzhangxf 20181219
 */
@Aspect
@Component
public class MessageV1AuthenticationInterceptor extends AbstractAuthenticationInterceptor {

    private TemplateService templateService;

    private SmsAuthConstant smsAuthConstant;

    @Pointcut("execution(* com.leonzhangxf.sms.api.MessageV1Api.message(..))")
    public void messageV1Interceptor() {
    }

    @Around("messageV1Interceptor()")
    public Object validateMessageV1Api(ProceedingJoinPoint joinPoint) throws Throwable {
        return validate(joinPoint);
    }

    @Override
    protected Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
        if (null == sra) {
            return ResponseEntity.badRequest().body("获取参数异常，请重试！");
        }
        HttpServletRequest request = sra.getRequest();

        String mobile = request.getParameter(MessageV1ParamsConstant.MOBILE);
        if (!StringUtils.hasText(mobile)) {
            return ResponseEntity.badRequest().body("手机号参数不能为空!");
        }
        String templateId = request.getParameter(MessageV1ParamsConstant.TEMPLATEID);
        if (!StringUtils.hasText(templateId)) {
            return ResponseEntity.badRequest().body("模板ID参数不能为空!");
        }
        String timestamp = request.getParameter(MessageV1ParamsConstant.TIMESTAMP);
        if (!StringUtils.hasText(timestamp)) {
            return ResponseEntity.badRequest().body("时间戳参数不能为空!");
        }
        String sign = request.getParameter(MessageV1ParamsConstant.SIGN);
        if (!StringUtils.hasText(sign)) {
            return ResponseEntity.badRequest().body("签名参数不能为空!");
        }

        // 校验时间戳
        try {
            validateTimestamp(timestamp, smsAuthConstant.getTimeDifference());
        } catch (ValidateTimestampException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        // 校验签名
        TemplateDTO templateDTO = templateService.templateByTemplateId(templateId);
        if (null == templateDTO || null == templateDTO.getClient() || null == templateDTO.getClient().getKey()) {
            return ResponseEntity.badRequest().body("根据模板ID参数未找到对应模板或对接方!");
        }
        String serverSign = SmsUtils.signV1(templateDTO.getClient().getKey(), mobile, templateId, timestamp);
        if (!StringUtils.hasText(serverSign) || !serverSign.equals(sign)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("签名校验不通过！");
        }

        return joinPoint.proceed();
    }

    @Autowired
    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }

    @Autowired
    public void setSmsAuthConstant(SmsAuthConstant smsAuthConstant) {
        this.smsAuthConstant = smsAuthConstant;
    }
}
