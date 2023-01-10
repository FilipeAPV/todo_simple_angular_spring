package com.mytodo.backend.config;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@DataJpaTest
@Configuration
// @EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaAuditing
public class TestConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("system");
    }
    // Needed for Testing to work with @EnableJpaAuditing
}
