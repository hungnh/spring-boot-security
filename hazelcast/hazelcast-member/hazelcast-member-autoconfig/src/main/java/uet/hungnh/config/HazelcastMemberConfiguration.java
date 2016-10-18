package uet.hungnh.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastInstanceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class HazelcastMemberConfiguration {

    public static final Logger LOGGER = LoggerFactory.getLogger(HazelcastMemberConfiguration.class);

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();
        config.setInstanceName("uet.hungnh.caching.hz-member");

        GroupConfig groupConfig = config.getGroupConfig();
        groupConfig.setName("hz-caching-group");
        groupConfig.setPassword("123qweasd");

        NetworkConfig networkConfig = config.getNetworkConfig();
        JoinConfig joinConfig = networkConfig.getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(true);
        joinConfig.getTcpIpConfig().addMember("192.168.1.68");

        config.addMapConfig(tokenMapConfig());

        return new HazelcastInstanceFactory(config).getHazelcastInstance();
    }

    private MapConfig tokenMapConfig() {
        MapConfig tokenMapConfig = new MapConfig();
        tokenMapConfig.setName("token-map");
        tokenMapConfig.setEvictionPolicy(EvictionPolicy.LRU);
        tokenMapConfig.setTimeToLiveSeconds(60 * 60);
        return tokenMapConfig;
    }

    @PreDestroy
    public void stopAllInstances() {
        LOGGER.info("Shutting down all Hazelcast instances!");
        Hazelcast.shutdownAll();
    }
}
