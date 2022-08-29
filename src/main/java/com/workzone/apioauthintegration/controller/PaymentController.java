package com.workzone.apioauthintegration.controller;

import com.workzone.apioauthintegration.entity.PaymentAuthorization;
import com.workzone.apioauthintegration.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<PaymentAuthorization> getPayments() {

        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPayments());
    }
}
