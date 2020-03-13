package com.leonzhangxf.sms.exception;

import org.springframework.http.HttpStatus;

/**
 * 短信单发异常
 *
 * @author leonzhangxf
 */
public class SingleMessageException extends Exception {

    private static final long serialVersionUID = 943977291316020780L;

    private HttpStatus status;

    public SingleMessageException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
