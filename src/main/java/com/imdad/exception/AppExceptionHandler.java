package com.imdad.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDate;

//@RestControllerAdvice
public class AppExceptionHandler {

    Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionInfo> handleException(Exception e) {
        ExceptionInfo ex = new ExceptionInfo();

        logger.error(e.getMessage());

        ex.setExCode("XY300");
        ex.setExMsg(e.getMessage());
        ex.setDate(LocalDate.now());

        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
