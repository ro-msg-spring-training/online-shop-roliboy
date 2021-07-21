package ro.msg.learning.shop.model;

import lombok.*;
import ro.msg.learning.shop.model.domain.Location;
import ro.msg.learning.shop.model.domain.Product;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class OrderLocation {
    private Location location;
    private Product product;
    private Integer quantity;
}
