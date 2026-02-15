package app.daos;

import app.entity.Receipt;
import app.persistence.IDAO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

public class ReceiptDAO implements IDAO<Receipt> {

    private final EntityManagerFactory emf;

    public ReceiptDAO(EntityManagerFactory emf){
        this.emf = emf;
    }

    @Override
    public Receipt create(Receipt r) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(r);
            em.getTransaction().commit();
            return r;
        }
    }

    @Override
    public Receipt getByID(Long id) {
        try(EntityManager em = emf.createEntityManager()) {
            Receipt receipt = em.find(Receipt.class, id);
            if (receipt == null)
                throw new EntityNotFoundException("No entity found with id: " + id);
            return receipt;
        }
    }

    @Override
    public Receipt update(Receipt r) {
        try(EntityManager em = emf.createEntityManager()){
            Receipt foundReceipt = em.find(Receipt.class, r.getId());
            if(foundReceipt == null)
                throw new EntityNotFoundException("No entity found with id: "+ r.getId());
            em.getTransaction().begin();
            Receipt receipt = em.merge(r);
            em.getTransaction().commit();
            return receipt;
        }
    }

    @Override
    public Long delete(Receipt r) {
        try(EntityManager em = emf.createEntityManager()){
            Receipt receipt = em.find(Receipt.class, r.getId());
            if(receipt == null)
                throw new EntityNotFoundException("No entity found with id: "+ r.getId());
            em.getTransaction().begin();
            em.remove(receipt);
            em.getTransaction().commit();
            return receipt.getId();
        }
    }

    @Override
    public Set<Receipt> get() {
        try(EntityManager em = emf.createEntityManager()){
            return new HashSet(em.createQuery("SELECT r FROM Receipt r").getResultList());
        }
    }
}

