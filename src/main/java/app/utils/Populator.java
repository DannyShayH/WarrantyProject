package app.utils;

import app.daos.*;
import app.entity.*;
import app.persistence.IDAO;
import app.persistence.IEntity;
import app.services.validationServices.PasswordService;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public class Populator {

    private final EntityManagerFactory emf;
    private final PasswordService passwordService;
    private final IDAO<Product> productDAO;
    private final IDAO<ProductRegistration> productRegistrationDAO;
    private final IDAO<Receipt> receiptDAO;
    private final IDAO<Warranty> warrantyDAO;
    private final IDAO<User> userDAO;
    private final SecurityDAO securityDAO;

    public Populator(EntityManagerFactory emf) {
        this.emf = emf;
        this.passwordService = new PasswordService();
        this.productDAO = new ProductDAO(emf);
        this.securityDAO = new SecurityDAO(emf);
        this.productRegistrationDAO = new ProductRegistrationDAO(emf);
        this.receiptDAO = new ReceiptDAO(emf);
        this.warrantyDAO = new WarrantyDAO(emf);
        this.userDAO = new UserDAO(emf);
    }

    public Map<String, IEntity> populate() {
        LocalDate start = LocalDate.now();

        User user1 = securityDAO.createUser("Shay@gmail.com", "1234");
        User user2 = securityDAO.createUser("Honda@gmail.com", "1234");
        User user3 = securityDAO.createUser("Clad@gmail.com", "1234");


        Product product1 = Product
                .builder()
                .productName("Iphone 18")
                .owner(user1)
                .build();
        Product product2 =  Product
                .builder()
                .productName("Iphone 18")
                .owner(user2)
                .build();
        Product product3 = Product
                .builder()
                .productName("MacBook Air")
                .owner(user1)
                .build();

        Receipt receipt1 = Receipt
                .builder()
                .purchasedAt(start)
                .description("AN IPHONE 17 WOW")
                .price(1234.98)
                .build();
        Receipt receipt2 = Receipt
                .builder()
                .purchasedAt(start)
                .description("AN IPHONE 18 WOW")
                .price(2430.50)
                .build();
        Receipt receipt3 = Receipt
                .builder()
                .purchasedAt(start)
                .description("A MACBOOK AIR WOW")
                .price(3504.43)
                .build();

        Warranty warranty1 = Warranty
                .builder()
                .warrantyMonths(12)
                .startDate(LocalDate.now())
                .build();
        Warranty warranty2 = Warranty
                .builder().warrantyMonths(24)
                .startDate(LocalDate.now())
                .build();
        Warranty warranty3 = Warranty
                .builder().warrantyMonths(36)
                .startDate(LocalDate.now())
                .build();

        warranty1.calculateEndDate();
        warranty2.calculateEndDate();
        warranty3.calculateEndDate();

        product1.setWarranty(warranty1);
        product2.setWarranty(warranty2);
        product3.setWarranty(warranty3);

        product1.setOwner(user1);
        product2.setOwner(user2);
        product3.setOwner(user3);

        warranty1.setProduct(product1);
        warranty2.setProduct(product2);
        warranty3.setProduct(product3);


        productDAO.create(product1);
        productDAO.create(product2);
        productDAO.create(product3);

        ProductRegistration pr1 = ProductRegistration
                .builder().registeredAt(start)
                .purchasedAt(start)
                .owner(user1)
                .product(product1)
                .receipt(receipt1)
                .build();
        ProductRegistration pr2 = ProductRegistration
                .builder().registeredAt(start)
                .purchasedAt(start)
                .owner(user2)
                .product(product2)
                .receipt(receipt2)
                .build();
        ProductRegistration pr3 = ProductRegistration
                .builder().registeredAt(start)
                .purchasedAt(start)
                .owner(user3)
                .product(product3)
                .receipt(receipt3)
                .build();

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

    public void populateAndCreateEntities(){
        Populator populator = new Populator(emf);
        populator.populate().forEach((k, v) -> System.out.println(k + ": " + v));

        RetrieveDAO retrieveDAO = new RetrieveDAO(emf);
        System.out.println();
        System.out.println("\n#####RETRIEVED######");
        print("User's Products:", retrieveDAO.getAllProductsForUsers(3));
        print("User's With Products:", retrieveDAO.getAllUsersForProduct("Iphone 18"));
        print("User's Warranty:", retrieveDAO.getAllWarrantiesForUsers(3));
        print("User's Receipt:", retrieveDAO.getAllReceiptForUsers(2));
        print("User's Product based on Email:", retrieveDAO.getProductsWithUserEmailDomain("Shay@gmail.com"));
        print("Products with specific price", retrieveDAO.getReceiptWithSpecificPrice(3504.43));
        print("Receipts with Price Range:", retrieveDAO.getReceiptWithPriceRange(6000.34));
        System.out.println("\n####################");
    }

    private <T> void print(String title, Set<T> data){
        System.out.println();
        System.out.println(title);
        data.forEach(System.out::println);
    }
}