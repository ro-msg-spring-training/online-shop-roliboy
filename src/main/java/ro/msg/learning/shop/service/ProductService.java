package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.ProductCategoryNotFoundException;
import ro.msg.learning.shop.exception.ProductNotFoundException;
import ro.msg.learning.shop.exception.SupplierNotFoundException;
import ro.msg.learning.shop.model.domain.Product;
import ro.msg.learning.shop.model.dto.out.ProductOutputDTO;
import ro.msg.learning.shop.persistence.ProductCategoryRepository;
import ro.msg.learning.shop.persistence.ProductRepository;
import ro.msg.learning.shop.persistence.SupplierRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    SupplierRepository supplierRepository;

    public Collection<ProductOutputDTO> list() {
        return productRepository.findAll().stream()
                .map(ProductOutputDTO::fromProduct)
                .toList();
    }

    public ProductOutputDTO retrieve(Integer id) {
        return productRepository.findById(id)
                .map(ProductOutputDTO::fromProduct)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public ProductOutputDTO create(Product product, Integer productCategoryId, Integer supplierId) {
        var productCategory = productCategoryRepository.findById(productCategoryId)
                .orElseThrow(() -> new ProductCategoryNotFoundException(productCategoryId));

        var supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException(supplierId));

        product.setCategory(productCategory);
        product.setSupplier(supplier);

        return ProductOutputDTO.fromProduct(productRepository.save(product));
    }

    @Transactional
    public ProductOutputDTO update(Integer id, Product product, Integer productCategoryId, Integer supplierId) {
        var productCategory = productCategoryRepository.findById(productCategoryId)
                .orElseThrow(() -> new ProductCategoryNotFoundException(productCategoryId));

        var supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException(supplierId));

        product.setCategory(productCategory);
        product.setSupplier(supplier);

        var updatedProduct = productRepository.findById(id)
                .map(oldProduct -> {
                    oldProduct.setName(product.getName());
                    oldProduct.setDescription(product.getDescription());
                    oldProduct.setPrice(product.getPrice());
                    oldProduct.setWeight(product.getWeight());
                    oldProduct.setCategory(product.getCategory());
                    oldProduct.setSupplier(product.getSupplier());
                    oldProduct.setImageUrl(product.getImageUrl());
                    return oldProduct;
                })
                .orElseThrow(() -> new ProductNotFoundException(id));

        return ProductOutputDTO.fromProduct(updatedProduct);
    }

    public ProductOutputDTO destroy(Integer id) {
        var product = productRepository.findById(id)
                .map(ProductOutputDTO::fromProduct)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.deleteById(id);
        return product;
    }
}
