package app.daos;

import app.entity.Warranty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PrePersist;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class WarrantyDAO {

    private static EntityManagerFactory emf;

    public WarrantyDAO(EntityManagerFactory emf){
        this.emf = emf;
    }

    @PrePersist
    public Warranty createWarranty(Warranty w) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(w);
            em.getTransaction().commit();
        }
        return w;
    }

    public Long getWarrantyCount() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Long> q1 = em.createQuery("SELECT COUNT(w) FROM Warranty w", Long.class);
            return q1.getSingleResult();
        }
    }


    public List<Warranty> allWarranties() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Warranty> query = em.createQuery("SELECT w FROM Warranty w", Warranty.class);
            query.setMaxResults(30);
            return query.getResultList();
        }
    }
}

