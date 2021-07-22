package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.model.domain.Order;
import ro.msg.learning.shop.model.dto.in.LocationInputDTO;
import ro.msg.learning.shop.model.dto.in.OrderInputDTO;
import ro.msg.learning.shop.model.dto.in.ProductInputDTO;
import ro.msg.learning.shop.model.dto.in.StockInputDTO;
import ro.msg.learning.shop.model.dto.out.LocationOutputDTO;
import ro.msg.learning.shop.model.dto.out.ProductOutputDTO;
import ro.msg.learning.shop.model.dto.out.StockOutputDTO;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.PopulateDatabaseService;
import ro.msg.learning.shop.service.StockService;

import java.util.Collection;

@Profile("test")
@RestController
@RequestMapping("/api/test/populate")
public class PopulateDatabaseController {
    @Autowired
    PopulateDatabaseService populateDatabaseService;

    @PostMapping(
            value = "/locations",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<Collection<LocationOutputDTO>> insertLocations(@RequestBody Collection<LocationInputDTO> locations) {
        return new ResponseEntity<>(
                populateDatabaseService.insertLocations(locations),
                HttpStatus.CREATED
        );
    }

    @PostMapping(
            value = "/products",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<Collection<ProductOutputDTO>> insertProducts(@RequestBody Collection<ProductInputDTO> products) {
        return new ResponseEntity<>(
                populateDatabaseService.insertProducts(products),
                HttpStatus.CREATED
        );
    }

    @PostMapping(
            value = "/stocks",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<Collection<StockOutputDTO>> insertStocks(@RequestBody Collection<StockInputDTO> stocks) {
        return new ResponseEntity<>(
                populateDatabaseService.insertStocks(stocks),
                HttpStatus.CREATED
        );
    }

    @GetMapping(
            value = "/drop",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<String> dropAll() {
        populateDatabaseService.drop();
        return new ResponseEntity<>(
                "dropped",
                HttpStatus.OK
        );
    }
}
