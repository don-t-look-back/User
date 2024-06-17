package com.example.dlbuser.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    private final String IDFV_TOKEN_HEADER = "Authorization";

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = { "/api/**" };

        return GroupedOpenApi.builder()
                .group("vision counting main web API v1")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info().version("v1")
                .title("vision counting main web API v1")
                .description("vision counting main web API v1");

        SecurityScheme bearerAuth = new SecurityScheme().type(SecurityScheme.Type.APIKEY).name(IDFV_TOKEN_HEADER).in(SecurityScheme.In.HEADER);
        SecurityRequirement securityItem = new SecurityRequirement().addList(IDFV_TOKEN_HEADER);

        return new OpenAPI()
                .components(new Components().addSecuritySchemes(IDFV_TOKEN_HEADER, bearerAuth))
                .info(info)
                .addSecurityItem(securityItem);
                //.components(components);
    }
}
