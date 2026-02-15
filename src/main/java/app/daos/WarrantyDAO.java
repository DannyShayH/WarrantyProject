package app.daos;

import app.entity.Warranty;
import app.persistence.IDAO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

public class WarrantyDAO implements IDAO<Warranty> {

    private final EntityManagerFactory emf;

    public WarrantyDAO(EntityManagerFactory emf){
        this.emf = emf;
    }

    @Override
    public Warranty create(Warranty w) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(w);
            em.getTransaction().commit();
            return w;
        }
    }

    @Override
    public Warranty getByID(Long id) {
        try(EntityManager em = emf.createEntityManager()) {
            Warranty warranty = em.find(Warranty.class, id);
            if (warranty == null)
                throw new EntityNotFoundException("No entity found with id: " + id);
            return warranty;
        }
    }

    @Override
    public Warranty update(Warranty w) {
        try(EntityManager em = emf.createEntityManager()){
            Warranty foundWarranty = em.find(Warranty.class, w.getId());
            if(foundWarranty == null)
                throw new EntityNotFoundException("No entity found with id: "+ w.getId());
            em.getTransaction().begin();
            Warranty warranty = em.merge(w);
            em.getTransaction().commit();
            return warranty;
        }
    }

    @Override
    public Long delete(Warranty w) {
        try(EntityManager em = emf.createEntityManager()){
            Warranty warranty = em.find(Warranty.class, w.getId());
            if(warranty == null)
                throw new EntityNotFoundException("No entity found with id: "+ w.getId());
            em.getTransaction().begin();
            em.remove(warranty);
            em.getTransaction().commit();
            return warranty.getId();
        }
    }

    @Override
    public Set<Warranty> get() {
        try(EntityManager em = emf.createEntityManager()){
            return new HashSet(em.createQuery("SELECT w FROM Warranty w").getResultList());
        }
    }
}

