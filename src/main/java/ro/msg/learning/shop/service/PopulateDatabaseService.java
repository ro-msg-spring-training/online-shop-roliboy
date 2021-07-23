package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.converter.LocationDTOConverter;
import ro.msg.learning.shop.converter.ProductDTOConverter;
import ro.msg.learning.shop.converter.StockDTOConverter;
import ro.msg.learning.shop.exception.LocationNotFoundException;
import ro.msg.learning.shop.exception.ProductNotFoundException;
import ro.msg.learning.shop.model.domain.ProductCategory;
import ro.msg.learning.shop.model.domain.Stock;
import ro.msg.learning.shop.model.dto.LocationDTO;
import ro.msg.learning.shop.model.dto.ProductDTO;
import ro.msg.learning.shop.model.dto.StockDTO;
import ro.msg.learning.shop.persistence.LocationRepository;
import ro.msg.learning.shop.persistence.ProductRepository;
import ro.msg.learning.shop.persistence.StockRepository;

import java.util.Collection;

@Profile("test")
@Service
@RequiredArgsConstructor
public class PopulateDatabaseService {
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;

    public Collection<LocationDTO> insertLocations(Collection<LocationDTO> locations) {
        return locationRepository.saveAll(locations.stream()
                .map(LocationDTOConverter::toLocation)
                .toList())
                .stream()
                .map(LocationDTOConverter::fromLocation)
                .toList();
    }

    public Collection<StockDTO> insertStocks(Collection<StockDTO> stocks) {
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
                .map(StockDTOConverter::fromStock)
                .toList();
    }

    public Collection<ProductDTO> insertProducts(Collection<ProductDTO> products) {
        return productRepository.saveAll(products.stream()
                .map(ProductDTOConverter::toProduct)
                .toList())
                .stream()
                .peek(product -> product.setCategory(new ProductCategory("name", "description")))
                .map(ProductDTOConverter::fromProduct)
                .toList();
    }

    public void drop() {
        stockRepository.deleteAll();
        productRepository.deleteAll();
        locationRepository.deleteAll();
    }
}
