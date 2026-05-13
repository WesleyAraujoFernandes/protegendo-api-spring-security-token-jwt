package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.auth.User;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.repository.UserRepository;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.service.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserDetailsImpl(user);
    }
}
