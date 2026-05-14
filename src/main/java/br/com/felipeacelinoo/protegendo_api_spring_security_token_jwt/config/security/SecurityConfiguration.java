package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.config.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

        @Autowired
        private UserAuthenticationFilter userAuthenticationFilter;

        public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
                        "/users/login",
                        "/users"
        };

        public static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
                        "/categories/**", "/products/**"
        };

        public static final String[] ENDPOINTS_CUSTOMER = {
                        "/categories/**", "/products/**"
        };

        public static final String[] ENDPOINTS_ADMIN = {
                        "/categories/**", "/products/**"
        };

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(t -> t.disable())
                                .sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                                .authorizeHttpRequests(t -> t
                                                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                                                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                                                .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR")
                                                .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("CUSTOMER")
                                                .anyRequest().denyAll())
                                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                http.cors(Customizer.withDefaults());
                return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("http://localhost:4200"));
                configuration.setAllowedMethods(List.of("*"));
                configuration.setAllowedHeaders(List.of("*"));
                configuration.setAllowCredentials(true);
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}
