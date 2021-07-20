package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.model.dto.ProductDTO;
import ro.msg.learning.shop.service.ProductService;

import java.util.Collection;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping(
            value = "",
            produces = {"application/json"}
    )
    public ResponseEntity<Collection<ProductDTO>> list() {
        return new ResponseEntity<>(
                productService.list(),
                HttpStatus.OK
        );
    }

    @PostMapping(
            value = "",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO) {
        var product = productDTO.toProduct();
        var productCategoryId = productDTO.getCategoryId();
        var supplierId = productDTO.getSupplierId();
        return new ResponseEntity<>(
                productService.create(product, productCategoryId, supplierId),
                HttpStatus.CREATED
        );
    }

    @GetMapping(
            value = "/{id}",
            produces = {"application/json"}
    )
    public ResponseEntity<ProductDTO> retrieve(@PathVariable Integer id) {
        return new ResponseEntity<>(
                productService.retrieve(id),
                HttpStatus.OK
        );
    }

    @PutMapping(
            value = "/{id}",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<ProductDTO> update(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        var product = productDTO.toProduct();
        var productCategoryId = productDTO.getCategoryId();
        var supplierId = productDTO.getSupplierId();
        return new ResponseEntity<>(
                productService.update(id, product, productCategoryId, supplierId),
                HttpStatus.OK
        );
    }

    @DeleteMapping(
            value = "/{id}",
            produces = {"application/json"}
    )
    public ResponseEntity<ProductDTO> destroy(@PathVariable Integer id) {
        return new ResponseEntity<>(
                productService.destroy(id),
                // TODO: change to NO_CONTENT?
                HttpStatus.OK
        );
    }
}
