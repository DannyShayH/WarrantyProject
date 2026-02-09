package app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "registered_products")
public class ProductRegistration {

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
    private LocalDate purchaseAt;

    @Column(name = "registered_at", nullable = false, updatable = false)
    private LocalDate registeredAt = LocalDate.now();
}
