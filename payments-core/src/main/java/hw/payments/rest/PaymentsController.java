package hw.payments.rest;

import hw.payments.dto.PaymentRequestDto;
import hw.payments.dto.PaymentResponseDto;
import hw.payments.dto.ProductResponseDto;
import hw.payments.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentsController {

    private final ProductsService productsService;

    @GetMapping("/product/{product-id}")
    public ProductResponseDto getProduct(@PathVariable(name = "product-id") Long productId) {
        return productsService.getProductInfo(productId);
    }

    @PostMapping(value = "/product/pay", consumes = "application/json")
    public PaymentResponseDto payByProduct(@RequestBody PaymentRequestDto paymentRequestDto) {
        return productsService.payProduct(paymentRequestDto);
    }
}


