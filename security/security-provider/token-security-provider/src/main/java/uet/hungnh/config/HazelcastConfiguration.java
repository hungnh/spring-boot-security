package uet.hungnh.config;

import com.hazelcast.config.*;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastInstanceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();

        GroupConfig groupConfig = config.getGroupConfig();
        groupConfig.setName("token-cache-group");
        groupConfig.setPassword("123qweasd");

        NetworkConfig networkConfig = config.getNetworkConfig();
        JoinConfig joinConfig = networkConfig.getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(true);
        joinConfig.getTcpIpConfig().addMember("192.168.1.18");

        config.addMapConfig(tokenMapConfig());

        return new HazelcastInstanceFactory(config).getHazelcastInstance();
    }

    private MapConfig tokenMapConfig() {
        MapConfig tokenMapConfig = new MapConfig();
        tokenMapConfig.setName("token-map");
        tokenMapConfig.setEvictionPolicy(EvictionPolicy.LRU);
        tokenMapConfig.setTimeToLiveSeconds(10);
        return tokenMapConfig;
    }
}
