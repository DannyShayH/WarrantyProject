package app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name ="e-mail", nullable = false)
    private String email;

    @Column(name ="password", nullable = false)
    private String password;


    @Column(name ="created_at", nullable = false)
    private LocalDateTime createdAt;
}
