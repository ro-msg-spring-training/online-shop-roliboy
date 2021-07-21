package ro.msg.learning.shop.model.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.domain.Product;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
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
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .weight(this.weight)
                .category(null)
                .supplier(null)
                .imageUrl(this.imageUrl)
                .build();
    }
}
