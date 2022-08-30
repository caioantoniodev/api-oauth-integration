package com.workzone.apioauthintegration.infra.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ApiGatewayConfig {

    @Value("${sensedia.api-gateway.client-id}")
    private String clientId;

    @Value("${sensedia.api-gateway.client-secret}")
    private String clientSecret;

    @Value("${sensedia.api-gateway.authorization-key}")
    private String grantCode;
}
