package uet.hungnh.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class HazelcastClientConfiguration {
    @Bean
    public HazelcastInstance hazelcastInstance() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setInstanceName("uet.hungnh.caching.hz-client");

        GroupConfig groupConfig = clientConfig.getGroupConfig();
        groupConfig.setName("hz-caching-group");
        groupConfig.setPassword("123qweasd");

        ClientNetworkConfig networkConfig = clientConfig.getNetworkConfig();
        networkConfig.addAddress("192.168.1.18");

        return HazelcastClient.newHazelcastClient(clientConfig);
    }

    @PreDestroy
    public void shutdownAllClients() {
        HazelcastClient.shutdownAll();
    }
}
