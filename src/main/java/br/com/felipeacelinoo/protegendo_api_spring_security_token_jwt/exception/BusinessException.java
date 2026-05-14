package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
