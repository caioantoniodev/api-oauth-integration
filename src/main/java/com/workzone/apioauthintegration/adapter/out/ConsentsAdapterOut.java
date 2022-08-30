package com.workzone.apioauthintegration.adapter.out;

import com.workzone.apioauthintegration.domain.Consents;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${sensedia.api-gateway.host}", name = "payment")
public interface ConsentsAdapterOut {

    @RequestMapping(method = RequestMethod.GET, path = "${sensedia.api-gateway.resources.get-payments}")
    Consents getPayments(@RequestHeader HttpHeaders headers);
}
