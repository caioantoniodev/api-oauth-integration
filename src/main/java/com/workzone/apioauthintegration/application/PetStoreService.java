package com.workzone.apioauthintegration.application;

import com.workzone.apioauthintegration.adapter.out.PetStoreAdapterOut;
import com.workzone.apioauthintegration.domain.Pet;
import com.workzone.apioauthintegration.infra.config.ApiGatewayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import static com.workzone.apioauthintegration.adapter.out.HeaderNames.ACCESS_TOKEN;
import static com.workzone.apioauthintegration.adapter.out.HeaderNames.CLIENT_ID;

@Service
@Slf4j
public class PetStoreService {

    private final PetStoreAdapterOut petStoreAdapterOut;
    private final IAuthenticationService authenticationService;

    public PetStoreService(PetStoreAdapterOut petStoreAdapterOut, IAuthenticationService authenticationService) {
        this.petStoreAdapterOut = petStoreAdapterOut;
        this.authenticationService = authenticationService;
    }

    public Pet getPet() {

        log.warn("get pet flow; start;");

        var pet = petStoreAdapterOut.getPet(buildRequestHeaders(), "1");

        log.warn("get pet flow; end; retrieved {}", pet);

        return pet;
    }

    public HttpHeaders buildRequestHeaders() {
        var headers = new HttpHeaders();
        headers.set(CLIENT_ID, authenticationService.retrieveClientId());
        headers.set(ACCESS_TOKEN, authenticationService.retrieveAccessToken());

        return headers;
    }
}
