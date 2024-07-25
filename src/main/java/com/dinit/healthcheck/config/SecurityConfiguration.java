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
    private static final String URL_ENDPOINT_EMAILS = "/email";
    private static final String USER_ENDPOINT = "/users";
    private static final String ENDPOINT_ID = "/{id:[0-9]+}";

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
                                // user endpoints
                                .requestMatchers(HttpMethod.POST, USER_ENDPOINT + "/register").hasAuthority(ROLE_ADMIN)
                                .requestMatchers(HttpMethod.GET, USER_ENDPOINT).hasAuthority(ROLE_ADMIN)
                                .requestMatchers(HttpMethod.DELETE, USER_ENDPOINT + ENDPOINT_ID).hasAuthority(ROLE_ADMIN)
                                .requestMatchers(HttpMethod.GET, USER_ENDPOINT + "/exists/**").hasAuthority(ROLE_ADMIN)
                                // Url endpoints
                                .requestMatchers(HttpMethod.DELETE, URL_ENDPOINT).hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                                .requestMatchers(HttpMethod.DELETE, URL_ENDPOINT + ENDPOINT_ID).hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                                .requestMatchers(HttpMethod.POST, URL_ENDPOINT).hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                                .requestMatchers(HttpMethod.PUT, URL_ENDPOINT + ENDPOINT_ID).hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                                .requestMatchers(HttpMethod.PUT, URL_ENDPOINT + "/healthcheck" + ENDPOINT_ID).hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                                .requestMatchers(HttpMethod.PUT, URL_ENDPOINT + "/toggle" + ENDPOINT_ID).hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                                // Email endpoints
                                .requestMatchers(HttpMethod.PUT, URL_ENDPOINT_EMAILS + ENDPOINT_ID + "/emails")
                                .hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                                .requestMatchers(HttpMethod.GET, URL_ENDPOINT_EMAILS + ENDPOINT_ID + "/emails")
                                .hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                                .requestMatchers(HttpMethod.DELETE, URL_ENDPOINT_EMAILS + ENDPOINT_ID + "/emails/**")
                                .hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                                .anyRequest().permitAll())
                .userDetailsService(userDetailsService)
                .httpBasic(httpSecurityHttpBasicConfigurer ->
                        httpSecurityHttpBasicConfigurer.authenticationEntryPoint(authenticationEntryPoint));
        http.addFilterAfter(customFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

}