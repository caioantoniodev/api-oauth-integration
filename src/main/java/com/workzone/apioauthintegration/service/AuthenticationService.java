package com.workzone.apioauthintegration.service;

import com.workzone.apioauthintegration.entity.ApiGatewayConfig;
import com.workzone.apioauthintegration.dto.OauthRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Retryable(
            value = HttpServerErrorException.class,
            maxAttempts = 4,
            backoff = @Backoff(delay = 2000))
    public String getAccessToken() {

        if (accessToken == null || LocalDateTime.now().plusSeconds(45).isAfter(accessTokenExpiration)) {

            var oauthRequest = new OauthRequest();

            var oAuthResponse = oauthExternal.accessToken(this.buildAccessHeaders(), oauthRequest);

            accessToken = Objects.requireNonNull(oAuthResponse).getAccessToken();
            accessTokenExpiration = LocalDateTime.now().plusSeconds(oAuthResponse.getExpiresIn());
        }

        return accessToken;
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
        headers.set("access_token", this.getAccessToken());

        return headers;
    }
}
