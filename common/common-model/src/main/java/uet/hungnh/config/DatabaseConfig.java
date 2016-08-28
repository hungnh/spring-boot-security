package uet.hungnh.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "uet.hungnh")
@EnableJpaRepositories(basePackages = "uet.hungnh")
public class DatabaseConfig {
}
