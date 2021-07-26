package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.converter.ProductDTOConverter;
import ro.msg.learning.shop.model.dto.ProductDTO;
import ro.msg.learning.shop.service.ProductService;

import java.util.Collection;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<Collection<ProductDTO>> list() {
        return new ResponseEntity<>(
                productService.list(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO) {
        var product = ProductDTOConverter.toProduct(productDTO);
        var productCategoryId = productDTO.getCategoryId();
        var supplierId = productDTO.getSupplierId();
        return new ResponseEntity<>(
                productService.create(product, productCategoryId, supplierId),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> retrieve(@PathVariable Integer id) {
        return new ResponseEntity<>(
                productService.retrieve(id),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        var product = ProductDTOConverter.toProduct(productDTO);
        product.setId(id);
        var productCategoryId = productDTO.getCategoryId();
        var supplierId = productDTO.getSupplierId();
        return new ResponseEntity<>(
                productService.update(product, productCategoryId, supplierId),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> delete(@PathVariable Integer id) {
        return new ResponseEntity<>(
                productService.delete(id),
                HttpStatus.NO_CONTENT
        );
    }
}
