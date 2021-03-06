package ro.msg.learning.shop.model.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "`ORDER`")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class Order extends GenericEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    private Location shippedFrom;
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;
    private LocalDateTime createdAt;
    @Embedded
    private Address address;
}
