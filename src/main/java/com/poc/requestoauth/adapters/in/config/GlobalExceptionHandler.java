package com.poc.requestoauth.adapters.in.config;

import com.poc.requestoauth.adapters.in.config.util.ResponseEntityUtil;
import com.poc.requestoauth.domain.exceptions.CharacterNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorInfo> handleException(Exception exception) {
        ErrorInfo errorInfo = ErrorInfo.builder()
                .withMessage(exception.getMessage())
                .build();

        logger.error("Error: {}, Localized Message: {}", exception.getMessage(), exception.getMessage());

        return ResponseEntityUtil.errorDataResponseEntity(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
