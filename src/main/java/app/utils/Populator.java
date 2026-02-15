package app.utils;

import app.daos.*;
import app.entity.*;
import app.persistence.IDAO;
import app.persistence.IEntity;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class Populator {

    private final EntityManagerFactory emf;
    private final IDAO<Product> productDAO;
    private final IDAO<ProductRegistration> productRegistrationDAO;
    private final IDAO<Receipt> receiptDAO;
    private final IDAO<Warranty> warrantyDAO;
    private final IDAO<User> userDAO;

    public Populator(EntityManagerFactory emf) {
        this.emf = emf;
        this.productDAO = new ProductDAO(emf);
        this.productRegistrationDAO = new ProductRegistrationDAO(emf);
        this.receiptDAO = new ReceiptDAO(emf);
        this.warrantyDAO = new WarrantyDAO(emf);
        this.userDAO = new UserDAO(emf);
    }

    public Map<String, IEntity> populate() {

        User user1 = new User("Shay@gmail.com", "123", LocalDateTime.now());
        User user2 = new User("Hond12@gmail.com", "123", LocalDateTime.now());
        User user3 = new User("Clad22@gmail.com", "123", LocalDateTime.now());

        userDAO.create(user1);
        userDAO.create(user2);
        userDAO.create(user3);

        LocalDate start = LocalDate.now();

        Product product1 = new Product("Iphone 17");
        Product product2 = new Product("Iphone 18");
        Product product3 = new Product("MacBook Air");

        Receipt receipt1 = new Receipt(start);
        Receipt receipt2 = new Receipt(start);
        Receipt receipt3 = new Receipt(start);

        Warranty warranty1 = new Warranty();
        Warranty warranty2 = new Warranty();
        Warranty warranty3 = new Warranty();

        warranty1.setStartDate(LocalDate.of(2026,2,14));
        warranty1.setWarrantyMonths(12);
        warranty1.calculateEndDate();

        warranty2.setStartDate(LocalDate.of(2026,2,14));
        warranty2.setWarrantyMonths(24);
        warranty2.calculateEndDate();

        warranty3.setStartDate(LocalDate.of(2026,2,14));
        warranty3.setWarrantyMonths(36);
        warranty3.calculateEndDate();

        product1.setWarranty(warranty1);
        product2.setWarranty(warranty2);
        product3.setWarranty(warranty3);

        warranty1.setProduct(product1);
        warranty2.setProduct(product2);
        warranty3.setProduct(product3);

        productDAO.create(product1);
        productDAO.create(product2);
        productDAO.create(product3);

        // add warranty to productRegistration
        ProductRegistration pr1 = new ProductRegistration(start, start, user1, product1, receipt1);
        ProductRegistration pr2 = new ProductRegistration(start, start, user2, product2, receipt2);
        ProductRegistration pr3 = new ProductRegistration(start, start, user3, product3, receipt3);

        receipt1.setRegistration(pr1);
        receipt2.setRegistration(pr2);
        receipt3.setRegistration(pr3);

        pr1.setReceipt(receipt1);
        pr2.setReceipt(receipt2);
        pr3.setReceipt(receipt3);

        pr1.setOwner(user1);
        pr2.setOwner(user2);
        pr3.setOwner(user3);

        productRegistrationDAO.create(pr1);
        productRegistrationDAO.create(pr2);
        productRegistrationDAO.create(pr3);

        return Map.ofEntries(
                Map.entry("user1", user1),
                Map.entry("user2", user2),
                Map.entry("user3", user3),
                Map.entry("product1", product1),
                Map.entry("product2", product2),
                Map.entry("product3", product3),
                Map.entry("receipt1", receipt1),
                Map.entry("receipt2", receipt2),
                Map.entry("receipt3", receipt3),
                Map.entry("warranty1", warranty1),
                Map.entry("warranty2", warranty2),
                Map.entry("warranty3", warranty3),
                Map.entry("pr1", pr1),
                Map.entry("pr2", pr2),
                Map.entry("pr3", pr3)
        );
    }
}
