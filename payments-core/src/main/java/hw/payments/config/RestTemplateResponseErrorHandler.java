package hw.payments.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import hw.payments.dto.ErrorDto;
import hw.payments.exception.IntegrationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return !response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is5xxServerError()) {
            var error = objectMapper.readValue(response.getBody(), ErrorDto.class);
            log.error(error.getMessage());
            throw new IntegrationException("Ошибка обращения к внешнему сервису", HttpStatus.BAD_GATEWAY.value(), error.getMessage());
        } else if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()))) {
            var error = objectMapper.readValue(response.getBody(), ErrorDto.class);
            throw new IntegrationException("Неизвестный продукт", HttpStatus.NOT_FOUND.value(), error.getMessage());
        } else if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()))) {
            var error = objectMapper.readValue(response.getBody(), ErrorDto.class);
            throw new IntegrationException("Невалидный запрос", HttpStatus.BAD_REQUEST.value(), error.getMessage());
        } else if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(HttpStatus.PAYMENT_REQUIRED.value()))) {
            var error = objectMapper.readValue(response.getBody(), ErrorDto.class);
            throw new IntegrationException("Недостаточно средств", HttpStatus.PAYMENT_REQUIRED.value(), error.getMessage());
        }
    }
}
