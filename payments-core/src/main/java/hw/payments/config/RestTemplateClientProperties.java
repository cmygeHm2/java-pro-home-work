package hw.payments.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("integrations.clients")
@Getter
public class RestTemplateClientProperties {

    private final RestTemplateProperties productsClient;

    public RestTemplateClientProperties(RestTemplateProperties productsClient) {
        this.productsClient = productsClient;
    }

}
