package ro.msg.learning.shop.model.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Product extends GenericEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;
    @ManyToOne(fetch = FetchType.EAGER)
    private ProductCategory category;
    @ManyToOne(fetch = FetchType.EAGER)
    private Supplier supplier;
    private String imageUrl;
}
