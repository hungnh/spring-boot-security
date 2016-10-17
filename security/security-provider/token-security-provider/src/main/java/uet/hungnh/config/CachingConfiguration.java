package uet.hungnh.config;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfiguration extends CachingConfigurerSupport {
    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Override
    public CacheManager cacheManager() {
        return new HazelcastCacheManager(hazelcastInstance);
    }
}
