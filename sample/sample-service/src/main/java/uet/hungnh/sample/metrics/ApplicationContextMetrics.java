package uet.hungnh.sample.metrics;

import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class ApplicationContextMetrics implements PublicMetrics {

    private ApplicationContext applicationContext;

    public ApplicationContextMetrics(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Collection<Metric<?>> metrics() {
        Collection<Metric<?>> metrics = new ArrayList<>();

        Metric<Long> appStartupDate = new Metric<>("spring.context.startup-date", applicationContext.getStartupDate());
        metrics.add(appStartupDate);

        Metric<Integer> beanCounts = new Metric<>("spring.beans.definitions", applicationContext.getBeanDefinitionCount());
        metrics.add(beanCounts);

        Metric<Integer> controllerCounts = new Metric<Integer>("spring.controllers", app)

        return metrics;
    }
}
