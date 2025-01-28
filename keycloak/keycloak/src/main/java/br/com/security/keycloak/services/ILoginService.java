package br.com.security.keycloak.services;

import br.com.security.keycloak.domain.User;
import org.springframework.http.ResponseEntity;

public interface ILoginService <T> {

    ResponseEntity<T> login(User user);
}
