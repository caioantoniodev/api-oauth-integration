package com.workzone.apioauthintegration.service;

import com.workzone.apioauthintegration.dto.OAuthRequest;
import com.workzone.apioauthintegration.dto.OAuthResponse;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${sensedia.api-gateway.host}", name = "oauth")
public interface OauthExternal {

    @RequestMapping(method = RequestMethod.POST, path = "${sensedia.api-gateway.resources.oauth}")
    @Retryable(
            value = FeignException.class,
            maxAttemptsExpression = "${retry.configurations.maxAttempts}",
            backoff = @Backoff(delay = 2000))
    OAuthResponse accessToken(@RequestHeader HttpHeaders headers, @RequestBody OAuthRequest oAuthRequest);
}
