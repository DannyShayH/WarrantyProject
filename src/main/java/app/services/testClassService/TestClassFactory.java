package app.services.testClassService;

import app.config.HibernateConfig;
import app.daos.ProductDAO;
import app.daos.UserDAO;
import app.daos.WarrantyDAO;
import app.entity.Product;
import app.entity.User;
import app.entity.Warranty;
import app.services.notificationServices.EmailService;
import app.services.notificationServices.WarrantyScheduler;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestClassFactory {
    static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
   static final WarrantyDAO warrantyDAO = new WarrantyDAO(emf);
    static final ProductDAO productDAO = new ProductDAO(emf);
    static final UserDAO userDAO = new UserDAO(emf);

    static final EmailService emailService = new EmailService(System.getenv("SENDGRID_API_KEY"));
    static final WarrantyScheduler scheduler = new WarrantyScheduler(warrantyDAO, emailService);

    public static void testClassWarranty() {
        User testUser = new User();
        testUser.setEmail("danielhalawi22@gmail.com"); // your email
        testUser.setCreatedAt(LocalDateTime.now());
        testUser.setPassword("12345678");
        userDAO.create(testUser);

        Product product = new Product();
        product.setOwner(testUser);
        product.setProductName("Test Product");
        productDAO.create(product);

        Warranty testWarranty = new Warranty();
        testWarranty.setProduct(product);
        testWarranty.setStartDate(LocalDate.now().minusMonths(3));
        testWarranty.setWarrantyMonths(3);
        testWarranty.setEndDate(testWarranty.getStartDate().plusMonths(testWarranty.getWarrantyMonths()));
        warrantyDAO.create(testWarranty);

        scheduler.checkWarranties();

        System.out.println("Test Sent to Email");
    }
}