package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.converter.CustomerDetailDTOConverter;
import ro.msg.learning.shop.model.dto.CustomerDetailDTO;
import ro.msg.learning.shop.persistence.CustomerRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserDetailService {
    private final CustomerRepository customerRepository;

    public Collection<CustomerDetailDTO> listUsers() {
        return customerRepository.findAll().stream()
                .map(CustomerDetailDTOConverter::fromCustomer)
                .toList();
    }
}
