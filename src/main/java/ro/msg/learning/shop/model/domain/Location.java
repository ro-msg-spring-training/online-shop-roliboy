package ro.msg.learning.shop.model.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Location extends GenericEntity {
    private String name;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "location", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Stock> stocks;
}
