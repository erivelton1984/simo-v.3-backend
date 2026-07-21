package br.com.ciccr.simo.common.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME = "bearerAuth";

    @Bean
    public OpenAPI simoOpenAPI() {

        return new OpenAPI()

                .info(
                        new Info()

                                .title("SIMO API")

                                .description("""
                                        Sistema Integrado de Monitoramento Operacional

                                        API responsável pelo gerenciamento de atendimentos,
                                        usuários, forças de segurança, tipos de atendimento
                                        e demais módulos do SIMO.
                                        """)

                                .version("1.0.0")

                                .contact(
                                        new Contact()
                                                .name("CICCR")
                                                .email("")
                                )

                                .license(
                                        new License()
                                                .name("SESP/PR")
                                )
                )

                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(SECURITY_SCHEME)
                )

                .components(
                        new Components()
                                .addSecuritySchemes(
                                        SECURITY_SCHEME,
                                        new SecurityScheme()
                                                .name("Authorization")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )

                .externalDocs(
                        new ExternalDocumentation()
                                .description("Documentação do Projeto")
                );
    }

}