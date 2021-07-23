package ro.msg.learning.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.domain.Stock;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({ "quantity", "locationId", "locationName", "productId", "locationId" })
public class StockDTO {
    private Integer quantity;
    private Integer productId;
    private String productName;
    private Integer locationId;
    private String locationName;
}
