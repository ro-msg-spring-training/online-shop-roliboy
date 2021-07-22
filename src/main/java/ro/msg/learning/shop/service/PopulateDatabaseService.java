package ro.msg.learning.shop.service;

import org.hibernate.action.internal.CollectionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.LocationNotFoundException;
import ro.msg.learning.shop.exception.ProductNotFoundException;
import ro.msg.learning.shop.model.domain.*;
import ro.msg.learning.shop.model.dto.in.LocationInputDTO;
import ro.msg.learning.shop.model.dto.in.ProductInputDTO;
import ro.msg.learning.shop.model.dto.in.StockInputDTO;
import ro.msg.learning.shop.model.dto.out.LocationOutputDTO;
import ro.msg.learning.shop.model.dto.out.ProductOutputDTO;
import ro.msg.learning.shop.model.dto.out.StockOutputDTO;
import ro.msg.learning.shop.persistence.LocationRepository;
import ro.msg.learning.shop.persistence.ProductRepository;
import ro.msg.learning.shop.persistence.StockRepository;

import java.util.Collection;

@Profile("test")
@Service
public class PopulateDatabaseService {
    @Autowired
    StockRepository stockRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    LocationRepository locationRepository;

    public Collection<LocationOutputDTO> insertLocations(Collection<LocationInputDTO> locations) {
        return locationRepository.saveAll(locations.stream()
                .map(LocationInputDTO::toLocation)
                .toList())
                .stream()
                .map(LocationOutputDTO::fromLocation)
                .toList();
    }

    public Collection<StockOutputDTO> insertStocks(Collection<StockInputDTO> stocks) {
        return stockRepository.saveAll(stocks.stream()
                .map(stockInputDTO -> Stock.builder()
                        .product(productRepository.findById(stockInputDTO.getProductId())
                                .orElseThrow(() -> new ProductNotFoundException(stockInputDTO.getProductId())))
                        .location(locationRepository.findById(stockInputDTO.getLocationId())
                                .orElseThrow(() -> new LocationNotFoundException(stockInputDTO.getLocationId())))
                        .quantity(stockInputDTO.getQuantity())
                        .build())
                .toList())
                .stream()
                .map(StockOutputDTO::fromStock)
                .toList();
    }

    public Collection<ProductOutputDTO> insertProducts(Collection<ProductInputDTO> products) {
        return productRepository.saveAll(products.stream()
                .map(ProductInputDTO::toProduct)
                .toList())
                .stream()
                .peek(product -> product.setCategory(new ProductCategory("name", "description")))
                .map(ProductOutputDTO::fromProduct)
                .toList();
    }

    public void drop() {
        stockRepository.deleteAll();
        productRepository.deleteAll();
        locationRepository.deleteAll();
    }
}
