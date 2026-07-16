package br.com.ciccr.simo.common.config;

import br.com.ciccr.simo.common.audit.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditConfig {

    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

}