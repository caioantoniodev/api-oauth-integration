package com.workzone.apioauthintegration.service;

import com.workzone.apioauthintegration.config.ApiGatewayConfig;
import com.workzone.apioauthintegration.dto.OAuthRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.workzone.apioauthintegration.entity.HeaderNames.ACCESS_TOKEN;
import static com.workzone.apioauthintegration.entity.HeaderNames.CLIENT_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class AuthenticationService {

    private final ApiGatewayConfig apiGatewayConfig;
    private final OauthExternal oauthExternal;
    private String accessToken;
    private LocalDateTime accessTokenExpiration;

    public AuthenticationService(ApiGatewayConfig apiGatewayConfig, OauthExternal oauthExternal) {
        this.apiGatewayConfig = apiGatewayConfig;
        this.oauthExternal = oauthExternal;
    }

    public String getAccessToken() {

        if (Objects.isNull(accessToken) || LocalDateTime.now().plusSeconds(45).isAfter(accessTokenExpiration)) {
            var oAuthResponse = oauthExternal.accessToken(buildAccessHeaders(), oauthBody());

            accessToken = Objects.requireNonNull(oAuthResponse).getAccessToken();
            accessTokenExpiration = LocalDateTime.now().plusSeconds(oAuthResponse.getExpiresIn());
        }

        return accessToken;
    }

    public OAuthRequest oauthBody() {

        return new OAuthRequest();
    }

    private HttpHeaders buildAccessHeaders() {
        var headers = new HttpHeaders();

        headers.setContentType(APPLICATION_JSON);
        headers.setBasicAuth(apiGatewayConfig.getGrantCode());

        return headers;
    }

    public HttpHeaders buildRequestHeaders() {
        var headers = new HttpHeaders();

        headers.set(CLIENT_ID, apiGatewayConfig.getClientId());
        headers.set(ACCESS_TOKEN, getAccessToken());

        return headers;
    }
}
