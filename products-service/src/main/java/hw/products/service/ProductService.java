package hw.products.service;

import hw.products.dto.ProductDto;
import hw.products.dto.ProductListDto;
import hw.products.entity.Product;
import hw.products.exception.NotEnoughMoneyException;
import hw.products.exception.RecordNotFoundException;
import hw.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDto getById(Long id) {
        return productRepository.findById(id)
                .map(this::productToDto)
                .orElseThrow(() -> new RecordNotFoundException("Product not found: id = " + id));
    }

    public ProductListDto getByUserId(Long id) {
        List<ProductDto> products = productRepository.findByUserId(id)
                .stream()
                .map(this::productToDto)
                .collect(Collectors.toList());
        return new ProductListDto(products);
    }

    private ProductDto productToDto(Product product) {
        return new ProductDto(product.getId(), product.getAccountNumber(), product.getBalance(), product.getProductType());
    }

    @Transactional
    public void decreaseBalance(ProductDto productToPay, BigDecimal sum) {
        Product lockedProduct = productRepository.selectForUpdate(productToPay.getId(), productToPay.getBalance())
                .orElseThrow(() -> new NotEnoughMoneyException("Balance changed, try again"));

        BigDecimal newBalance = lockedProduct.getBalance().subtract(sum);
        lockedProduct.setBalance(newBalance);
        productRepository.save(lockedProduct);
    }
}
