package ro.msg.learning.shop.converter;

import ro.msg.learning.shop.model.domain.Stock;
import ro.msg.learning.shop.model.dto.StockDTO;

public class StockDTOConverter {
    public static StockDTO fromStock(Stock stock) {
        return StockDTO.builder()
                .quantity(stock.getQuantity())
                .locationId(stock.getLocation().getId())
                .locationName(stock.getLocation().getName())
                .productId(stock.getProduct().getId())
                .productName(stock.getProduct().getName())
                .build();
    }
}
