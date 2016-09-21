package uet.hungnh.admin;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class MetricsAdminConsoleApp {

    public static void main(String[] args) {
        SpringApplication.run(MetricsAdminConsoleApp.class, args);
    }
}
