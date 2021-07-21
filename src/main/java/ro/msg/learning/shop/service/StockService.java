package ro.msg.learning.shop.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.LocationNotFoundException;
import ro.msg.learning.shop.model.dto.out.StockOutputDTO;
import ro.msg.learning.shop.persistence.LocationRepository;

import java.util.Collection;

@Service
public class StockService {
    @Autowired
    LocationRepository locationRepository;

    public Collection<StockOutputDTO> export(Integer locationId) {
        var location = locationRepository.findById(locationId)
                .orElseThrow(() -> new LocationNotFoundException(locationId));
        return location.getStocks().stream()
                .map(StockOutputDTO::fromStock)
                .toList();
    }
}
