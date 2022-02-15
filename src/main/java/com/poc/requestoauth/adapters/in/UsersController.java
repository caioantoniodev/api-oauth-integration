package com.poc.requestoauth.adapters.in;

import com.poc.requestoauth.adapters.out.Oauth;
import com.poc.requestoauth.domain.OauthResponse;
import com.poc.requestoauth.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/v1/users")
public class UsersController {

    @Value("${sensedia.client_id}")
    private String clientId;

    private final Oauth oauth;
    private final RestTemplate restTemplate;

    public UsersController(Oauth oauth, @Autowired RestTemplateBuilder builder) {
        this.oauth = oauth;
        this.restTemplate = builder.build();
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        var headers = new HttpHeaders();

        String code = "24547d14-f8ec-3890-b63e-ae48f59e7bc8";

        headers.set("client_id", clientId);
        headers.set("access_token",oauth.execute());

        var requestEntity = new HttpEntity<>(headers);

        var response = restTemplate.exchange("https://api-demov3.sensedia.com/sandbox/user-api/v1/users", HttpMethod.GET, requestEntity, Users.class);

        return ResponseEntity.ok(response.getBody());
    }
}
