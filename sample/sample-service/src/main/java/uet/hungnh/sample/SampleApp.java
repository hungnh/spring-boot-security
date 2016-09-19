package uet.hungnh.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import uet.hungnh.AbstractApplication;

import java.util.Date;
import java.util.TimeZone;

@Configuration
@EnableAutoConfiguration(
        exclude = {
                RepositoryRestMvcAutoConfiguration.class,
                SecurityAutoConfiguration.class
        }
)
@ComponentScan(basePackages = "uet.hungnh")
public class SampleApp extends AbstractApplication {
    public static void main(String[] args) {
        SpringApplication.run(SampleApp.class, args);
    }
}
