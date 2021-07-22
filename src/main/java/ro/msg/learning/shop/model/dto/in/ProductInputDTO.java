package ro.msg.learning.shop.model.dto.in;

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
public class ProductInputDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;
    private Integer categoryId;
    private Integer supplierId;
    private String imageUrl;

    public Product toProduct() {
        return Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .weight(weight)
                .category(null)
                .supplier(null)
                .imageUrl(imageUrl)
                .build();
    }
}
