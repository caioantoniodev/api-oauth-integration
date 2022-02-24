package com.poc.requestoauth.adapters.out;

import com.poc.requestoauth.domain.OauthResponse;
import com.poc.requestoauth.domain.exceptions.OauthException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
@Slf4j
public class OauthExternalService {
    final Logger logger = LoggerFactory.getLogger(OauthExternalService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${sensedia.client_id}")
    private String clientId;

    @Value("${sensedia.client_secret}")
    private String clientSecret;

    @Value("${sensedia.code}")
    private String code;

    public String execute() {
        try {
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
        } catch (Exception exception) {
            throw new OauthException(exception.getMessage());
        }
    }
}
