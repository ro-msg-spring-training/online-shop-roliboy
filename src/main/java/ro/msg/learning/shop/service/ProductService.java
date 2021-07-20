package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.ProductCategoryNotFoundException;
import ro.msg.learning.shop.exception.ProductNotFoundException;
import ro.msg.learning.shop.exception.SupplierNotFoundException;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.dto.ProductDTO;
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

    public Collection<ProductDTO> list() {
        return productRepository.findAll().stream()
                .map(ProductDTO::fromProduct)
                .toList();
    }

    public ProductDTO retrieve(Integer id) {
        return productRepository.findById(id)
                .map(ProductDTO::fromProduct)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public ProductDTO create(Product product, Integer productCategoryId, Integer supplierId) {
        var productCategory = productCategoryRepository.findById(productCategoryId)
                .orElseThrow(() -> new ProductCategoryNotFoundException(productCategoryId));

        var supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException(supplierId));

        product.setCategory(productCategory);
        product.setSupplier(supplier);

        return ProductDTO.fromProduct(productRepository.save(product));
    }

    @Transactional
    public ProductDTO update(Integer id, Product product, Integer productCategoryId, Integer supplierId) {
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

        return ProductDTO.fromProduct(updatedProduct);
    }

    public ProductDTO destroy(Integer id) {
        var product = productRepository.findById(id)
                .map(ProductDTO::fromProduct)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.deleteById(id);
        return product;
    }
}
