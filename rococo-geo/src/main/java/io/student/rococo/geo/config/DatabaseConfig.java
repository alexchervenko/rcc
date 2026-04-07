package io.student.rococo.geo.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("io.student.rococo.geo.data.entity")
@EnableJpaRepositories("io.student.rococo.geo.data.repository")
public class DatabaseConfig {
}