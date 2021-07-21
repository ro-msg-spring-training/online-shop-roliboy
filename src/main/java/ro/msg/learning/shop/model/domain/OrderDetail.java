package ro.msg.learning.shop.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDetail extends GenericEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORDER_ID")
    private Order order;
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
    private Integer quantity;
}
