package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.model.domain.Order;
import ro.msg.learning.shop.model.dto.OrderDTO;
import ro.msg.learning.shop.service.OrderService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderDTO orderDTO) {
        var orderDetailsList = orderDTO.getProducts();
        var timestamp = orderDTO.getTimestamp();
        var address = orderDTO.getAddress();
        return new ResponseEntity<>(
                orderService.create(timestamp, address, orderDetailsList),
                HttpStatus.CREATED
        );
    }
}
