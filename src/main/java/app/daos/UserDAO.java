package app.daos;

import app.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PrePersist;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UserDAO {

    private static EntityManagerFactory emf;

    public UserDAO(EntityManagerFactory emf){
        this.emf = emf;
    }

    @PrePersist
    public User createUser(User u) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
        }
        return u;
    }

    public Long getUserCount() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Long> q1 = em.createQuery("SELECT COUNT(u) FROM User u", Long.class);
            return q1.getSingleResult();
        }
    }


    public List<User> allUsers() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            query.setMaxResults(30);
            return query.getResultList();
        }
    }
}

