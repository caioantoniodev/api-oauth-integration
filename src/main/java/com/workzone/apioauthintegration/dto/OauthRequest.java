package com.workzone.apioauthintegration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OauthRequest {

    @JsonProperty("grant_type")
    private final String grantType = "client_credentials";
}
