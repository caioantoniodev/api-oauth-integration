package com.workzone.apioauthintegration.application;

import com.workzone.apioauthintegration.adapter.dto.OauthBodyAggregate;
import com.workzone.apioauthintegration.adapter.dto.OauthRequest;
import com.workzone.apioauthintegration.adapter.out.OauthFlowAdapterOut;
import com.workzone.apioauthintegration.infra.config.ApiGatewayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Slf4j
public class AuthenticationService implements IAuthenticationService {

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

    @Override
    public String retrieveClientId() {
        return apiGatewayConfig.getClientId();
    }

    @Override
    public String retrieveClientSecret() {
        return apiGatewayConfig.getClientSecret();
    }

    @Override
    public String retrieveAccessToken() {

        if (ACCESS_TOKEN == null)
            this.accessToken();

        if (LocalDateTime.now().plusSeconds(30).isAfter(ACCESS_TOKEN_EXPIRATION))
            this.refreshToken();

        return ACCESS_TOKEN;
    }

    private void accessToken() {
        var grantCode = this.retrieveGrantCode();
        var oauthRequest = oauthBodyAggregate.buildAccessTokenBody(grantCode);
        this.authenticate(oauthRequest);
    }

    private void refreshToken() {
        var oauthRequest = oauthBodyAggregate.buildRefreshTokenBody(REFRESH_TOKEN);
        this.authenticate(oauthRequest);
    }

    private void authenticate(OauthRequest oauthRequest) {

        var headers = new HttpHeaders();

        headers.setContentType(APPLICATION_JSON);
        headers.setBasicAuth(this.retrieveClientId(), this.retrieveClientSecret());

        var accessTokenResponse = oauthFlowAdapterOut.generateAccessToken(headers, oauthRequest);

        ACCESS_TOKEN = Objects.requireNonNull(accessTokenResponse).getAccessToken();
        ACCESS_TOKEN_EXPIRATION = LocalDateTime.now().plusSeconds(
                Objects.requireNonNull(accessTokenResponse.getExpiresIn()));
        REFRESH_TOKEN = Objects.requireNonNull(accessTokenResponse.getRefreshToken());
    }

    private String retrieveGrantCode() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var grantCodeBody = oauthBodyAggregate.buildGrantCodeBody(this.retrieveClientId());
        var grantAccessResponse = oauthFlowAdapterOut.generateGrantCode(headers, grantCodeBody);

        return this.extractGrantCode(grantAccessResponse.getRedirectUri());
    }

    private String extractGrantCode(String redirectUri) {
        return redirectUri.split("=")[1];
    }
}
