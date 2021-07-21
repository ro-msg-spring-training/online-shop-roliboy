package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.model.domain.Order;
import ro.msg.learning.shop.model.dto.in.OrderInputDTO;
import ro.msg.learning.shop.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping(
            value = "",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<Order> create(@RequestBody OrderInputDTO orderInputDTO) {
        var orderDetailsList = orderInputDTO.getProducts();
        var timestamp = orderInputDTO.getTimestamp();
        var address = orderInputDTO.getAddress();
        return new ResponseEntity<>(
                orderService.create(timestamp, address, orderDetailsList),
                HttpStatus.CREATED
        );
    }
}
