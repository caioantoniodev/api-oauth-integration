package com.workzone.apioauthintegration.application;

import com.workzone.apioauthintegration.adapter.dto.OAuthRequest;
import com.workzone.apioauthintegration.adapter.out.OauthFlowAdapterOut;
import com.workzone.apioauthintegration.infra.config.ApiGatewayConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.workzone.apioauthintegration.adapter.out.HeaderNames.ACCESS_TOKEN;
import static com.workzone.apioauthintegration.adapter.out.HeaderNames.CLIENT_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class AuthenticationService {

    private final ApiGatewayConfig apiGatewayConfig;

    private final OauthFlowAdapterOut oauthFlowAdapterOut;

    private String accessToken;

    private String refreshToken;

    private LocalDateTime accessTokenExpiration;

    public AuthenticationService(ApiGatewayConfig apiGatewayConfig, OauthFlowAdapterOut oauthFlowAdapterOut) {
        this.apiGatewayConfig = apiGatewayConfig;
        this.oauthFlowAdapterOut = oauthFlowAdapterOut;
    }

    public String getAccessToken() {

        if (Objects.isNull(accessToken)) {
            var grantCodeResponse = oauthFlowAdapterOut.generateGrantType(buildGrantCodeBody());

            var oAuthResponse = oauthFlowAdapterOut.generateAccessToken(buildAccessHeaders(),
                            buildAccessTokenBody(grantCodeResponse.getRedirectUri().split("=")[1]));

            accessToken = Objects.requireNonNull(oAuthResponse).getAccessToken();
            refreshToken = Objects.requireNonNull(oAuthResponse).getRefreshToken();
            accessTokenExpiration = LocalDateTime.now().plusSeconds(oAuthResponse.getExpiresIn());
        }

        if (LocalDateTime.now().isAfter(accessTokenExpiration)) {
            var oAuthResponse = oauthFlowAdapterOut.generateAccessToken(buildAccessHeaders(),
                    buildRefreshTokenBody(refreshToken));

            accessToken = Objects.requireNonNull(oAuthResponse).getAccessToken();
            refreshToken = Objects.requireNonNull(oAuthResponse).getRefreshToken();
            accessTokenExpiration = LocalDateTime.now().plusSeconds(oAuthResponse.getExpiresIn());
        }

        return accessToken;
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
    private HttpHeaders buildAccessHeaders() {
        var headers = new HttpHeaders();

        headers.setContentType(APPLICATION_JSON);
        headers.setBasicAuth(apiGatewayConfig.getClientId(), apiGatewayConfig.getClientSecret());

        return headers;
    }

    public HttpHeaders buildRequestHeaders() {
        var headers = new HttpHeaders();

        headers.set(CLIENT_ID, apiGatewayConfig.getClientId());
        headers.set(ACCESS_TOKEN, getAccessToken());

        return headers;
    }
}
