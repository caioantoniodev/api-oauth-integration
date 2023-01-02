package com.workzone.apioauthintegration.adapter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OauthRequest {

    @JsonProperty("code")
    private String code;

    @JsonProperty("grant_type")
    private String grantType;

    @JsonProperty("refresh_token")
    private String refreshToken;
}
