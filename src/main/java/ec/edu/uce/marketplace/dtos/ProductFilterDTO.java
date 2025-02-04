package ec.edu.uce.marketplace.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductFilterDTO {
    // Getters y setters
    private String category;
    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

}
