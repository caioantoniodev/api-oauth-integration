package com.workzone.apioauthintegration.application;

import com.workzone.apioauthintegration.adapter.dto.OauthBodyAggregate;
import com.workzone.apioauthintegration.adapter.out.OauthFlowAdapterOut;
import com.workzone.apioauthintegration.infra.config.ApiGatewayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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

    private String accessToken;

    private String refreshToken;

    private LocalDateTime accessTokenExpiration;

    public AuthenticationService(OauthFlowAdapterOut oauthFlowAdapterOut,
                                 OauthBodyAggregate oauthBodyAggregate, ApiGatewayConfig apiGatewayConfig) {
        this.oauthFlowAdapterOut = oauthFlowAdapterOut;
        this.oauthBodyAggregate = oauthBodyAggregate;
        this.apiGatewayConfig = apiGatewayConfig;
    }

    public String retrieveAccessToken() {

        log.warn("oauth2 flow; start;");
        if (Objects.isNull(accessToken)) {

            var grantCodeResponse = oauthFlowAdapterOut.generateGrantType(oauthBodyAggregate.buildGrantCodeBody());

            log.warn("retrieve grant-code;");

            var oAuthResponse = oauthFlowAdapterOut.generateAccessToken(buildAccessHeaders(),
                    oauthBodyAggregate.buildAccessTokenBody(grantCodeResponse.getRedirectUri().split("=")[1]));

            log.warn("retrieve access-token; expiresIn=\"{} seconds\"; message=\"after {} we generate refresh-token by call\";",
                    oAuthResponse.getExpiresIn(),
                    LocalDateTime.now().plusSeconds(oAuthResponse.getExpiresIn()));

            accessToken = Objects.requireNonNull(oAuthResponse).getAccessToken();
            refreshToken = Objects.requireNonNull(oAuthResponse).getRefreshToken();
            accessTokenExpiration = LocalDateTime.now().plusSeconds(oAuthResponse.getExpiresIn());
        }

        if (LocalDateTime.now().isAfter(accessTokenExpiration)) {
            var oAuthResponse = oauthFlowAdapterOut.generateAccessToken(buildAccessHeaders(),
                    oauthBodyAggregate.buildRefreshTokenBody(refreshToken));

            log.warn("retrieve refresh-token; expiresIn=\"{} seconds\";", oAuthResponse.getExpiresIn());

            accessToken = Objects.requireNonNull(oAuthResponse).getAccessToken();
            refreshToken = Objects.requireNonNull(oAuthResponse).getRefreshToken();
            accessTokenExpiration = LocalDateTime.now().plusSeconds(oAuthResponse.getExpiresIn());
        }

        log.warn("oauth2 flow; end; success;");

        return accessToken;
    }

    public HttpHeaders buildAccessHeaders() {
        var headers = new HttpHeaders();

        headers.setContentType(APPLICATION_JSON);
        headers.setBasicAuth(apiGatewayConfig.getClientId(), apiGatewayConfig.getClientSecret());

        return headers;
    }
}
