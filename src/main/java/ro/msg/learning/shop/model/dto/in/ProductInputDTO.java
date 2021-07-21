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
        var product = new Product();
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setWeight(this.weight);
        product.setCategory(null);
        product.setSupplier(null);
        product.setImageUrl(this.imageUrl);
        return product;
    }
}
