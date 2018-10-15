package com.mitrais.carrot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * jpa auditing configuration
 *
 */
@Configuration
@EnableJpaAuditing
public class AuditingConfig {

    /**
     * provide class audit security
     *
     * @return AuditorAware Long object
     */
    @Bean
    public AuditorAware<Long> auditorProvider() {
        return new SpringSecurityAuditAwareImpl();
    }
}
