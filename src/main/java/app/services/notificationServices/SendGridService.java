package app.services.notificationServices;

import app.config.HibernateConfig;
import app.daos.WarrantyDAO;
import jakarta.persistence.EntityManagerFactory;

public class SendGridService {
    final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final WarrantyDAO warrantyDAO = new WarrantyDAO(emf);
    private static final EmailService emailService = new EmailService(System.getenv("SENDGRID_API_KEY"));
    private static final WarrantyScheduler scheduler = new WarrantyScheduler(warrantyDAO, emailService);

    public static void sendGridService(){
        scheduler.start();
    }
}
