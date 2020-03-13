package com.leonzhangxf.sms.exception;

/**
 * 更新异常
 *
 * @author leonzhangxf 20181008
 */
public class UpdateException extends RuntimeException {

    private static final long serialVersionUID = 7509486403149074651L;

    public UpdateException(String message) {
        super(message);
    }
}
