package ro.msg.learning.shop.model.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.domain.Address;
import ro.msg.learning.shop.model.domain.Location;

import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LocationOutputDTO {
    private String name;
    private Address address;

    public static LocationOutputDTO fromLocation(Location location) {
        return LocationOutputDTO.builder()
                .name(location.getName())
                .address(location.getAddress())
                .build();
    }
}
