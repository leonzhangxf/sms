package com.leonzhangxf.sms.authentication;

import com.leonzhangxf.sms.exception.ValidateTimestampException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 请求认证拦截器，具体实现需要对对应的切面进行验证
 *
 * @author leonzhangxf 21181219
 */
public abstract class AbstractAuthenticationInterceptor {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 校验时间戳时效性
     *
     * @param timestamp      时间戳
     * @param timeDifference 时间戳的允许时间差，单位：毫秒
     * @throws ValidateTimestampException 校验时间戳异常
     */
    protected void validateTimestamp(String timestamp, Integer timeDifference) throws ValidateTimestampException {
        if (!StringUtils.hasText(timestamp) || null == timeDifference)
            throw new ValidateTimestampException("时间戳参数不能为空！");

        try {
            Date paramsTime = new Date(Long.valueOf(timestamp));
            Date sysTime = new Date();
            if ((sysTime.getTime() - paramsTime.getTime()) > timeDifference) {
                throw new ValidateTimestampException("时间戳校验超时，请重新请求！");
            }
        } catch (Exception ex) {
            logger.debug("####时间戳校验错误。", ex);
            throw new ValidateTimestampException("时间戳校验错误，请重新请求！");
        }
    }

    /**
     * 对指定切面进行验证
     */
    protected abstract Object validate(ProceedingJoinPoint joinPoint) throws Throwable;
}
