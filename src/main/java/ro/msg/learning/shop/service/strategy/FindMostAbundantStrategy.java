package ro.msg.learning.shop.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ro.msg.learning.shop.exception.OrderNotFulfilledException;
import ro.msg.learning.shop.model.OrderLocation;
import ro.msg.learning.shop.model.domain.OrderDetail;
import ro.msg.learning.shop.model.domain.Stock;
import ro.msg.learning.shop.persistence.StockRepository;

import java.util.Collection;
import java.util.Comparator;

@RequiredArgsConstructor
public class FindMostAbundantStrategy implements FindLocationStrategy {
    private final StockRepository stockRepository;

    @Override
    public Collection<OrderLocation> getOrderLocations(Collection<OrderDetail> orderDetails) {
        var stocks = stockRepository.findAll();
        return orderDetails.stream()
                .map(orderDetail -> OrderLocation.builder()
                        .location(stocks.stream()
                                .filter(stock -> stock.getProduct().equals(orderDetail.getProduct())
                                              && stock.getQuantity() >= orderDetail.getQuantity())
                                .sorted(Comparator.comparingInt(Stock::getQuantity).reversed())
                                .map(Stock::getLocation)
                                .findFirst()
                                .orElseThrow(OrderNotFulfilledException::new))
                        .product(orderDetail.getProduct())
                        .quantity(orderDetail.getQuantity())
                        .build())
                .toList();
    }
}
