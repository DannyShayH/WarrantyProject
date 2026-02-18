package app.entity;

import app.persistence.IEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude ="warranty")
@Entity
@Table(name="products")
public class Product implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="product_name")
    private String productName;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private Warranty warranty;
}
