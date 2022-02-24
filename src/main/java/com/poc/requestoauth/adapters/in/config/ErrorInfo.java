package com.poc.requestoauth.adapters.in.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorInfo {
    @JsonProperty("error_message")
    private String errorMessage;

    private ErrorInfo() {
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private String message;

        private Builder() {
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ErrorInfo build() {
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.errorMessage = this.message;
            return errorInfo;
        }
    }
}
