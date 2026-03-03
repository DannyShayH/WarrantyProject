package app.services.notificationServices;

import app.daos.WarrantyDAO;
import app.entity.Product;
import app.entity.User;
import app.entity.Warranty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.mockito.Mockito.*;

class WarrantySchedulerTest {

    private WarrantyDAO warrantyDAO;
    private EmailService emailService;
    private WarrantyScheduler scheduler;

    @BeforeEach
    void setup() {

        warrantyDAO = mock(WarrantyDAO.class);
        emailService = mock(EmailService.class);

        User testUser = new User();
        testUser.setEmail("danielhalawi22@gmail.com");

        Product product = new Product();
        product.setProductName("Test Product");
        product.setOwner(testUser);

        Warranty warranty = new Warranty();
        warranty.setProduct(product);
        warranty.setStartDate(LocalDate.now());
        warranty.setWarrantyMonths(3);

        when(warrantyDAO.get()).thenReturn(Set.of(warranty));

        scheduler = new WarrantyScheduler(warrantyDAO, emailService);

    }

    @Test
    void testWarrantyNotificationIsSent(){
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("danielhalawi22@gmail.com");

        Product product = mock(Product.class);
        when(product.getProductName()).thenReturn("Test Product");
        when(product.getOwner()).thenReturn(user);

        Warranty warranty = mock(Warranty.class);
        when(warranty.calculateEndDate()).thenReturn(LocalDate.now().plusDays(90));
        when(warranty.getProduct()).thenReturn(product);
        when(warranty.isNotified90Days()).thenReturn(false);

        when(warrantyDAO.get()).thenReturn(Set.of(warranty));

        // Call method manually
        scheduler.checkWarranties();

        // Verify email sent
        verify(emailService, atLeastOnce())
                .sendWarrantyReminder(eq("danielhalawi22@gmail.com"),
                        eq("Test Product"),
                        eq(90L));
    }
    }