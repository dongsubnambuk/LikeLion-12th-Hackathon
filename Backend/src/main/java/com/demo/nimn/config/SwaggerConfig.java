package com.demo.nimn.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .addServersItem(new io.swagger.v3.oas.models.servers.Server()
                        .url("https://nimn.store")
                        .description("Production server"))
                .addServersItem(new io.swagger.v3.oas.models.servers.Server()
                        .url("http://localhost:8080")
                        .description("Local development server"));
    }

    private Info apiInfo() {
        return new Info()
                .title("우리동네 영양사")
                .description("우리동네 영양사 API Docs")
                .version("1.0.0");
    }
}