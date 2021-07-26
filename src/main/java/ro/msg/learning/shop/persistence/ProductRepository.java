package ro.msg.learning.shop.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.domain.Product;

import javax.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("update Product p set " +
            "p.name = :#{#product.name}, " +
            "p.description = :#{#product.description}, " +
            "p.price = :#{#product.price}, " +
            "p.weight = :#{#product.weight}, " +
            "p.category = :#{#product.category}, " +
            "p.supplier = :#{#product.supplier}, " +
            "p.imageUrl = :#{#product.imageUrl} " +
            "where p.id = :#{#product.id}")
    Integer update(@Param("product") Product product);
}
