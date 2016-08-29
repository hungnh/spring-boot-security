package uet.hungnh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import uet.hungnh.common.event.DistributiveEventMulticaster;

@Configuration
public class ApplicationEventConfig {

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster applicationEventMulticaster() {
        DistributiveEventMulticaster applicationEventMulticaster = new DistributiveEventMulticaster();
        applicationEventMulticaster.setSyncEventMulticaster(syncEventMulticaster());
        applicationEventMulticaster.setAsyncEventMulticaster(asyncEventMulticaster());
        return applicationEventMulticaster;
    }

    private SimpleApplicationEventMulticaster syncEventMulticaster() {
        return new SimpleApplicationEventMulticaster();
    }

    private SimpleApplicationEventMulticaster asyncEventMulticaster() {
        SimpleApplicationEventMulticaster asyncEventMulticaster = new SimpleApplicationEventMulticaster();
        asyncEventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return asyncEventMulticaster;
    }
}
