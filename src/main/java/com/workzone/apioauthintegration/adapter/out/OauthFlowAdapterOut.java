package com.workzone.apioauthintegration.adapter.out;

import com.workzone.apioauthintegration.adapter.dto.GrantCodeResponse;
import com.workzone.apioauthintegration.adapter.dto.OAuthRequest;
import com.workzone.apioauthintegration.adapter.dto.OAuthResponse;
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
public interface OauthFlowAdapterOut {

    @RequestMapping(method = RequestMethod.POST, path = "${sensedia.api-gateway.resources.grant-code}")
    @Retryable(
            value = FeignException.FeignServerException.class,
            maxAttemptsExpression = "${retry.configurations.maxAttempts}",
            backoff = @Backoff(delay = 2000))
    GrantCodeResponse generateGrantType(@RequestBody OAuthRequest oAuthRequest);

    @RequestMapping(method = RequestMethod.POST, path = "${sensedia.api-gateway.resources.oauth}")
    @Retryable(
            value = FeignException.FeignServerException.class,
            maxAttemptsExpression = "${retry.configurations.maxAttempts}",
            backoff = @Backoff(delay = 2000))
    OAuthResponse generateAccessToken(@RequestHeader HttpHeaders headers, @RequestBody OAuthRequest oAuthRequest);
}
