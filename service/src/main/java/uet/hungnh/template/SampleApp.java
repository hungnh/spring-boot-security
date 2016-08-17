package uet.hungnh.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(
        exclude = {RepositoryRestMvcAutoConfiguration.class}
)
@ComponentScan(
        basePackages = "uet.hungnh.template",
        excludeFilters = @ComponentScan.Filter({Configuration.class})
)
public class SampleApp {
    public static void main(String[] args) {
        SpringApplication.run(SampleApp.class, args);
    }
}
