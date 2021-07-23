package ro.msg.learning.shop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.domain.Customer;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDetailDTO {
    private String username;
    private String password;
}
