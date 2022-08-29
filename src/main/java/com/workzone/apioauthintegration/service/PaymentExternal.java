package com.workzone.apioauthintegration.service;

import com.workzone.apioauthintegration.entity.PaymentAuthorization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${sensedia.api-gateway.host}", name = "payment")
public interface PaymentExternal {

    @RequestMapping(method = RequestMethod.GET, path = "${sensedia.api-gateway.resources.get-payments}")
    PaymentAuthorization getPayments(@RequestHeader HttpHeaders headers);
}
