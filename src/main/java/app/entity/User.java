package app.entity;

import app.persistence.IEntity;
import app.services.securityService.securityInterface.ISecurityUser;
import jakarta.persistence.*;
import lombok.*;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude ="registrationlist")
@Entity
@Table(name = "users")
public class User implements IEntity, ISecurityUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductRegistration> registrationlist = new ArrayList<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles = new HashSet<>();

    public User(String email, String hashedPassword){
        this.email = email;
        this.password = hashedPassword;
    }

    @Override
    public Set<String> getRolesAsStrings() {
        return this.roles.stream().map(Role::getRoleName).collect(Collectors.toSet());
    }

    @Override
    public boolean verifyPassword(String pw) {return BCrypt.checkpw(pw, this.password);}

    @Override
    public void addRole(Role role) {this.roles.add(role);}

    @Override
    public void removeRole(String role) {
        if(role == null){
            roles.removeIf(r -> role.equals(r.getRoleName()));
        }
    }
}
