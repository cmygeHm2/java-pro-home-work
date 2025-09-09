package hw.rest;

import hw.dto.ProductListDto;
import hw.dto.UserDto;
import hw.service.ProductService;
import hw.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
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
