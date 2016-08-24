package uet.hungnh.template.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@ComponentScan(
        basePackages = "uet.hungnh.template",
        excludeFilters = @ComponentScan.Filter({Configuration.class})
)
public class CacheConfig {
}
