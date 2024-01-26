package com.example.demo2.security;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import java.util.concurrent.TimeUnit;

import static com.example.demo2.security.ApplicationUserRole.*;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {



    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers("/", "index", "/css/**", "/js/**")
                .permitAll()
                .requestMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/fmscode", true)
                    .passwordParameter("password")
                    .usernameParameter("username")
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("somthingverysecured")
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                   .logoutUrl("/logout")
                   .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                   .clearAuthentication(true)
                   .invalidateHttpSession(true)
                   .deleteCookies("JSESSIONID", "remember-me")
                   .logoutSuccessUrl("/login");
        return http.build();
    }

        @Bean
        public InMemoryUserDetailsManager userDetailsService() {

        // Student
            UserDetails mohmmedUser = User.builder()
                    .username("Mohmmed")
                    .password(passwordEncoder.encode("password"))
//                    .roles("STUDENT") // ROLE_STUDENT
                    .authorities(STUDENT.getGrantedAuthority())
                    .build();

        // Admin
            UserDetails faisalFMSUser = User.builder()
                    .username("Faisal")
                    .password(passwordEncoder.encode("password123"))
//                    .roles(ADMIN.name()) // ROLE_ADMIN
                    .authorities(ADMIN.getGrantedAuthority())
                    .build();

            // Admin
            UserDetails marwanUser = User.builder()
                    .username("Marwan")
                    .password(passwordEncoder.encode("password123"))
//                    .roles(ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE
                    .authorities(ADMINTRAINEE.getGrantedAuthority())
                    .build();

            return new InMemoryUserDetailsManager(
                    mohmmedUser,
                    faisalFMSUser,
                    marwanUser
            );
        }

    }