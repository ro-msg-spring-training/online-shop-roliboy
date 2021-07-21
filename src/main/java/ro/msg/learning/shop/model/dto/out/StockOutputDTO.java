package ro.msg.learning.shop.model.dto.out;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.domain.ProductCategory;
import ro.msg.learning.shop.model.domain.Stock;
import ro.msg.learning.shop.model.domain.Supplier;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({ "quantity", "locationId", "locationName", "productId", "locationId" })
public class StockOutputDTO {
    private Integer quantity;
    private Integer productId;
    private String productName;
    private Integer locationId;
    private String locationName;

    public static StockOutputDTO fromStock(Stock stock) {
        return StockOutputDTO.builder()
                .quantity(stock.getQuantity())
                .locationId(stock.getLocation().getId())
                .locationName(stock.getLocation().getName())
                .productId(stock.getProduct().getId())
                .productName(stock.getProduct().getName())
                .build();
    }
}
