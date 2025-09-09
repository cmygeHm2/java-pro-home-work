package hw.service;

import hw.dto.ProductDto;
import hw.dto.ProductListDto;
import hw.entity.Product;
import hw.exception.RecordNotFoundException;
import hw.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
