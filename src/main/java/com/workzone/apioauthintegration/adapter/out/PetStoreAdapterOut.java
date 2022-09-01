package com.workzone.apioauthintegration.adapter.out;

import com.workzone.apioauthintegration.domain.Pet;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${sensedia.api-gateway.host}", name = "consent")
public interface PetStoreAdapterOut {

    @RequestMapping(method = RequestMethod.GET, path = "${sensedia.api-gateway.resources.get-pet}")
    @Retryable(
            value = FeignException.FeignServerException.class,
            maxAttemptsExpression = "${retry.configurations.maxAttempts}",
            backoff = @Backoff(delay = 2000))
    Pet getPet(@RequestHeader HttpHeaders headers, @PathVariable("petId") String petId);
}
