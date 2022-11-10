package com.example.JobAssignmentAPI.config;

import com.example.JobAssignmentAPI.security.security.Jwt.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@EnableWebSecurity
public class WebSecurityConfig  {

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors() // already set cors configuration in WebMvcConfig
                .and()
                .csrf()// csrf is not being used
                .disable()
                .httpBasic()// token is used, so basic auth is disabled
                .disable()
                .sessionManagement()  // not session reliable
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // path "/" and "/auth/**" no need to authorise
                .antMatchers("/","/auth/**","/api/**" ,"/swagger-resources/**","/v2/api-docs/**", "/swagger-ui/index.html", "/swagger-ui.html","/webjars/**", "/swagger/**").permitAll()
                .anyRequest() // any other paths besides path "/" and "/auth/**"  need to be authenticated.
                .authenticated();
        http.addFilterAfter(
                jwtAuthenticationFilter,
                CorsFilter.class
        );
        return http.build();
    }


}
