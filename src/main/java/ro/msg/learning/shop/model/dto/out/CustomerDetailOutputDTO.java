package ro.msg.learning.shop.model.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.domain.Customer;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDetailOutputDTO {
    private String username;
    private String password;

    public static CustomerDetailOutputDTO fromCustomer(Customer customer) {
        return CustomerDetailOutputDTO.builder()
                .username(customer.getUsername())
                .password(customer.getPassword())
                .build();
    }
}
