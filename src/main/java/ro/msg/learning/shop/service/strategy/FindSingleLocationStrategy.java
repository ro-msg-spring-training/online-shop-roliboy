package ro.msg.learning.shop.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import ro.msg.learning.shop.exception.OrderNotFulfilledException;
import ro.msg.learning.shop.model.OrderLocation;
import ro.msg.learning.shop.model.domain.Location;
import ro.msg.learning.shop.model.domain.OrderDetail;
import ro.msg.learning.shop.persistence.LocationRepository;

import java.util.Collection;
import java.util.Comparator;

public class FindSingleLocationStrategy implements FindLocationStrategy {
    @Autowired
    LocationRepository locationRepository;

    @Override
    public Collection<OrderLocation> getOrderLocations(Collection<OrderDetail> orderDetails) {
        return locationRepository.findAll().stream()
                .filter(location -> orderDetails.stream()
                        .allMatch(orderDetail -> location.getStocks().stream()
                                .anyMatch(stock -> stock.getProduct().equals(orderDetail.getProduct())
                                        && stock.getQuantity() >= orderDetail.getQuantity())))
                .sorted(Comparator.comparingInt(Location::getId))
                .map(location -> orderDetails.stream()
                        .map(orderDetail -> OrderLocation.builder()
                                .location(location)
                                .product(orderDetail.getProduct())
                                .quantity(orderDetail.getQuantity())
                                .build())
                        .toList())
                .findFirst()
                .orElseThrow(OrderNotFulfilledException::new);
    }
}
