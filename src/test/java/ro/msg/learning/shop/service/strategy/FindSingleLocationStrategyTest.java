package ro.msg.learning.shop.service.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.msg.learning.shop.exception.OrderNotFulfilledException;
import ro.msg.learning.shop.model.OrderLocation;
import ro.msg.learning.shop.model.domain.Location;
import ro.msg.learning.shop.model.domain.OrderDetail;
import ro.msg.learning.shop.model.domain.Product;
import ro.msg.learning.shop.model.domain.Stock;
import ro.msg.learning.shop.persistence.LocationRepository;

import java.util.Collection;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class FindSingleLocationStrategyTest {
    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private FindSingleLocationStrategy findSingleLocationStrategy;

    private Product firstProduct;
    private Product secondProduct;

    private Location firstLocation;
    private Location secondLocation;

    private Stock firstLocationFirstProductStock;
    private Stock firstLocationSecondProductStock;
    private Stock secondLocationFirstProductStock;
    private Stock secondLocationSecondProductStock;

    @BeforeEach
    void setUp() {
        firstProduct = Product.builder().id(1).build();
        secondProduct = Product.builder().id(2).build();

        firstLocationFirstProductStock = Stock.builder()
                .location(firstLocation)
                .product(firstProduct)
                .build();
        firstLocationSecondProductStock = Stock.builder()
                .location(firstLocation)
                .product(secondProduct)
                .build();
        secondLocationFirstProductStock = Stock.builder()
                .location(secondLocation)
                .product(firstProduct)
                .build();
        secondLocationSecondProductStock = Stock.builder()
                .location(secondLocation)
                .product(secondProduct)
                .build();

        firstLocation = Location.builder()
                .id(1)
                .stocks(List.of(
                        firstLocationFirstProductStock,
                        firstLocationSecondProductStock))
                .build();
        secondLocation = Location.builder()
                .id(2)
                .stocks(List.of(
                        secondLocationFirstProductStock,
                        secondLocationSecondProductStock))
                .build();

        Mockito.when(locationRepository.findAll()).thenReturn(
                List.of(firstLocation, secondLocation));
    }

    @Test
    void everywhereOutOfStock_throwsOrderNotFulfilled() {
        firstLocationFirstProductStock.setQuantity(0);
        firstLocationSecondProductStock.setQuantity(0);
        secondLocationFirstProductStock.setQuantity(0);
        secondLocationSecondProductStock.setQuantity(0);

        Collection<OrderDetail> orderDetails = List.of(
                OrderDetail.builder()
                        .product(firstProduct)
                        .quantity(1)
                        .build(),
                OrderDetail.builder()
                        .product(secondProduct)
                        .quantity(1)
                        .build());

        Assertions.assertThrows(
                OrderNotFulfilledException.class,
                () -> findSingleLocationStrategy.getOrderLocations(orderDetails));
    }

    @Test
    void inStockAtDifferentLocations_throwsOrderNotFulfilled() {
        firstLocationFirstProductStock.setQuantity(42);
        firstLocationSecondProductStock.setQuantity(0);
        secondLocationFirstProductStock.setQuantity(0);
        secondLocationSecondProductStock.setQuantity(1337);

        Collection<OrderDetail> orderDetails = List.of(
                OrderDetail.builder()
                        .product(firstProduct)
                        .quantity(1)
                        .build(),
                OrderDetail.builder()
                        .product(secondProduct)
                        .quantity(1)
                        .build());

        Assertions.assertThrows(
                OrderNotFulfilledException.class,
                () -> findSingleLocationStrategy.getOrderLocations(orderDetails));
    }

    @Test
    void onlyOneProductInStock_throwsOrderNotFulfilled() {
        firstLocationFirstProductStock.setQuantity(727);
        firstLocationSecondProductStock.setQuantity(0);
        secondLocationFirstProductStock.setQuantity(0);
        secondLocationSecondProductStock.setQuantity(0);

        Collection<OrderDetail> orderDetails = List.of(
                OrderDetail.builder()
                        .product(firstProduct)
                        .quantity(1)
                        .build(),
                OrderDetail.builder()
                        .product(secondProduct)
                        .quantity(1)
                        .build());

        Assertions.assertThrows(
                OrderNotFulfilledException.class,
                () -> findSingleLocationStrategy.getOrderLocations(orderDetails));
    }

    @Test
    void inStockEverywhere_returnsOrdersForLocationWithLowestId() {
        firstLocationFirstProductStock.setQuantity(111);
        firstLocationSecondProductStock.setQuantity(111);
        secondLocationFirstProductStock.setQuantity(222);
        secondLocationSecondProductStock.setQuantity(222);

        Collection<OrderDetail> orderDetails = List.of(
                OrderDetail.builder()
                        .product(firstProduct)
                        .quantity(1)
                        .build(),
                OrderDetail.builder()
                        .product(secondProduct)
                        .quantity(2)
                        .build());

        Assertions.assertEquals(
                List.of(
                        OrderLocation.builder()
                                .location(firstLocation)
                                .product(firstProduct)
                                .quantity(1)
                                .build(),
                        OrderLocation.builder()
                                .location(firstLocation)
                                .product(secondProduct)
                                .quantity(2)
                                .build()),
                findSingleLocationStrategy.getOrderLocations(orderDetails));
    }
}
