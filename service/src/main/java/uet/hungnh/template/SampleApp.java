package uet.hungnh.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import uet.hungnh.template.config.security.WebSecurityConfig;

@Configuration
@EnableAutoConfiguration(
        exclude = {
                RepositoryRestMvcAutoConfiguration.class,
                SecurityAutoConfiguration.class
        }
)
@ComponentScan(
        basePackages = "uet.hungnh.template",
        excludeFilters = @ComponentScan.Filter({Configuration.class})
)
@Import({WebSecurityConfig.class})
public class SampleApp {
    public static void main(String[] args) {
        SpringApplication.run(SampleApp.class, args);
    }
}
