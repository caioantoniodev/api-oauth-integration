package com.workzone.apioauthintegration.adapter.in;

import com.workzone.apioauthintegration.application.PaymentService;
import com.workzone.apioauthintegration.domain.Consents;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payments")
public class ConsentsAdapterIn {

    private final PaymentService paymentService;

    public ConsentsAdapterIn(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<Consents> getPayments() {

        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPayments());
    }
}
