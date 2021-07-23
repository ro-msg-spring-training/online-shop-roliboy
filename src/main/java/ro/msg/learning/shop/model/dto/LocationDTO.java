package ro.msg.learning.shop.model.dto;

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
public class LocationDTO {
    private String name;
    private Address address;
}
