package app.daos;

import app.entity.ProductRegistration;
import app.persistence.IDAO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

public class ProductRegistrationDAO implements IDAO<ProductRegistration> {

    private final EntityManagerFactory emf;

    public ProductRegistrationDAO(EntityManagerFactory emf){
        this.emf = emf;
    }

    @Override
    public ProductRegistration create(ProductRegistration p) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        return p;
        }
    }

    @Override
    public ProductRegistration getByID(Long id) {
        try(EntityManager em = emf.createEntityManager()) {
            ProductRegistration productRegistration = em.find(ProductRegistration.class, id);
            if (productRegistration == null)
                throw new EntityNotFoundException("No entity found with id: " + id);
            return productRegistration;
        }
    }

    @Override
    public ProductRegistration update(ProductRegistration pr) {
        try(EntityManager em = emf.createEntityManager()){
            ProductRegistration foundProduct = em.find(ProductRegistration.class, pr.getId());
            if(foundProduct == null)
                throw new EntityNotFoundException("No entity found with id: "+ pr.getId());
            em.getTransaction().begin();
            ProductRegistration productRegistration = em.merge(pr);
            em.getTransaction().commit();
            return productRegistration;
        }
    }

    @Override
    public Long delete(ProductRegistration pr) {
        try(EntityManager em = emf.createEntityManager()){
            ProductRegistration productRegistration = em.find(ProductRegistration.class, pr.getId());
            if(productRegistration == null)
                throw new EntityNotFoundException("No entity found with id: "+ pr.getId());
            em.getTransaction().begin();
            em.remove(productRegistration);
            em.getTransaction().commit();
            return productRegistration.getId();
        }
    }

    @Override
    public Set<ProductRegistration> get() {
        try(EntityManager em = emf.createEntityManager()){
            return new HashSet(em.createQuery("SELECT pr FROM ProductRegistration pr").getResultList());
        }
    }
}

