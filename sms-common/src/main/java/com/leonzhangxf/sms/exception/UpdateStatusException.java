package com.leonzhangxf.sms.exception;

/**
 * 更新状态异常
 *
 * @author leonzhangxf 20181008
 */
public class UpdateStatusException extends RuntimeException {

    private static final long serialVersionUID = -7240398487740174852L;

    public UpdateStatusException(String message) {
        super(message);
    }
}
