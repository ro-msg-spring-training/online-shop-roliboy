package ro.msg.learning.shop.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.domain.Location;
import ro.msg.learning.shop.model.domain.Product;
import ro.msg.learning.shop.model.domain.Stock;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    @Query("select s from Stock s where s.location = :location and s.product = :product")
    Optional<Stock> findByLocationAndProduct(
            @Param("location") Location location,
            @Param("product") Product product);
}
