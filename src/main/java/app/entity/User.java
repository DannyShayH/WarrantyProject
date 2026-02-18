package app.entity;

import app.persistence.IEntity;
import app.services.PasswordService;
import jakarta.persistence.*;
import lombok.*;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude ="registrationlist")
@Entity
@Table(name = "users")
public class User implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "e-mail", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<ProductRegistration> registrationlist = new ArrayList<>();


    public boolean validatePassword(String password, PasswordService passwordService){
        return passwordService.verify(password, this.password);
    }
}
