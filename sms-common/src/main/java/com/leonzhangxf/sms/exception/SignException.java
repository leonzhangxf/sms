package com.leonzhangxf.sms.exception;

/**
 * 签名异常
 *
 * @author leonzhangxf 20181024
 */
public class SignException extends Exception {

    private static final long serialVersionUID = -8177931079065505427L;

    public SignException(String message) {
        super(message);
    }
}
