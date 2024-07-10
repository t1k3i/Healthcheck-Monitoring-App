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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

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
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(expressionInterceptUrlRegistry -> expressionInterceptUrlRegistry
                                .requestMatchers(HttpMethod.DELETE, "/urls").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/urls/{id:[0-9]+}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/urls").hasRole("USER")
                                .requestMatchers(HttpMethod.PUT, "/urls/{id:[0-9]+}").hasRole("USER")
                                .requestMatchers("/urls/{id:[0-9]+}").hasRole("USER")
                                .requestMatchers(getOpenedResources()).permitAll()
                                .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults());
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
                "/urls"
        };
    }

}