package hw.payments.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(RestTemplateClientProperties.class)
@RequiredArgsConstructor
public class AppConfig {

    private final RestTemplateClientProperties restTemplateClientProperties;
    private final RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplateProperties productsClient = restTemplateClientProperties.getProductsClient();
        return new RestTemplateBuilder()
                .rootUri(productsClient.getUrl())
                .setReadTimeout(productsClient.getReadTimeout())
                .setConnectTimeout(productsClient.getConnectTimeout())
                .errorHandler(restTemplateResponseErrorHandler)
                .build();
    }
}
