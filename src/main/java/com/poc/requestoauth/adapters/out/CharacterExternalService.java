package com.poc.requestoauth.adapters.out;

import com.poc.requestoauth.domain.Character;
import com.poc.requestoauth.domain.exceptions.CharacterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CharacterExternalService {

    final Logger logger = LoggerFactory.getLogger(CharacterExternalService.class);
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private OauthExternalService oauthService;

    @Value("${sensedia.client_id}")
    private String clientId;

    public Character findOneCharacter(String id) {
        try {
            var url = "https://api-demov3.sensedia.com/sandbox/ca1o-4testing/v1/characters/" + id;

            var headers = new HttpHeaders();

            headers.set("client_id", clientId);
            headers.set("access_token", oauthService.execute());

            var requestEntity = new HttpEntity<>(headers);

            var response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Character.class);

            return response.getBody();
        } catch (Exception exception) {
            logger.info("Unexpected Error When find character: {}", exception.getMessage());
            throw new CharacterException(exception.getMessage());
        }
    }
}
