package com.workzone.apioauthintegration.adapter.dto;

import com.workzone.apioauthintegration.infra.config.ApiGatewayConfig;

public class OauthBodyAggregate {

    private final ApiGatewayConfig apiGatewayConfig;

    protected OauthBodyAggregate(ApiGatewayConfig apiGatewayConfig) {
        this.apiGatewayConfig = apiGatewayConfig;
    }

    public OAuthRequest buildGrantCodeBody() {

        return OAuthRequest.builder()
                .clientId(apiGatewayConfig.getClientId())
                .redirectUri("http://localhost")
                .build();
    }

    public OAuthRequest buildRefreshTokenBody(String refreshToken) {

        return OAuthRequest.builder()
                .grantType("refresh_token")
                .refreshToken(refreshToken)
                .build();
    }

    public OAuthRequest buildAccessTokenBody(String code) {

        return OAuthRequest.builder()
                .grantType("authorization_code")
                .code(code)
                .build();
    }
}
