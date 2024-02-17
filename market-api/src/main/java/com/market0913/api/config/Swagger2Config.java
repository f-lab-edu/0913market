package com.market0913.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger2Config {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Market API")
                .description("공구일상 마켓 API 문서")
                .contact(new Contact().email("bbomi0913@gmail.com"))
                .version("1.0.0");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
