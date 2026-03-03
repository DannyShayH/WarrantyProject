package app.services.notificationServices;

import app.daos.WarrantyDAO;
import app.services.persistenceServices.EntityManagerFactoryService;

public class SendGridService {

    private static final WarrantyDAO warrantyDAO = new WarrantyDAO(EntityManagerFactoryService.getEntityManagerFactory());
    private static final EmailService emailService = new EmailService(System.getenv("SENDGRID_API_KEY"));
    private static final WarrantyScheduler scheduler = new WarrantyScheduler(warrantyDAO, emailService);

    public static void sendGridService(){
        scheduler.start();
    }
}
