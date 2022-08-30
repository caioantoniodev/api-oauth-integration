package com.workzone.apioauthintegration.application;

import com.workzone.apioauthintegration.adapter.out.ConsentsAdapterOut;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final ConsentsAdapterOut consentsAdapterOut;
    private final AuthenticationService authenticationService;


    public PaymentService(ConsentsAdapterOut consentsAdapterOut, AuthenticationService authenticationService) {
        this.consentsAdapterOut = consentsAdapterOut;
        this.authenticationService = authenticationService;
    }

    public com.workzone.apioauthintegration.domain.Consents getPayments() {
        var headers = authenticationService.buildRequestHeaders();
        return consentsAdapterOut.getPayments(headers);
    }
}
