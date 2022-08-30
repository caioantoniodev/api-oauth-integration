package com.workzone.apioauthintegration.adapter.in;

import com.workzone.apioauthintegration.application.ConsentsService;
import com.workzone.apioauthintegration.domain.Consents;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/opb/v1/consents")
public class ConsentsController {

    private final ConsentsService consentsService;

    public ConsentsController(ConsentsService consentsService) {
        this.consentsService = consentsService;
    }

    @GetMapping
    public ResponseEntity<Consents> getPayments() {

        return ResponseEntity.status(HttpStatus.OK).body(consentsService.getPayments());
    }
}
