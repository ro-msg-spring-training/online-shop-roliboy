package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.model.dto.StockDTO;
import ro.msg.learning.shop.service.StockService;

import java.util.Collection;

@RestController
@RequestMapping("/stocks")
public class StockController {
    @Autowired
    StockService stockService;

    @GetMapping(
            value = "/{id}",
            produces = "text/csv"
    )
    @ResponseBody
    public ResponseEntity<Collection<StockDTO>> list(@PathVariable Integer id) {
        return new ResponseEntity<>(
                stockService.export(id),
                HttpStatus.OK
        );
    }
}
