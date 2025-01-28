package br.com.security.keycloak.services.impl;

import br.com.security.keycloak.componentes.HttpComponent;
import br.com.security.keycloak.domain.User;
import br.com.security.keycloak.services.ILoginService;
import br.com.security.keycloak.utils.HttpParamsMapBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;


@Service
public class LoginServiceImpl implements ILoginService<String> {

    @Value("${keycloak.auth-server-url}")
    private String keyCloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.user-login.grant-type}")
    private String grantType;

    private final HttpComponent httpComponent;

    public LoginServiceImpl(HttpComponent httpComponent) {
        this.httpComponent = httpComponent;
    }

    @Override
    public ResponseEntity<String> login(User user) {
        httpComponent.httpHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var map = HttpParamsMapBuilder.builder()
                .withClient(clientId)
                .withClientSecret(clientSecret)
                .withGrantType(grantType)
                .withUsername(user.getUsername())
                .withPassword(user.getPassword())
                .build();


        var request = new HttpEntity<>(map, httpComponent.httpHeaders());

        try {

            var response = httpComponent.restTemplate().postForEntity(
                    keyCloakServerUrl + "/protocol/openid-connect/token",
                    request,
                    String.class
            );

            return ResponseEntity.ok(response.getBody());

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> refreshToken(String refreshToken) {

        httpComponent.httpHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var map = HttpParamsMapBuilder.builder()
                .withClient(clientId)
                .withClientSecret(clientSecret)
                .withGrantType("refresh_token")
                .withRefreshToken(refreshToken)
                .build();


        var request = new HttpEntity<>(map, httpComponent.httpHeaders());

        try {

            var response = httpComponent.restTemplate().postForEntity(
                    keyCloakServerUrl + "/protocol/openid-connect/token",
                    request,
                    String.class
            );


            return ResponseEntity.ok(response.getBody());

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());

        }
    }
}
