package com.dinit.healthcheck.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomFilter customFilter;
    private final UserDetailsService userDetailsService;

    private static final String URL_ENDPOINT = "/urls";
    private static final String URL_ENDPOINT_EX = "/{id:[0-9]+}";
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";

    public SecurityConfiguration(RestAuthenticationEntryPoint authenticationEntryPoint,
                                 CustomFilter customFilter, UserDetailsService userDetailsService) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.customFilter = customFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(expressionInterceptUrlRegistry -> expressionInterceptUrlRegistry
                                .requestMatchers(HttpMethod.DELETE, URL_ENDPOINT).hasAuthority(ROLE_ADMIN)
                                .requestMatchers(HttpMethod.DELETE, URL_ENDPOINT + URL_ENDPOINT_EX).hasAuthority(ROLE_ADMIN)
                                .requestMatchers(HttpMethod.POST, URL_ENDPOINT).hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                                .requestMatchers(HttpMethod.PUT, URL_ENDPOINT + URL_ENDPOINT_EX).hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                                .requestMatchers(HttpMethod.PUT, URL_ENDPOINT + URL_ENDPOINT_EX + "/addEmail")
                                .hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                                .requestMatchers(getOpenedResources()).permitAll()
                                .anyRequest().permitAll())
                .userDetailsService(userDetailsService)
                .httpBasic(httpSecurityHttpBasicConfigurer ->
                        httpSecurityHttpBasicConfigurer.authenticationEntryPoint(authenticationEntryPoint));
        http.addFilterAfter(customFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

    private String[] getOpenedResources() {
        return new String[]{
                "/swagger-ui/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/v3/api-docs",
                "/v3/api-docs/**",
                URL_ENDPOINT,
                URL_ENDPOINT + URL_ENDPOINT_EX,
                "/users/authenticate"
        };
    }

}