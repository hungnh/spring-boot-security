package uet.hungnh.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class HazelcastConfiguration {
    @Bean
    public Config config() {
        Config hazelcastClusterConfig = new Config();

        GroupConfig groupConfig = hazelcastClusterConfig.getGroupConfig();
        groupConfig.setName("token-cache-group");
        groupConfig.setPassword("123qweasd");

        NetworkConfig networkConfig = hazelcastClusterConfig.getNetworkConfig();
        JoinConfig joinConfig = networkConfig.getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(true);
        joinConfig.getTcpIpConfig().addMember("192.168.1.18");

        return hazelcastClusterConfig;
    }
}
