package ro.msg.learning.shop.service.strategy;

import ro.msg.learning.shop.model.OrderLocation;
import ro.msg.learning.shop.model.domain.OrderDetail;

import java.util.Collection;

public interface FindLocationStrategy {
    Collection<OrderLocation> getOrderLocations(Collection<OrderDetail> orderDetails);
}
