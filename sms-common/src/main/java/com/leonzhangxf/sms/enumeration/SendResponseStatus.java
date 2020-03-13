package com.leonzhangxf.sms.enumeration;

/**
 * 短信发送响应状态
 *
 * @author leonzhangxf 20181009
 */
public enum SendResponseStatus {

    /**
     * 200-正常
     */
    OK(200, "正常"),

    /**
     * 400-请求异常
     */
    BAD_REQUEST(400, "请求异常"),

    /**
     * 403-短信渠道限流
     */
    FORBIDDEN(403, "短信渠道限流"),

    /**
     * 500-响应异常
     */
    INTERNAL_SERVER_ERROR(500, "响应异常");

    private int value;

    private String desc;

    SendResponseStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int value() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static SendResponseStatus getSendResponseStatus(int param) {
        SendResponseStatus[] values = SendResponseStatus.values();
        if (values.length > 0) {
            for (SendResponseStatus value : values) {
                if (value.value() == param)
                    return value;
            }
        }
        return null;
    }
}
