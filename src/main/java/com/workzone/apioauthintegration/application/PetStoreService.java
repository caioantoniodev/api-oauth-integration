package com.workzone.apioauthintegration.application;

import com.workzone.apioauthintegration.adapter.out.PetStoreAdapterOut;
import com.workzone.apioauthintegration.domain.Pet;
import com.workzone.apioauthintegration.infra.config.ApiGatewayConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import static com.workzone.apioauthintegration.adapter.out.HeaderNames.ACCESS_TOKEN;
import static com.workzone.apioauthintegration.adapter.out.HeaderNames.CLIENT_ID;

@Service
public class PetStoreService {

    private final PetStoreAdapterOut petStoreAdapterOut;

    private final AuthenticationService authenticationService;

    private final ApiGatewayConfig apiGatewayConfig;


    public PetStoreService(PetStoreAdapterOut petStoreAdapterOut, AuthenticationService authenticationService, ApiGatewayConfig apiGatewayConfig) {
        this.petStoreAdapterOut = petStoreAdapterOut;
        this.authenticationService = authenticationService;
        this.apiGatewayConfig = apiGatewayConfig;
    }

    public Pet getPayments() {
        return petStoreAdapterOut.getPet(buildRequestHeaders(), "1");
    }

    public HttpHeaders buildRequestHeaders() {
        var headers = new HttpHeaders();

        headers.set(CLIENT_ID, apiGatewayConfig.getClientId());
        headers.set(ACCESS_TOKEN, authenticationService.retrieveAccessToken());

        return headers;
    }
}
