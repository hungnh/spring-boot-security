package uet.hungnh.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.cache.impl.HazelcastClientCacheManager;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean
@EnableCaching
public class CachingConfiguration extends CachingConfigurerSupport {
    @Override
    public CacheManager cacheManager() {
        ClientConfig hazelcastClientConfig = new ClientConfig();

        GroupConfig groupConfig = hazelcastClientConfig.getGroupConfig();
        groupConfig.setName("token-cache-group");
        groupConfig.setPassword("123qweasd");

        ClientNetworkConfig networkConfig = hazelcastClientConfig.getNetworkConfig();
        networkConfig.addAddress("192.168.1.18");

        HazelcastInstance hazelcastClient = HazelcastClient.newHazelcastClient(hazelcastClientConfig);

        return new HazelcastCacheManager(hazelcastClient);
    }
}
