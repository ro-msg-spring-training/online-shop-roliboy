package ro.msg.learning.shop.model.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.domain.Product;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductOutputDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;
    private String categoryName;
    private String categoryDescription;
    private String imageUrl;

    public static ProductOutputDTO fromProduct(Product product) {
        var productDTO = new ProductOutputDTO();
        productDTO.name = product.getName();
        productDTO.description = product.getDescription();
        productDTO.price = product.getPrice();
        productDTO.weight = product.getWeight();
        productDTO.categoryName = product.getCategory().getName();
        productDTO.categoryDescription = product.getCategory().getDescription();
        productDTO.imageUrl = product.getImageUrl();
        return productDTO;
    }
}
