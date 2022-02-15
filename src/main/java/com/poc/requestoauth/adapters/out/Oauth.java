package com.poc.requestoauth.adapters.out;

import com.poc.requestoauth.domain.OauthResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Service
public class Oauth {
    final Logger logger = LoggerFactory.getLogger(Oauth.class);
    final RestTemplate restTemplate;

    @Value("${sensedia.client_id}")
    private String clientId;
    @Value("${sensedia.client_secret}")
    private String clientSecret;
    @Value("${sensedia.code}")
    private String code;

    protected Oauth(@Autowired RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public String execute() {
        log.info("generate-access-token; start;");

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", code);

        var map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");

        var requestEntity = new HttpEntity<>(map, headers);

        var response = restTemplate.exchange("https://api-demov3.sensedia.com/dev/oauth/access-token", HttpMethod.POST, requestEntity, OauthResponse.class);

        log.info(Objects.requireNonNull(response.getBody()).getAccessToken());

        return response.getBody().getAccessToken();
    }
}
