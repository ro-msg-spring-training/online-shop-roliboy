package ro.msg.learning.shop.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.Stock;

@Repository
//TODO: replace integer with id class (if composite key will be used)
public interface StockRepository extends JpaRepository<Stock, Integer> {
}
