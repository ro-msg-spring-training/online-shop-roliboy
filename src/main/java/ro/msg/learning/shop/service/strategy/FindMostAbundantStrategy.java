package ro.msg.learning.shop.service.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ro.msg.learning.shop.exception.OrderNotFulfilledException;
import ro.msg.learning.shop.model.OrderLocation;
import ro.msg.learning.shop.model.domain.Location;
import ro.msg.learning.shop.model.domain.OrderDetail;
import ro.msg.learning.shop.model.domain.Stock;
import ro.msg.learning.shop.persistence.LocationRepository;
import ro.msg.learning.shop.persistence.StockRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class FindMostAbundantStrategy implements FindLocationStrategy {
    @Autowired
    StockRepository stockRepository;

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
