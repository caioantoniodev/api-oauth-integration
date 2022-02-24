package com.poc.requestoauth.adapters.in.config;

import com.poc.requestoauth.adapters.in.config.util.ResponseEntityUtil;
import com.poc.requestoauth.domain.exceptions.CharacterException;
import com.poc.requestoauth.domain.exceptions.OauthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({CharacterException.class})
    public ResponseEntity<ErrorInfo> handleCharacterException(Exception exception) {
        ErrorInfo errorInfo = ErrorInfo.builder()
                .withMessage(exception.getMessage())
                .build();

        logger.error("Error: {}, Localized Message: {}", exception.getMessage(), exception.getLocalizedMessage());

        return ResponseEntityUtil.errorDataResponseEntity(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({OauthException.class})
    public ResponseEntity<ErrorInfo> handleOauthException(Exception exception) {
        ErrorInfo errorInfo = ErrorInfo.builder()
                .withMessage(exception.getMessage())
                .build();

        logger.error("Error: {}, Localized Message: {}", exception.getMessage(), exception.getLocalizedMessage());

        return ResponseEntityUtil.errorDataResponseEntity(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
