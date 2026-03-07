package app.entity;

import app.persistence.IEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.Temporal;

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

    @OneToOne(mappedBy = "warranty")
    private Product product;

    @Column(name ="warranty_months", nullable = false, updatable = false)
    private int warrantyMonths;

    @Column(name="start_date", nullable = false, updatable = false)
    private LocalDate startDate;

    @Column(name="end_date", nullable = false, updatable = false)
    private LocalDate endDate;

    @Column(name="notified_90_days")
    private boolean notified90Days;

    @Column(name="notified_60_days")
    private boolean notified60Days;

    @Column(name="notified_30_days")
    private boolean notified30Days;

    @Column(name="notified_expired")
    private boolean notifiedExpired;

    public LocalDate calculateEndDate(){
        if(endDate == null){
            endDate = startDate.plusMonths(warrantyMonths);
        }
        return endDate;
    }

}
