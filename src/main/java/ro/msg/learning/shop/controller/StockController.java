package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.model.domain.Order;
import ro.msg.learning.shop.model.domain.Stock;
import ro.msg.learning.shop.model.dto.in.OrderInputDTO;
import ro.msg.learning.shop.model.dto.out.StockOutputDTO;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.StockService;

import java.util.Collection;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    @Autowired
    StockService stockService;

    @GetMapping(
            value = "/{id}",
            produces = "text/csv"
    )
    @ResponseBody
    public ResponseEntity<Collection<StockOutputDTO>> list(@PathVariable Integer id) {
        return new ResponseEntity<>(
                stockService.export(id),
                HttpStatus.OK
        );
    }
}
