package ro.msg.learning.shop.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Customer extends GenericEntity {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String emailAddress;
}
