package com.workzone.apioauthintegration.adapter.dto;

import com.workzone.apioauthintegration.infra.config.ApiGatewayConfig;
import org.springframework.stereotype.Component;

@Component
public class OauthBodyAggregate {

    private final ApiGatewayConfig apiGatewayConfig;

    protected OauthBodyAggregate(ApiGatewayConfig apiGatewayConfig) {
        this.apiGatewayConfig = apiGatewayConfig;
    }

    public GrantAccessRequest buildGrantCodeBody(String clientId) {

        return GrantAccessRequest.builder()
                .clientId(clientId)
                .redirectUri("http://localhost/oauth")
                .build();
    }

    public OauthRequest buildRefreshTokenBody(String refreshToken) {

        return OauthRequest.builder()
                .grantType("refresh_token")
                .refreshToken(refreshToken)
                .build();
    }

    public OauthRequest buildAccessTokenBody(String code) {

        return OauthRequest.builder()
                .grantType("authorization_code")
                .code(code)
                .build();
    }
}
