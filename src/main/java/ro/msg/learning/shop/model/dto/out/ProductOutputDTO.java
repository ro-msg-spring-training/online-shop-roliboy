package ro.msg.learning.shop.model.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.domain.Product;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductOutputDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;
    private String categoryName;
    private String categoryDescription;
    private String imageUrl;

    public static ProductOutputDTO fromProduct(Product product) {
        return ProductOutputDTO.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .weight(product.getWeight())
                .categoryName(product.getCategory().getName())
                .categoryDescription(product.getCategory().getDescription())
                .imageUrl(product.getImageUrl())
                .build();
    }
}
