package uet.hungnh.config;


import com.codahale.metrics.JvmAttributeGaugeSet;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.jvm.ClassLoadingGaugeSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
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
                .prefixedWith("metrics")
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
    public ClassLoadingGaugeSet classLoadingGaugeSet(MetricRegistry registry) {
        ClassLoadingGaugeSet classLoadingGaugeSet = new ClassLoadingGaugeSet();
        registry.register("sample-app.class", classLoadingGaugeSet);
        return classLoadingGaugeSet;
    }

    @Bean
    public JvmAttributeGaugeSet jvmAttributeGaugeSet(MetricRegistry registry) {
        JvmAttributeGaugeSet jvmAttributeGaugeSet = new JvmAttributeGaugeSet();
        registry.register("sample-app.jvm", jvmAttributeGaugeSet);
        return jvmAttributeGaugeSet;
    }

    @Bean
    public GarbageCollectorMetricSet garbageCollectorMetricSet(MetricRegistry registry) {
        GarbageCollectorMetricSet garbageCollectorMetricSet = new GarbageCollectorMetricSet();
        registry.register("sample-app.gc", garbageCollectorMetricSet);
        return garbageCollectorMetricSet;
    }

    @Bean
    public ThreadStatesGaugeSet threadStatesGaugeSet(MetricRegistry registry) {
        ThreadStatesGaugeSet threadStatesGaugeSet = new ThreadStatesGaugeSet();
        registry.register("sample-app.thread-state", threadStatesGaugeSet);
        return threadStatesGaugeSet;
    }
}
