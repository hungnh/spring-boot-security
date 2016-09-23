package uet.hungnh.config;


import com.codahale.metrics.Histogram;
import com.codahale.metrics.JvmAttributeGaugeSet;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.jvm.ClassLoadingGaugeSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import uet.hungnh.metrics.ResponseTimeInterceptor;

import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan(basePackages = "uet.hungnh.metrics")
@PropertySource(value = {"classpath:/metrics.properties"})
public class MetricsConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private MetricRegistry metricRegistry;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(responseTimeInterceptor());
    }

    @Bean
    public ResponseTimeInterceptor responseTimeInterceptor() {
        return new ResponseTimeInterceptor(responseSizeHistogram());
    }

    @Bean(name = "responseSizeHistogram")
    public Histogram responseSizeHistogram() {
        return metricRegistry.histogram("sample-app.response-size");
    }

    @Bean
    public MemoryUsageGaugeSet memoryUsageGaugeSet() {
        MemoryUsageGaugeSet memoryUsageGaugeSet = new MemoryUsageGaugeSet();
        metricRegistry.register("sample-app.memory", memoryUsageGaugeSet);
        return memoryUsageGaugeSet;
    }

    @Bean
    public ClassLoadingGaugeSet classLoadingGaugeSet() {
        ClassLoadingGaugeSet classLoadingGaugeSet = new ClassLoadingGaugeSet();
        metricRegistry.register("sample-app.class", classLoadingGaugeSet);
        return classLoadingGaugeSet;
    }

    @Bean
    public JvmAttributeGaugeSet jvmAttributeGaugeSet() {
        JvmAttributeGaugeSet jvmAttributeGaugeSet = new JvmAttributeGaugeSet();
        metricRegistry.register("sample-app.jvm", jvmAttributeGaugeSet);
        return jvmAttributeGaugeSet;
    }

    @Bean
    public GarbageCollectorMetricSet garbageCollectorMetricSet() {
        GarbageCollectorMetricSet garbageCollectorMetricSet = new GarbageCollectorMetricSet();
        metricRegistry.register("sample-app.gc", garbageCollectorMetricSet);
        return garbageCollectorMetricSet;
    }

    @Bean
    public ThreadStatesGaugeSet threadStatesGaugeSet() {
        ThreadStatesGaugeSet threadStatesGaugeSet = new ThreadStatesGaugeSet();
        metricRegistry.register("sample-app.thread-state", threadStatesGaugeSet);
        return threadStatesGaugeSet;
    }

    @Bean
    public Graphite graphite(
            @Value("${graphite.host}") String graphiteHost,
            @Value("${graphite.port}") int graphitePort
    ) {
        return new Graphite(graphiteHost, graphitePort);
    }

    @Bean
    public GraphiteReporter graphiteReporter(Graphite graphite) {
        GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry)
                .prefixedWith("metrics")
                .convertRatesTo(TimeUnit.MILLISECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build(graphite);

        reporter.start(5, TimeUnit.SECONDS);

        return reporter;
    }
}
