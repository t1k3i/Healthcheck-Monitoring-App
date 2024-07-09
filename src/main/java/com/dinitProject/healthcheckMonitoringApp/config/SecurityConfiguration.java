package com.dinitProject.healthcheckMonitoringApp.config;

import com.dinitProject.healthcheckMonitoringApp.services.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomFilter customFilter;

    public SecurityConfiguration(RestAuthenticationEntryPoint authenticationEntryPoint,
                                 CustomFilter customFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.customFilter = customFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password(PasswordEncoderConfig.passwordEncoder().encode("user"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(PasswordEncoderConfig.passwordEncoder().encode("admin"))
                .roles("ADMIN");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(expressionInterceptUrlRegistry -> expressionInterceptUrlRegistry
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                                .requestMatchers("/urls").permitAll()
                                .requestMatchers(HttpMethod.POST, "/urls").hasRole("USER")
                                .requestMatchers("/urls/{id:[0-9]+}").hasRole("USER")
                                .requestMatchers(HttpMethod.PUT, "/urls/{id:[0-9]+}").hasRole("USER")
                                .anyRequest().hasRole("ADMIN"))
                .httpBasic(Customizer.withDefaults());
        http.addFilterAfter(customFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

}