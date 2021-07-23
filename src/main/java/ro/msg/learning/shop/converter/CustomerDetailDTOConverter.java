package ro.msg.learning.shop.converter;

import ro.msg.learning.shop.model.domain.Customer;
import ro.msg.learning.shop.model.dto.CustomerDetailDTO;

public class CustomerDetailDTOConverter {
    public static CustomerDetailDTO fromCustomer(Customer customer) {
        return CustomerDetailDTO.builder()
                .username(customer.getUsername())
                .password(customer.getPassword())
                .build();
    }
}
