package com.example.demo2.security;



import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class ApplicationSecurityConfig {


    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/api/v1/students/**", "/css/*", "/js/*")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                );

    return http.build();
    }

        @Bean
        public InMemoryUserDetailsManager userDetailsService() {

        // Student
            UserDetails Mohmmmed = User.builder()
                    .username("Mohmmmed")
                    .password(passwordEncoder.encode("password"))
                    .roles("STUDENT")
                    .build();

        // Admin
            UserDetails faisalFMS = User.builder()
                    .username("faisalFMS")
                    .password(passwordEncoder.encode("password123"))
                    .roles("ADMIN")
                    .build();

            return new InMemoryUserDetailsManager(
                    Mohmmmed,
                    faisalFMS
            );
        }

    }