package app;

import app.services.notificationServices.SendGridService;
import app.services.persistenceServices.EntityManagerFactoryService;
import app.services.testClassService.TestClassFactory;
import app.services.xmlServices.XmlService;
import app.utils.Populator;

public class App {
     static void initiate(){
        XmlService xmlService = new XmlService();
        Populator populater = new Populator(EntityManagerFactoryService.getEntityManagerFactory());

        long start = System.currentTimeMillis();

        populater.populateAndCreateEntities();

        long end = System.currentTimeMillis();

         xmlService.extractAndPrintXML();

        TestClassFactory.testClassWarranty();

        SendGridService.sendGridService();

        System.out.println("All data fetched in " + (end - start) + " ms");
    }
}