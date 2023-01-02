package com.workzone.apioauthintegration.application;

import com.workzone.apioauthintegration.adapter.dto.OauthBodyAggregate;
import com.workzone.apioauthintegration.adapter.out.OauthFlowAdapterOut;
import com.workzone.apioauthintegration.infra.config.ApiGatewayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Slf4j
public class AuthenticationService {

    private final OauthFlowAdapterOut oauthFlowAdapterOut;

    private final OauthBodyAggregate oauthBodyAggregate;

    private final ApiGatewayConfig apiGatewayConfig;

    private static String ACCESS_TOKEN;
    private static String REFRESH_TOKEN;
    private static LocalDateTime ACCESS_TOKEN_EXPIRATION;

    public AuthenticationService(OauthFlowAdapterOut oauthFlowAdapterOut,
                                 OauthBodyAggregate oauthBodyAggregate, ApiGatewayConfig apiGatewayConfig) {
        this.oauthFlowAdapterOut = oauthFlowAdapterOut;
        this.oauthBodyAggregate = oauthBodyAggregate;
        this.apiGatewayConfig = apiGatewayConfig;
    }

    public String retrieveToken() {

        if (ACCESS_TOKEN == null)
            this.authenticate();

        if (LocalDateTime.now().plusSeconds(30).isAfter(ACCESS_TOKEN_EXPIRATION))
            this.refreshToken();

        return ACCESS_TOKEN;

    }

    private void authenticate() {

        var headers = new HttpHeaders();

        headers.setContentType(APPLICATION_JSON);
        headers.setBasicAuth(apiGatewayConfig.getClientId(), apiGatewayConfig.getClientSecret());

        var body = oauthBodyAggregate.buildAccessTokenBody(this.retrieveGrantCode());

        var accessTokenResponse = oauthFlowAdapterOut.generateAccessToken(headers, body);

        ACCESS_TOKEN = Objects.requireNonNull(accessTokenResponse).getAccessToken();
        ACCESS_TOKEN_EXPIRATION = LocalDateTime.now().plusSeconds(
                Objects.requireNonNull(accessTokenResponse.getExpiresIn()));
        REFRESH_TOKEN = Objects.requireNonNull(accessTokenResponse.getRefreshToken());
    }

    private void refreshToken() {

        var headers = new HttpHeaders();

        headers.setContentType(APPLICATION_JSON);
        headers.setBasicAuth(apiGatewayConfig.getClientId(), apiGatewayConfig.getClientSecret());

        var body = oauthBodyAggregate.buildRefreshTokenBody(REFRESH_TOKEN);

        var accessTokenResponse = oauthFlowAdapterOut.generateAccessToken(headers, body);

        ACCESS_TOKEN = Objects.requireNonNull(accessTokenResponse).getAccessToken();
        ACCESS_TOKEN_EXPIRATION = LocalDateTime.now().plusSeconds(
                Objects.requireNonNull(accessTokenResponse.getExpiresIn()));
        REFRESH_TOKEN = Objects.requireNonNull(accessTokenResponse.getRefreshToken());
    }

    private String retrieveGrantCode() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var grantAccessResponse = oauthFlowAdapterOut.generateGrantCode(
                headers, oauthBodyAggregate.buildGrantCodeBody());

        return this.extractGrantCode(grantAccessResponse.getRedirectUri());
    }

    private String extractGrantCode(String redirectUri) {
        return redirectUri.split("=")[1];
    }
}
