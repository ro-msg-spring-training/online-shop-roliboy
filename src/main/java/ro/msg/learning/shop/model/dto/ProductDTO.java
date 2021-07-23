package ro.msg.learning.shop.model.dto;

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
public class ProductDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;
    private String categoryName;
    private String categoryDescription;
    private String imageUrl;
    private Integer categoryId;
    private Integer supplierId;
}
