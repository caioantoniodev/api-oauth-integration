package com.poc.requestoauth.adapters.in.config.util;

import com.poc.requestoauth.adapters.in.config.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtil {


    private ResponseEntityUtil() {
    }

    public static ResponseEntity<ErrorInfo> errorDataResponseEntity(ErrorInfo errorInfo, HttpStatus status) {
        return ResponseEntity.status(status).body(errorInfo);
    }
}
