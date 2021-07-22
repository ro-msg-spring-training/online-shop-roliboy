package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.model.dto.out.CustomerDetailOutputDTO;
import ro.msg.learning.shop.service.UserDetailService;

import java.util.Collection;


@RestController
@RequestMapping("/api/users")
public class UserDetailsController {
    @Autowired
    UserDetailService userDetailService;

    @GetMapping(
            value = "",
            produces = {"application/json"}
    )
    public ResponseEntity<Collection<CustomerDetailOutputDTO>> list() {
        return new ResponseEntity<>(
                userDetailService.listUsers(),
                HttpStatus.OK
        );
    }
}
