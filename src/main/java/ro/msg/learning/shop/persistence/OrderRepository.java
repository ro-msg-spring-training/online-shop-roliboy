package ro.msg.learning.shop.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
