package ro.msg.learning.shop.model.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailInputDTO {
    private Integer productId;
    private Integer quantity;
}
