package com.leonzhangxf.sms.exception;

/**
 * 校验时间戳异常
 *
 * @author leonzhangxf 20181025
 */
public class ValidateTimestampException extends Exception {

    private static final long serialVersionUID = -4086014104147642086L;

    public ValidateTimestampException(String message) {
        super(message);
    }
}
