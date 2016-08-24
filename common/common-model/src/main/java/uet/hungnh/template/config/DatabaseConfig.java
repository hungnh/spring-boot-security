package uet.hungnh.template.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories(basePackages = "uet.hungnh.template.model.repo")
@EntityScan(basePackages = "uet.hungnh.template.model.entity")
public class DatabaseConfig {
    @Bean
    private LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        return new LocalContainerEntityManagerFactoryBean();
    }
}