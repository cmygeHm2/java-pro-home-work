package hw.products.rest;

import hw.products.dto.PaymentRequestDto;
import hw.products.dto.PaymentResponseDto;
import hw.products.dto.ProductDto;
import hw.products.exception.NotEnoughMoneyException;
import hw.products.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/product/{id}")
    public ProductDto getById(@PathVariable("id") Long id) {
        return productService.getById(id);
    }

    @PostMapping("/product")
    public PaymentResponseDto pay(@RequestBody @Valid PaymentRequestDto requestDto) {
        var productToPay = productService.getById(requestDto.getProductId());

        if (productToPay.getBalance().compareTo(requestDto.getSum()) < 0) {
            throw new NotEnoughMoneyException("Not enough money on the account to make a payment. Please check your balance and try again.");
        }

        productService.decreaseBalance(productToPay, requestDto.getSum());

        return new PaymentResponseDto(true);
    }

}








