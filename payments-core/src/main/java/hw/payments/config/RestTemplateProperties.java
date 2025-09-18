package hw.payments.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Duration;


@Getter
@Setter
@RequiredArgsConstructor
public class RestTemplateProperties {
    private final String url;
    private final Duration connectTimeout;
    private final Duration readTimeout;
}
