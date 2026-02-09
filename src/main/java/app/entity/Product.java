package app.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="product_name")
    private String productName;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private Warranty warranty;

}
