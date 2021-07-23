package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.model.dto.LocationDTO;
import ro.msg.learning.shop.model.dto.ProductDTO;
import ro.msg.learning.shop.model.dto.StockDTO;
import ro.msg.learning.shop.service.PopulateDatabaseService;

import java.util.Collection;

@Profile("test")
@RestController
@RequestMapping("/test/populate")
public class PopulateDatabaseController {
    @Autowired
    PopulateDatabaseService populateDatabaseService;

    @PostMapping("/locations")
    public ResponseEntity<Collection<LocationDTO>> insertLocations(@RequestBody Collection<LocationDTO> locations) {
        return new ResponseEntity<>(
                populateDatabaseService.insertLocations(locations),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/products")
    public ResponseEntity<Collection<ProductDTO>> insertProducts(@RequestBody Collection<ProductDTO> products) {
        return new ResponseEntity<>(
                populateDatabaseService.insertProducts(products),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/stocks")
    public ResponseEntity<Collection<StockDTO>> insertStocks(@RequestBody Collection<StockDTO> stocks) {
        return new ResponseEntity<>(
                populateDatabaseService.insertStocks(stocks),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/drop")
    public ResponseEntity<String> dropAll() {
        populateDatabaseService.drop();
        return new ResponseEntity<>(
                "dropped",
                HttpStatus.OK
        );
    }
}
