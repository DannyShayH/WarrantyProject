package app.daos;

import app.entity.User;
import app.persistence.IDAO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

public class UserDAO implements IDAO<User> {

    private final EntityManagerFactory emf;

    public UserDAO(EntityManagerFactory emf){
        if(emf == null) throw new IllegalArgumentException("EntityManagerFactory cannot be null");
        this.emf = emf;
    }

    @Override
    public User create(User u) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
            return u;
        }
    }

    @Override
    public User getByID(Long id) {
        try(EntityManager em = emf.createEntityManager()) {
            User user = em.find(User.class, id);
            if (user == null)
                throw new EntityNotFoundException("No entity found with id: " + id);
            return user;
        }
    }

    @Override
    public User update(User u) {
        try(EntityManager em = emf.createEntityManager()){
            User foundUser = em.find(User.class, u.getId());
            if(foundUser == null)
                throw new EntityNotFoundException("No entity found with id: "+ u.getId());
            em.getTransaction().begin();
            User user = em.merge(u);
            em.getTransaction().commit();
            return user;
        }
    }

    @Override
    public Long delete(User u) {
        try(EntityManager em = emf.createEntityManager()){
            User user = em.find(User.class, u.getId());
            if(user == null)
                throw new EntityNotFoundException("No entity found with id: "+ u.getId());
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
            return user.getId();
        }
    }

    @Override
    public Set<User> get() {
        try(EntityManager em = emf.createEntityManager()){
            return new HashSet(em.createQuery("SELECT u FROM User u").getResultList());
        }
    }
}

