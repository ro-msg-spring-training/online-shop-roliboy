package ro.msg.learning.shop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.domain.Address;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDTO {
    private LocalDateTime timestamp;
    private Address address;
    private List<OrderDetailDTO> products;
}
