package uet.hungnh.config.remote;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RemoteHttpInvokerConfig {

    private static final int TIME_OUT_IN_MILLISECOND = 60 * 1000;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        return restTemplate;
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        RequestConfig requestConfig =
                RequestConfig
                        .custom()
                        .setConnectTimeout(TIME_OUT_IN_MILLISECOND)
                        .setConnectionRequestTimeout(TIME_OUT_IN_MILLISECOND)
                        .setSocketTimeout(TIME_OUT_IN_MILLISECOND)
                        .build();

        CloseableHttpClient httpClient =
                HttpClientBuilder
                        .create()
                        .setDefaultRequestConfig(requestConfig)
                        .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
}
