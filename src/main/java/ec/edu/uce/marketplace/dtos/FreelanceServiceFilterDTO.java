package ec.edu.uce.marketplace.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FreelanceServiceFilterDTO {
    private String name; // Nombre del servicio
    private BigDecimal minPrice; // Precio mínimo
    private BigDecimal maxPrice; // Precio máximo
    private List<String> skillsRequired; // Lista de habilidades requeridas
}
