package app;

import app.config.HibernateConfig;
import app.daos.RetrieveDAO;
import app.dto.LawDataDTO;
import app.services.ApiFetcher;
import app.services.XMLExtractor;
import app.utils.Populator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManagerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;


public class App
{
    public static void initiate() throws IOException, URISyntaxException, InterruptedException, ParserConfigurationException, SAXException {
        String url = "https://www.retsinformation.dk/eli/lta/2021/1853/xml";
        String xml = ApiFetcher.getXml(url);
        LawDataDTO data;
        long start = System.currentTimeMillis();

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

        data = XMLExtractor.extract(xml);
        System.out.println("\n########LAW CONTENT########");
        System.out.println("Title:\n" + data.getTitle());
        System.out.println("Content:\n" + data.getContent());

        long end = System.currentTimeMillis();

        System.out.println("All data fetched in " + (end - start) + " ms");

    }
    private static <T> void print(String title, Set<T> data){
        System.out.println();
        System.out.println(title);
        data.forEach(System.out::println);
    }
}
