package app.daos;

import app.entity.LawData;
import app.persistence.IDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class LawDataDAO implements IDAO<LawData> {

    private final EntityManagerFactory emf;

    public LawDataDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public LawData create(LawData lawData) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(lawData);
            em.getTransaction().commit();
            return lawData;
        }
    }

    @Override
    public Set<LawData> get() {
        try (EntityManager em = emf.createEntityManager()) {
            return new HashSet<>(em.createQuery("SELECT l FROM LawData l", LawData.class).getResultList());
        }
    }

    @Override
    public LawData getByID(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            LawData lawData = em.find(LawData.class, id);
            if (lawData == null) {
                throw new EntityNotFoundException("No entity found with id: " + id);
            }
            return lawData;
        }
    }

    @Override
    public LawData update(LawData lawData) {
        try (EntityManager em = emf.createEntityManager()) {
            LawData found = em.find(LawData.class, lawData.getId());
            if (found == null) {
                throw new EntityNotFoundException("No entity found with id: " + lawData.getId());
            }
            em.getTransaction().begin();
            LawData merged = em.merge(lawData);
            em.getTransaction().commit();
            return merged;
        }
    }

    @Override
    public Long delete(LawData lawData) {
        try (EntityManager em = emf.createEntityManager()) {
            LawData found = em.find(LawData.class, lawData.getId());
            if (found == null) {
                throw new EntityNotFoundException("No entity found with id: " + lawData.getId());
            }
            em.getTransaction().begin();
            em.remove(found);
            em.getTransaction().commit();
            return found.getId();
        }
    }
}
