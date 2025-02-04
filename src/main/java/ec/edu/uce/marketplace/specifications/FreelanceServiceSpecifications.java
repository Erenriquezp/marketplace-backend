package ec.edu.uce.marketplace.specifications;

import ec.edu.uce.marketplace.entities.FreelanceService;
import ec.edu.uce.marketplace.dtos.FreelanceServiceFilterDTO;
import org.springframework.data.jpa.domain.Specification;

public class FreelanceServiceSpecifications {

    public static Specification<FreelanceService> applyFilters(FreelanceServiceFilterDTO filters) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            // Filtrar por nombre
            if (filters.getName() != null && !filters.getName().isEmpty()) {
                predicates = cb.and(predicates,
                        cb.like(cb.lower(root.get("name")), "%" + filters.getName().toLowerCase() + "%"));
            }

            // Filtrar por rango de precios
            if (filters.getMinPrice() != null) {
                predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("price"), filters.getMinPrice()));
            }
            if (filters.getMaxPrice() != null) {
                predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("price"), filters.getMaxPrice()));
            }

            // Filtrar por habilidades requeridas
            if (filters.getSkillsRequired() != null && !filters.getSkillsRequired().isEmpty()) {
                for (String skill : filters.getSkillsRequired()) {
                    predicates = cb.and(predicates, cb.isTrue(cb.literal(skill).in(root.get("skillsRequired"))));
                }
            }

            return predicates;
        };
    }
}
