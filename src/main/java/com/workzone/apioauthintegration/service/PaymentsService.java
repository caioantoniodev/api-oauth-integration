package com.workzone.apioauthintegration.service;

import com.workzone.apioauthintegration.entity.PaymentAuthorization;
import org.springframework.stereotype.Service;

@Service
public class PaymentsService {

    private final PaymentExternal paymentExternal;
    private final AuthenticationService authenticationService;


    public PaymentsService(PaymentExternal paymentExternal, AuthenticationService authenticationService) {
        this.paymentExternal = paymentExternal;
        this.authenticationService = authenticationService;
    }

    public PaymentAuthorization getPayments() {
        return paymentExternal.getPayments(authenticationService.buildRequestHeaders());
    }
}
