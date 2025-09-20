package hw.payments.service;

import hw.payments.dto.PaymentRequestDto;
import hw.payments.dto.PaymentResponseDto;
import hw.payments.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProductsService {

    private final RestTemplate restTemplate;

    public ProductResponseDto getProductInfo(Long productId) {
        return restTemplate.getForObject("/products/product/" + productId, ProductResponseDto.class);
    }

    public PaymentResponseDto payProduct(PaymentRequestDto paymentRequestDto) {
        var response = restTemplate.postForEntity("/products/product", paymentRequestDto, PaymentResponseDto.class);
        return response.getBody();
    }
}
