package ro.msg.learning.shop.model.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.domain.Address;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderInputDTO {
    private LocalDateTime timestamp;
    private Address address;
    private List<OrderDetailInputDTO> products;
}
