package app.entity;

import app.persistence.IEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude ={"owner", "receipt"})
@Entity
@Table(name = "registered_products")
public class ProductRegistration implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToOne(optional = false)
    @JoinColumn(name ="product_id")
    private Product product;

    @OneToOne(mappedBy = "registration", cascade = CascadeType.ALL)
    private Receipt receipt;

    @Column(name = "purchased_at", nullable = false, updatable = false)
    private LocalDate purchasedAt;

    @Column(name = "registered_at", nullable = false, updatable = false)
    private LocalDate registeredAt = LocalDate.now();

}
