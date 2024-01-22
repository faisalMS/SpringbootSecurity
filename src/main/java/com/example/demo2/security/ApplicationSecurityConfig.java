package com.example.demo2.security;



import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.demo2.security.ApplicationUserRole.ADMIN;
import static com.example.demo2.security.ApplicationUserRole.ADMINTRAINEE;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationSecurityConfig {



    private final PasswordEncoder passwordEncoder;

    @Bean
    public  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .requestMatchers("/", "index", "/css/**", "/js/**")
                .permitAll()
                .requestMatchers("/api/v1/students/**")
                .permitAll().anyRequest().authenticated().and()
                .httpBasic();
        return http.build();
    }

        @Bean
        public InMemoryUserDetailsManager userDetailsService() {

        // Student
            UserDetails mohmmedUser = User.builder()
                    .username("mohmmed")
                    .password(passwordEncoder.encode("password"))
                    .roles("STUDENT") // ROLE_STUDENT
                    .build();

        // Admin
            UserDetails faisalFMSUser = User.builder()
                    .username("faisalFMS")
                    .password(passwordEncoder.encode("password123"))
                    .roles(ADMIN.name())
                    .build();

            // Admin
            UserDetails marwanUser = User.builder()
                    .username("marwan")
                    .password(passwordEncoder.encode("password123"))
                    .roles(ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE
                    .build();

            return new InMemoryUserDetailsManager(
                    mohmmedUser,
                    faisalFMSUser,
                    marwanUser
            );
        }

    }