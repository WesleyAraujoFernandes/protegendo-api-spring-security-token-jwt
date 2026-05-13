package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(@NotBlank String name) {

}
