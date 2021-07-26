package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.converter.ProductDTOConverter;
import ro.msg.learning.shop.exception.ProductCategoryNotFoundException;
import ro.msg.learning.shop.exception.ProductNotFoundException;
import ro.msg.learning.shop.exception.SupplierNotFoundException;
import ro.msg.learning.shop.model.domain.Product;
import ro.msg.learning.shop.model.dto.ProductDTO;
import ro.msg.learning.shop.persistence.ProductCategoryRepository;
import ro.msg.learning.shop.persistence.ProductRepository;
import ro.msg.learning.shop.persistence.SupplierRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final SupplierRepository supplierRepository;

    public Collection<ProductDTO> list() {
        return productRepository.findAll().stream()
                .map(ProductDTOConverter::fromProduct)
                .toList();
    }

    public ProductDTO retrieve(Integer id) {
        return productRepository.findById(id)
                .map(ProductDTOConverter::fromProduct)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public ProductDTO create(Product product, Integer productCategoryId, Integer supplierId) {
        var productCategory = productCategoryRepository.findById(productCategoryId)
                .orElseThrow(() -> new ProductCategoryNotFoundException(productCategoryId));

        var supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException(supplierId));

        product.setCategory(productCategory);
        product.setSupplier(supplier);

        return ProductDTOConverter.fromProduct(productRepository.save(product));
    }

    @Transactional
    public ProductDTO update(Product product, Integer productCategoryId, Integer supplierId) {
        var productCategory = productCategoryRepository.findById(productCategoryId)
                .orElseThrow(() -> new ProductCategoryNotFoundException(productCategoryId));

        var supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException(supplierId));

        product.setCategory(productCategory);
        product.setSupplier(supplier);

        if (productRepository.update(product) == 0) {
            throw new ProductNotFoundException(product.getId());
        } else {
            var updatedProduct = productRepository.getById(product.getId());
            return ProductDTOConverter.fromProduct(updatedProduct);
        }
    }

    public ProductDTO delete(Integer id) {
        var product = productRepository.findById(id)
                .map(ProductDTOConverter::fromProduct)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.deleteById(id);
        return product;
    }
}
