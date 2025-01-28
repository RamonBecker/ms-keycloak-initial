package br.com.security.keycloak.web;

import br.com.security.keycloak.domain.User;
import br.com.security.keycloak.services.impl.LoginServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AuthController {

    @Autowired
    private LoginServiceImpl loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody User user) {

        return loginService.login(user);
    }
}
