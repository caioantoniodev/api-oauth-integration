package com.workzone.apioauthintegration.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Value("${sensedia.api-gateway.clientId}")
    private String clientId;

    @Value("${sensedia.api-gateway.authorizationKey}")
    private String grantCode;

    public String getClientId() {
        return clientId;
    }

    public String getGrantCode() {
        return grantCode;
    }
}
