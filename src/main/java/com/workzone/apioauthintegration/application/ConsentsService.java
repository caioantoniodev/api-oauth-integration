package com.workzone.apioauthintegration.application;

import com.workzone.apioauthintegration.adapter.out.ConsentsAdapterOut;
import com.workzone.apioauthintegration.infra.config.ApiGatewayConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import static com.workzone.apioauthintegration.adapter.out.HeaderNames.ACCESS_TOKEN;
import static com.workzone.apioauthintegration.adapter.out.HeaderNames.CLIENT_ID;

@Service
public class ConsentsService {

    private final ConsentsAdapterOut consentsAdapterOut;

    private final AuthenticationService authenticationService;

    private final ApiGatewayConfig apiGatewayConfig;


    public ConsentsService(ConsentsAdapterOut consentsAdapterOut, AuthenticationService authenticationService, ApiGatewayConfig apiGatewayConfig) {
        this.consentsAdapterOut = consentsAdapterOut;
        this.authenticationService = authenticationService;
        this.apiGatewayConfig = apiGatewayConfig;
    }

    public com.workzone.apioauthintegration.domain.Consents getPayments() {
        return consentsAdapterOut.getConsents(buildRequestHeaders());
    }

    public HttpHeaders buildRequestHeaders() {
        var headers = new HttpHeaders();

        headers.set(CLIENT_ID, apiGatewayConfig.getClientId());
        headers.set(ACCESS_TOKEN, authenticationService.retrieveAccessToken());

        return headers;
    }
}
