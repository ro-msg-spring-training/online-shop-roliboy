package ro.msg.learning.shop.model.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDetailInputDTO {
    private Integer productId;
    private Integer quantity;
}
