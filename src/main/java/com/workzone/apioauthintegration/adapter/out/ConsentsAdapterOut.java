package com.workzone.apioauthintegration.adapter.out;

import com.workzone.apioauthintegration.domain.Consents;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${sensedia.api-gateway.host}", name = "consent")
public interface ConsentsAdapterOut {

    @RequestMapping(method = RequestMethod.GET, path = "${sensedia.api-gateway.resources.get-payments}")
    @Retryable(
            value = FeignException.FeignServerException.class,
            maxAttemptsExpression = "${retry.configurations.maxAttempts}",
            backoff = @Backoff(delay = 2000))
    Consents getConsents(@RequestHeader HttpHeaders headers);
}
