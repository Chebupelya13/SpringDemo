package com.example.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Bank System Api",
                description = "API для обработки кредитных заявок",
                version = "1.1.0",
                contact = @Contact(
                        name = "tg => syntax3900"
                )
        )
)
public class OpenApiConfig {

}