package app.daos;

import app.entity.Role;
import app.entity.User;
import app.exceptions.ValidationException;
import app.services.securityService.securityInterface.ISecurityDAO;
import app.services.validationServices.PasswordService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

import java.time.LocalDateTime;

public class SecurityDAO implements ISecurityDAO {
    EntityManagerFactory emf;

    public SecurityDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public User getVerifiedUser(String email, String password) throws ValidationException {
        try (EntityManager em = emf.createEntityManager()) {
            User user;
            try {
                user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                        .setParameter("email", email)
                        .getSingleResult();
            } catch (NoResultException e) {
                throw new ValidationException("User could not be validated");
            }
            if (!user.verifyPassword(password)) {
                throw new ValidationException("User could not be validated");
            }
            return user;
        }
    }

    @Override
    public User createUser(String email, String password) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            boolean exists = !em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .setMaxResults(1)
                    .getResultList()
                    .isEmpty();
                    if(exists){
                        em.getTransaction().rollback();
                        throw new ValidationException("User already exists");
                    }

            String roleName = "USER";
            Role userRole = em.find(Role.class, roleName);
            if (userRole == null) {
                userRole = new Role(roleName);
                em.persist(userRole);
            }

            String hashed = new PasswordService().hash(password);
            User user = User.builder()
                    .email(email)
                    .password(hashed)
                    .createdAt(LocalDateTime.now())
                    .build();
            user.addRole(userRole);

            em.persist(user);
            em.getTransaction().commit();
            return user;
        }
    }

    @Override
    public Role createRole(String role) {
        String roleName = role.toUpperCase();
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Role existing = em.find(Role.class, roleName);
            if (existing != null) {
                em.getTransaction().commit();
                return existing;
            }

            Role newRole = new Role(roleName);
            em.persist(newRole);

            em.getTransaction().commit();
            return newRole;
        }
    }

    @Override
    public User addUserRole(String email, String role) {
        String roleName = role.toUpperCase();
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            try {
                User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                        .setParameter("email", email)
                        .getSingleResult();

                Role dbRole = em.find(Role.class, roleName);
                if (dbRole == null) {
                    dbRole = new Role(roleName);
                    em.persist(dbRole);
                }

                user.addRole(dbRole);
                em.getTransaction().commit();
                return user;
            } catch (NoResultException e) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                throw new ValidationException("User not found");
            } catch (RuntimeException e) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                throw e;
            }
        }
    }
}