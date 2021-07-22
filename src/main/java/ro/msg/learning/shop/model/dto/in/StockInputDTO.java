package ro.msg.learning.shop.model.dto.in;

import lombok.*;

import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StockInputDTO {
    private Integer productId;
    private Integer locationId;
    private Integer quantity;
}
