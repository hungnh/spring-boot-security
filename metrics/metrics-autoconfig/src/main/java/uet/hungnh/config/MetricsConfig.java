package uet.hungnh.config;


import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan(basePackages = "uet.hungnh.metrics")
@PropertySource(value = {"classpath:/metrics.properties"})
public class MetricsConfig {

    @Bean
    public Graphite graphite(
            @Value("${graphite.host}") String graphiteHost,
            @Value("${graphite.port}") int graphitePort
    ) {
        return new Graphite(graphiteHost, graphitePort);
    }

    @Bean
    public GraphiteReporter graphiteReporter(Graphite graphite, MetricRegistry registry) {
        GraphiteReporter reporter = GraphiteReporter.forRegistry(registry)
                .prefixedWith("metrics.uet.hungnh")
                .convertRatesTo(TimeUnit.MILLISECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build(graphite);

        reporter.start(5, TimeUnit.SECONDS);

        return reporter;
    }

    @Bean
    public MemoryUsageGaugeSet memoryUsageGaugeSet(MetricRegistry registry) {
        MemoryUsageGaugeSet memoryUsageGaugeSet = new MemoryUsageGaugeSet();
        registry.register("sample-app.memory", memoryUsageGaugeSet);
        return memoryUsageGaugeSet;
    }

    @Bean
    public ThreadStatesGaugeSet threadStatesGaugeSet(MetricRegistry registry) {
        ThreadStatesGaugeSet threadStatesGaugeSet = new ThreadStatesGaugeSet();
        registry.register("sample-app.thread-state", threadStatesGaugeSet);
        return threadStatesGaugeSet;
    }
}
