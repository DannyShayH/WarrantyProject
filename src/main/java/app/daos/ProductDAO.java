package app.daos;

import app.entity.Product;
import app.persistence.IDAO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

public class ProductDAO implements IDAO<Product> {

    private final EntityManagerFactory emf;

    public ProductDAO(EntityManagerFactory emf){
        this.emf = emf;
    }

    @Override
    public Product create(Product p) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return p;
        }
    }

    @Override
    public Product getByID(Long id) {
        try(EntityManager em = emf.createEntityManager()) {
            Product product = em.find(Product.class, id);
            if (product == null)
                throw new EntityNotFoundException("No entity found with id: " + id);
            return product;
        }
    }

    @Override
    public Product update(Product p) {
        try(EntityManager em = emf.createEntityManager()){
            Product foundProduct = em.find(Product.class, p.getId());
            if(foundProduct == null)
                throw new EntityNotFoundException("No entity found with id: "+ p.getId());
            em.getTransaction().begin();
            Product product = em.merge(p);
            em.getTransaction().commit();
            return product;
        }
    }

    @Override
    public Long delete(Product p) {
        try(EntityManager em = emf.createEntityManager()){
            Product product = em.find(Product.class, p.getId());
            if(product == null)
                throw new EntityNotFoundException("No entity found with id: "+ p.getId());
            em.getTransaction().begin();
            em.remove(product);
            em.getTransaction().commit();
            return product.getId();
        }
    }

    @Override
    public Set<Product> get() {
        try(EntityManager em = emf.createEntityManager()){
            return new HashSet(em.createQuery("SELECT p FROM Product p").getResultList());
        }
    }
}

