package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.auth.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
