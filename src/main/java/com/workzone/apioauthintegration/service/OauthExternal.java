package com.workzone.apioauthintegration.service;

import com.workzone.apioauthintegration.dto.OAuthResponse;
import com.workzone.apioauthintegration.dto.OauthRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "https://api-demov3.sensedia.com/sandbox/oauth/access-token", name = "oauth")
public interface OauthExternal {

    @RequestMapping(method = RequestMethod.POST)
    OAuthResponse accessToken(@RequestHeader HttpHeaders headers, @RequestBody OauthRequest httpEntity);
}
