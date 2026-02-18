package app.daos;

import app.entity.*;
import app.persistence.IRetrieveDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.Set;

public class RetrieveDAO implements IRetrieveDAO {
    private EntityManagerFactory emf;

    public RetrieveDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Set<Product> getAllProductsForUsers(long userId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Product> query = em.createQuery("SELECT p " +
                    "FROM ProductRegistration pr " +
                    "JOIN pr.product p " +
                    "WHERE pr.owner.id = :userId", Product.class);
            query.setParameter("userId", userId);
            return new HashSet<>(query.getResultList());
        }
    }

    @Override
    public Set<User> getAllUsersForProduct(String productName) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<User> query = em.createQuery("SELECT DISTINCT pr.owner " +
                    "FROM ProductRegistration pr " +
                    "WHERE pr.product.productName = :productName", User.class);
            query.setParameter("productName", productName);
            return new HashSet<>(query.getResultList());
        }
    }

    @Override
    public Set<Warranty> getAllWarrantiesForUsers(long userId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Warranty> query = em.createQuery("SELECT DISTINCT pr.product.warranty " +
                    "FROM ProductRegistration pr " +
                    "WHERE pr.owner.id = :userId", Warranty.class);
            query.setParameter("userId", userId);
            return new HashSet<>(query.getResultList());
        }
    }

    @Override
    public Set<Receipt> getAllReceiptForUsers(long userId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Receipt> query = em.createQuery("SELECT DISTINCT pr.receipt " +
                    "FROM ProductRegistration pr " +
                    "WHERE pr.owner.id = :userId", Receipt.class);
            query.setParameter("userId", userId);
            return new HashSet<>(query.getResultList());
        }
    }

    @Override
    public Set<Receipt> getReceiptWithSpecificPrice(double price) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Receipt> query = em.createQuery("SELECT DISTINCT pr.receipt " +
                    "FROM ProductRegistration pr " +
                    "WHERE pr.receipt.price = :price", Receipt.class);
            query.setParameter("price", price);
            return new HashSet<>(query.getResultList());
        }
    }

    @Override
    public Set<Receipt> getReceiptWithPriceRange(double price) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Receipt> query = em.createQuery("SELECT DISTINCT pr.receipt " +
                    "FROM ProductRegistration pr " +
                    "WHERE pr.receipt.price < :maxPrice", Receipt.class);
            query.setParameter("maxPrice", 4000);
            return new HashSet<>(query.getResultList());
        }
    }

    @Override
    public Set<Product> getProductsWithUserEmailDomain(String domain) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Product> query = em.createQuery("SELECT DISTINCT pr.product " +
                    "FROM ProductRegistration pr " +
                    "WHERE pr.owner.email " +
                    "LIKE :domain", Product.class);
            query.setParameter("domain", domain);
            return new HashSet<>(query.getResultList());
        }
    }

}
