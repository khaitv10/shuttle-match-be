package com.example.shuttlematch.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${project.openapi.dev-url}")
    private String devUrl;

    @Value("${project.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        // Tạo server cho môi trường phát triển (development)
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        // Tạo server cho môi trường sản xuất (production)
        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        // Thông tin liên hệ
        Contact contact = new Contact();
        contact.setEmail("khaitranvu1011@gmail.com");
        contact.setName("Tran Vu Khai");

        // License
        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        // Thông tin API
        Info info = new Info()
                .title("PROJECT Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage project.")
                .termsOfService("https://www.google.com")
                .license(mitLicense);

        // Cấu hình bảo mật Bearer JWT
        SecurityScheme securitySchemeBear = new SecurityScheme()
                .description("Security authorization bearer")
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");

        // Thêm các server vào OpenAPI
        return new OpenAPI()
                .components(
                        new Components().addSecuritySchemes("Authorization", securitySchemeBear))
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList("Authorization", Arrays.asList("read", "write")))
                .servers(List.of(devServer, prodServer)); // Thêm cả devServer và prodServer vào danh sách server
    }
}
