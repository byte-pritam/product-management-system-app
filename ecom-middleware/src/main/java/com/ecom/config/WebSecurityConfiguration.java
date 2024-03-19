package com.ecom.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfiguration  {

    private final JwtRequestFilter requestFilter;


    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        log.error("SecurityFilterChain method executed -> enter ");
       // disabled csrf
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        // disabled cors
       // httpSecurity.cors(AbstractHttpConfigurer::disable);

        // Http Request filter
        httpSecurity.authorizeHttpRequests(
                requestMatcher->
                        requestMatcher.requestMatchers("/authenticate","/sign-up", "/order/**").permitAll()
                                .anyRequest().authenticated()

        );
        // Exception handler
        // Authentication entry point-> exception handler
        httpSecurity.exceptionHandling(
                exceptionConfig-> exceptionConfig.authenticationEntryPoint(authenticationEntryPoint)
        );

        // set session policy -> stateless
        httpSecurity.sessionManagement(
                sessionConfig-> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        // add Jwt authentication filter jwtAuthenticationFilter

        httpSecurity.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
        log.error("SecurityFilterChain method executed -> complete ");
        return httpSecurity.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        log.error("Authentication managaer ");
        return config.getAuthenticationManager();

    }


}
