package ro.msg.learning.shop.model.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.domain.Address;
import ro.msg.learning.shop.model.domain.Location;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LocationInputDTO {
    private String name;
    private Address address;

    public Location toLocation() {
        return Location.builder()
                .name(name)
                .address(address)
                .build();
    }
}
