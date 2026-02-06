package app.daos;

import app.entity.Receipt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PrePersist;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ReceiptDAO {

    private static EntityManagerFactory emf;

    public ReceiptDAO(EntityManagerFactory emf){
        this.emf = emf;
    }

    @PrePersist
    public Receipt createReceipt(Receipt r) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(r);
            em.getTransaction().commit();
        }
        return r;
    }

    public Long getReceiptCount() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Long> q1 = em.createQuery("SELECT COUNT(r) FROM Receipt r", Long.class);
            return q1.getSingleResult();
        }
    }


    public List<Receipt> allReceipts() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Receipt> query = em.createQuery("SELECT r FROM Receipt r", Receipt.class);
            query.setMaxResults(30);
            return query.getResultList();
        }
    }
}

