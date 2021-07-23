package ro.msg.learning.shop.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.converter.StockDTOConverter;
import ro.msg.learning.shop.exception.LocationNotFoundException;
import ro.msg.learning.shop.model.dto.StockDTO;
import ro.msg.learning.shop.persistence.LocationRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class StockService {
    private final LocationRepository locationRepository;

    public Collection<StockDTO> export(Integer locationId) {
        var location = locationRepository.findById(locationId)
                .orElseThrow(() -> new LocationNotFoundException(locationId));
        return location.getStocks().stream()
                .map(StockDTOConverter::fromStock)
                .toList();
    }
}
