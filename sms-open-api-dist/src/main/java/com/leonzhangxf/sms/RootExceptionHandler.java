package com.leonzhangxf.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RootExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(RootExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<String> rootExceptionHandler(RuntimeException ex) {
        logger.error("### RuntimeException:", ex);
        return new ResponseEntity<>("Ops，好像出了点毛病？=。=", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}