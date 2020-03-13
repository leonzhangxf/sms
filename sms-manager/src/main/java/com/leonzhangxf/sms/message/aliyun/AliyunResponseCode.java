package com.leonzhangxf.sms.message.aliyun;

import com.leonzhangxf.sms.enumeration.SendResponseStatus;
import org.springframework.util.StringUtils;

/**
 * 阿里云渠道响应码枚举
 *
 * @author leonzhangxf 20181115
 */
public enum AliyunResponseCode {

    /**
     * OK - 请求成功
     */
    OK("OK", "请求成功", SendResponseStatus.OK),

    /**
     * isv.BUSINESS_LIMIT_CONTROL - 业务限流
     */
    BUSINESS_LIMIT_CONTROL("isv.BUSINESS_LIMIT_CONTROL", "业务限流", SendResponseStatus.FORBIDDEN),

    /**
     * isv.* - 阿里云渠道其他异常
     */
    OTHER("isv.*", "阿里云渠道其他异常", SendResponseStatus.INTERNAL_SERVER_ERROR);

    String code;
    String desc;
    SendResponseStatus status;

    AliyunResponseCode(String code, String desc, SendResponseStatus status) {
        this.code = code;
        this.desc = desc;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public SendResponseStatus getStatus() {
        return status;
    }

    public static AliyunResponseCode getAliyunResponseCode(String code) {
        if (!StringUtils.hasText(code)) return OTHER;
        AliyunResponseCode[] values = AliyunResponseCode.values();
        if (values.length > 0) {
            for (AliyunResponseCode value : values) {
                if (value.getCode().equals(code)) {
                    return value;
                }
            }
        }
        return OTHER;
    }
}
