package ro.msg.learning.shop.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.Product;

@Repository
//TODO: generic repository interface (implicit id type)?
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
