package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
