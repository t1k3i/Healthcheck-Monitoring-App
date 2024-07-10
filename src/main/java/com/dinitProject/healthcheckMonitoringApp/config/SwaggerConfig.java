package com.dinitProject.healthcheckMonitoringApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Health Check Monitoring Api"))
                .addSecurityItem(new SecurityRequirement().addList("HealthCheckSecurityScheme"))
                .components(new Components().addSecuritySchemes("HealthCheckSecurityScheme", new SecurityScheme()
                        .name("HealthCheckSecurityScheme" +
                                "").type(SecurityScheme.Type.HTTP).scheme("basic")));
    }

}
