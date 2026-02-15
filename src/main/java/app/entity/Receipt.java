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
@ToString(exclude = "registration")
@Entity
@Table(name="receipts")
public class Receipt implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name ="registration_id", unique = true)
    private ProductRegistration registration;

    @Column(name="purchase_at", nullable = false)
    private LocalDate purchasedAt;

    @Column(name="image_url")
    private String imageUrl;

    public Receipt(LocalDate purchasedAt) {
        this.purchasedAt = purchasedAt;
    }
}
