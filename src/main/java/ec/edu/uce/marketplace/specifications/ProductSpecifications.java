package ec.edu.uce.marketplace.specifications;

import ec.edu.uce.marketplace.entities.Product;
import ec.edu.uce.marketplace.dtos.ProductFilterDTO;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> withFilters(ProductFilterDTO filters) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            if (filters.getCategory() != null && !filters.getCategory().isEmpty()) {
                predicates.getExpressions().add(
                        criteriaBuilder.equal(root.get("category"), filters.getCategory())
                );
            }

            if (filters.getName() != null && !filters.getName().isEmpty()) {
                predicates.getExpressions().add(
                        criteriaBuilder.like(root.get("name"), "%" + filters.getName() + "%")
                );
            }

            if (filters.getMinPrice() != null) {
                predicates.getExpressions().add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filters.getMinPrice())
                );
            }

            if (filters.getMaxPrice() != null) {
                predicates.getExpressions().add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("price"), filters.getMaxPrice())
                );
            }

            return predicates;
        };
    }
}
