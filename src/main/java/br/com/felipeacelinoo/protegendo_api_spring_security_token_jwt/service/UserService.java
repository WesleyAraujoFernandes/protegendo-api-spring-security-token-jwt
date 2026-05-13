package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.service;

import java.util.List;

import org.apache.catalina.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.auth.Role;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.auth.User;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.config.security.SecurityConfiguration;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto.CreateUserDto;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto.LoginUserDto;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto.RecoveryJwtTokenDto;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityConfiguration securityConfiguration;

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginUserDto.email(),
                loginUserDto.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDto createUserDto) {
        User newUser = User.builder()
                .email(createUserDto.email())
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                .roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .build();
        userRepository.save(newUser);
    }
}
