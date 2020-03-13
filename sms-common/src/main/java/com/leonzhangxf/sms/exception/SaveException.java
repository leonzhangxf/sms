package com.leonzhangxf.sms.exception;

/**
 * 保存异常
 *
 * @author leonzhangxf 20181008
 */
public class SaveException extends RuntimeException {

    private static final long serialVersionUID = -3332499826040711010L;

    public SaveException(String message) {
        super(message);
    }
}
