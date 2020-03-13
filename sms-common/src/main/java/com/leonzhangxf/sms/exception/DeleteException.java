package com.leonzhangxf.sms.exception;

/**
 * 删除异常
 *
 * @author leonzhangxf 20181008
 */
public class DeleteException extends RuntimeException {

    private static final long serialVersionUID = 3468032016912701663L;

    public DeleteException(String message) {
        super(message);
    }
}
