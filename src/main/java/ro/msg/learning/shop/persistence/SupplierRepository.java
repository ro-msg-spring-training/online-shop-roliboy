package ro.msg.learning.shop.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.domain.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
