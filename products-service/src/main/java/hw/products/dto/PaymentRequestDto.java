package hw.products.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PaymentRequestDto {
    @NotNull
    @Min(0)
    private Long productId;

    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    private BigDecimal sum;
}




