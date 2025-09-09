package hw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private String productType;
}
