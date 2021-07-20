package ro.msg.learning.shop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.Product;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;
    private Integer categoryId;
    private String categoryName;
    private String categoryDescription;
    private String imageUrl;
    private Integer supplierId;

    public static ProductDTO fromProduct(Product product) {
        var productDTO = new ProductDTO();
        productDTO.name = product.getName();
        productDTO.description = product.getDescription();
        productDTO.price = product.getPrice();
        productDTO.weight = product.getWeight();
        productDTO.categoryId = product.getCategory().getId();
        productDTO.categoryName = product.getCategory().getName();
        productDTO.categoryDescription = product.getCategory().getDescription();
        productDTO.imageUrl = product.getImageUrl();
        productDTO.supplierId = product.getSupplier().getId();
        return productDTO;
    }

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
