package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto;

import java.util.List;

import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.auth.Role;

public record RecoveryUserDto(
        Long id,
        String email,
        List<Role> roles) {

}
