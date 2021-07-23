package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.ProductNotFoundException;
import ro.msg.learning.shop.model.domain.Address;
import ro.msg.learning.shop.model.domain.Customer;
import ro.msg.learning.shop.model.domain.Order;
import ro.msg.learning.shop.model.domain.OrderDetail;
import ro.msg.learning.shop.model.dto.OrderDetailDTO;
import ro.msg.learning.shop.persistence.*;
import ro.msg.learning.shop.service.strategy.FindLocationStrategy;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final SendEmailService sendEmailService;
    private final FindLocationStrategy findLocationStrategy;

    // TODO: DTO bad
    @Transactional
    public Order create(LocalDateTime timestamp, Address address, Collection<OrderDetailDTO> orderDetailsList) {
        // TODO: get customer object from currently authorized user
        var customer = customerRepository.save(
                Customer.builder()
                        .emailAddress("roliboy@protonmail.com")
                        .firstName("nagy")
                        .lastName("roland")
                        .build());

        var order = Order.builder()
                .createdAt(timestamp)
                .address(address)
                .customer(customer)
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
        orderElements.forEach(orderDetailRepository::save);

        sendEmailService.sendConfirmationMail(order);

        return order;
    }
}
