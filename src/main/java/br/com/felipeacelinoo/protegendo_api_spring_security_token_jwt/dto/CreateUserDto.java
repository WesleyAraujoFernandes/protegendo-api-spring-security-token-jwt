package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto;

import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.enums.RoleName;

public record CreateUserDto(
        String email,
        String password,
        RoleName role) {

}
