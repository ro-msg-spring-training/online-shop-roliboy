package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.dto.out.CustomerDetailOutputDTO;
import ro.msg.learning.shop.persistence.CustomerRepository;

import java.util.Collection;

@Service
public class UserDetailService {
    @Autowired
    CustomerRepository customerRepository;

    public Collection<CustomerDetailOutputDTO> listUsers() {
        return customerRepository.findAll().stream()
                .map(CustomerDetailOutputDTO::fromCustomer)
                .toList();
    }
}
