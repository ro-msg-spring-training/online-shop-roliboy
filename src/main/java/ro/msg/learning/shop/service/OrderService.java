package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.ProductNotFoundException;
import ro.msg.learning.shop.model.domain.Address;
import ro.msg.learning.shop.model.domain.Order;
import ro.msg.learning.shop.model.domain.OrderDetail;
import ro.msg.learning.shop.model.dto.in.OrderDetailInputDTO;
import ro.msg.learning.shop.persistence.OrderDetailRepository;
import ro.msg.learning.shop.persistence.OrderRepository;
import ro.msg.learning.shop.persistence.ProductRepository;
import ro.msg.learning.shop.persistence.StockRepository;
import ro.msg.learning.shop.service.strategy.FindLocationStrategy;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    StockRepository stockRepository;
    @Autowired
    FindLocationStrategy findLocationStrategy;

    // TODO: DTO bad
    @Transactional
    public Order create(LocalDateTime timestamp, Address address, Collection<OrderDetailInputDTO> orderDetailsList) {
        var order = Order.builder()
                .createdAt(timestamp)
                .address(address)
                // TODO: customer
                .customer(null)
                // TODO: shippedFrom
                .shippedFrom(null)
                .build();

        var orderElements = orderDetailsList.stream()
                .map(orderDetail -> {
                    var product = productRepository.findById(orderDetail.getProductId())
                            .orElseThrow(() -> new ProductNotFoundException(orderDetail.getProductId()));
                    return OrderDetail.builder()
                            .order(order)
                            .product(product)
                            .quantity(orderDetail.getQuantity())
                            .build();
                })
                .toList();

        var orderLocations = findLocationStrategy.getOrderLocations(orderElements);
        orderLocations.forEach(orderLocation -> {
            var stock = stockRepository.findByLocationAndProduct(
                    orderLocation.getLocation(),
                    orderLocation.getProduct())
                    .orElseThrow(() -> new RuntimeException("something went terribly wrong"));
            stock.setQuantity(stock.getQuantity() - orderLocation.getQuantity());
            stockRepository.save(stock);
        });

        orderRepository.save(order);
        orderElements.forEach(orderDetail -> orderDetailRepository.save(orderDetail));

        return order;
    }
}
