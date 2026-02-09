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
@Table(name="receipts")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name ="registration_id", unique = true)
    private ProductRegistration registration;

    @Column(name="purchase_at", nullable = false)
    private LocalDate purchaseAt;

    @Column(name="image_url")
    private String imageUrl;

}
