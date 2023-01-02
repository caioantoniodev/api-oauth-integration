package com.workzone.apioauthintegration.adapter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GrantAccessResponse {

    @JsonProperty("redirect_uri")
    private String redirectUri;
}
