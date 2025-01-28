package br.com.security.keycloak.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

    @NotBlank(message = "Username is required")
    private String username;
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;
}
