package hw.products.rest;

import hw.products.dto.ProductListDto;
import hw.products.dto.UserDto;
import hw.products.service.ProductService;
import hw.products.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products/users")
public class UserController {
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @GetMapping("/{id}/products")
    public ProductListDto getByUserId(@PathVariable("id") Long id) {
        return productService.getByUserId(id);
    }

}








