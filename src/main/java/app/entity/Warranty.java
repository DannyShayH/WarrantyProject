package app.entity;

import app.persistence.IEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude ="product")
@Entity
@Table(name="warranties")
public class Warranty implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name ="warranty_months", nullable = false, updatable = false)
    private int warrantyMonths;

    @Column(name="start_date", nullable = false, updatable = false)
    private LocalDate startDate;

    @Column(name="end_date", nullable = false, updatable = false)
    private LocalDate endDate;

    public void calculateEndDate(){this.endDate = startDate.plusMonths(warrantyMonths);}

}
