package ro.msg.learning.shop.converter;

import ro.msg.learning.shop.model.domain.Product;
import ro.msg.learning.shop.model.dto.ProductDTO;

public class ProductDTOConverter {
    public static ProductDTO fromProduct(Product product) {
        return ProductDTO.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .weight(product.getWeight())
                .categoryName(product.getCategory().getName())
                .categoryDescription(product.getCategory().getDescription())
                .imageUrl(product.getImageUrl())
                .build();
    }

    public static Product toProduct(ProductDTO productDTO) {
        return Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .weight(productDTO.getWeight())
                .category(null)
                .supplier(null)
                .imageUrl(productDTO.getImageUrl())
                .build();
    }
}
