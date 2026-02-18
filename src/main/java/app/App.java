package app;

import app.config.HibernateConfig;
import app.daos.RetrieveDAO;
import app.utils.Populator;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;


public class App
{

    public static void initiate()
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
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
    private static <T> void print(String title, Set<T> data){
        System.out.println();
        System.out.println(title);
        data.forEach(System.out::println);
    }
}
