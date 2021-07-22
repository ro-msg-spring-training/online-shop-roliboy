package ro.msg.learning.shop.model.domain;

import lombok.*;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Customer extends GenericEntity {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String emailAddress;
}
