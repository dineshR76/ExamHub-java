package com.ExamHub.ExamHub;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Exam Hub REST API Documentation",
                description = "Exam Hub REST API Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Dinesh, Abul",
                        email = "rdinesh709@gmail.com, ahsanabulala@gmail.com"
                ),
                license = @License(
                        name = "Apache 2.0"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Exam Hub Notion Documentation",
                url = "https://www.notion.so/Exam-Hub-Application-779daa08b8ad43f9bb9a7262e67896d0"
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class ExamHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamHubApplication.class, args);
    }

}
