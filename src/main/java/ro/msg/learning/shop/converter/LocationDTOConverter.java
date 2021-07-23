package ro.msg.learning.shop.converter;

import ro.msg.learning.shop.model.domain.Location;
import ro.msg.learning.shop.model.dto.LocationDTO;

public class LocationDTOConverter {
    public static Location toLocation(LocationDTO locationDTO) {
        return Location.builder()
                .name(locationDTO.getName())
                .address(locationDTO.getAddress())
                .build();
    }

    public static LocationDTO fromLocation(Location location) {
        return LocationDTO.builder()
                .name(location.getName())
                .address(location.getAddress())
                .build();
    }
}
