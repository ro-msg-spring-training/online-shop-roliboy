package ro.msg.learning.shop.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.domain.ProductCategory;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    @Query("select pc from ProductCategory pc where pc.name = :name")
    Optional<ProductCategory> findByName(
            @Param("name") String name);
}
