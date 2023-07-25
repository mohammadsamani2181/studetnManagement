package com.studentManagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "mohammad samani",
                        email = "hdhd5mohammad@gmail.com"
                ),
                description = "OpenApi documentation for sample school",
                title = "Sample school OpenApi",
                version = "1.0",
                license = @License(
                        name = "license name"
                ),
                termsOfService = "https://t.me/mmds1121"
        ),
        servers = {
                @Server(
                        description = "local environment",
                        url = "http://localhost:3030"
                )
//                @Server(
//                        description = "Pod authorize api",
//                        url = "https://accounts.pod.ir/oauth2/authorize"
//                ),
//                @Server(
//                        description = "Pod get user token api",
//                        url = "https://accounts.pod.ir/oauth2/token"
//                ),
//                @Server(
//                        description = "Pod get token information api",
//                        url = "https://api.pod.ir/srv/core"
//                )
        }
)
@SecurityScheme(
        name = "Pod Token",
        description = "Authorize with Pod services",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
