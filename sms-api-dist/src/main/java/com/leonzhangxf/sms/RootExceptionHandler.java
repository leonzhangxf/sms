package com.leonzhangxf.sms;

import com.leonzhangxf.sms.exception.DeleteException;
import com.leonzhangxf.sms.exception.SaveException;
import com.leonzhangxf.sms.exception.UpdateException;
import com.leonzhangxf.sms.exception.UpdateStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理
 *
 * @author leonzhangxf
 * @since 20190320
 */
@ControllerAdvice
public class RootExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(RootExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<String> rootExceptionHandler(RuntimeException ex) {
        logger.error("### Exception:", ex);
        return new ResponseEntity<>("Ops，好像出了点毛病？=。=", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SaveException.class)
    @ResponseBody
    public ResponseEntity<String> saveExceptionHandler(SaveException ex) {
        logger.info("### SaveException:", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UpdateException.class)
    @ResponseBody
    public ResponseEntity<String> updateExceptionHandler(UpdateException ex) {
        logger.info("### UpdateException:", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UpdateStatusException.class)
    @ResponseBody
    public ResponseEntity<String> updateStatusExceptionHandler(UpdateStatusException ex) {
        logger.info("### UpdateStatusException:", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(DeleteException.class)
    @ResponseBody
    public ResponseEntity<String> deleteExceptionHandler(DeleteException ex) {
        logger.info("### DeleteException:", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
