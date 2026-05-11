package app.daos;

import app.entity.Product;
import app.entity.ProductRegistration;
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
            if(p.getOwner() != null){
                p.setOwner(em.merge(p.getOwner()));
            }
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

            var receiptIds = em.createQuery(
                    "SELECT pr.receipt.id FROM ProductRegistration pr " +
                            "WHERE pr.product.id = :id AND pr.receipt IS NOT NULL", Long.class)
                            .setParameter("id", p.getId())
                            .getResultList();
            em.createQuery("DELETE FROM ProductRegistration pr WHERE pr.product.id = :id")
                            .setParameter("id", p.getId())
                            .executeUpdate();
            if(!receiptIds.isEmpty()){
                em.createQuery("DELETE FROM Receipt r WHERE r.id IN :ids")
                        .setParameter("ids", receiptIds)
                        .executeUpdate();
            }

            em.remove(product);
            em.getTransaction().commit();
            return p.getId();
        }
    }

    @Override
    public Set<Product> get() {
        try(EntityManager em = emf.createEntityManager()){
            return new HashSet(em.createQuery("SELECT p FROM Product p").getResultList());
        }
    }

    public Set<Product> getAllByUserId(Long userId){
        try(EntityManager em = emf.createEntityManager()){
            return new HashSet<>(em.createQuery(
                    "SELECT p FROM Product p WHERE p.owner.id = :userId", Product.class)
                    .setParameter("userId", userId)
                    .getResultList());
        }
    }
}

