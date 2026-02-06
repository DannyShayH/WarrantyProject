package app.daos;

import app.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PrePersist;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductDAO {

    private static EntityManagerFactory emf;

    public ProductDAO(EntityManagerFactory emf){
        this.emf = emf;
    }

    @PrePersist
    public Product createProduct(Product p) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        }
        return p;
    }

    public Long getProductCount() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Long> q1 = em.createQuery("SELECT COUNT(p) FROM Product p", Long.class);
            return q1.getSingleResult();
        }
    }


    public List<Product> allProducts() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
            query.setMaxResults(30);
            return query.getResultList();
        }
    }
}

