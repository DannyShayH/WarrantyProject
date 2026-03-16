package app;

import app.config.HibernateConfig;
import app.controllers.*;
import app.routes.Routes;
import app.services.notificationServices.SendGridService;
import app.services.testClassService.TestClassFactory;
import app.services.xmlServices.XmlService;
import app.utils.Populator;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;

public class App {
     static void initiate(){
        final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        XmlService xmlService = new XmlService(emf);
        Populator populater = new Populator(emf);
        Routes routes = new Routes();

        long start = System.currentTimeMillis();

        populater.populateAndCreateEntities();

        xmlService.extractAndPersistXML();

        TestClassFactory.testClassWarranty();

        SendGridService.sendGridService();

        var app = Javalin.create(config -> {
           config.routes.apiBuilder(routes.getRoutes());
           config.bundledPlugins.enableRouteOverview("routes");
           config.routes.exception(RuntimeException.class, (e, ctx) -> {
              ctx.status(400).json(e.getMessage());
           });
        }).start(7070);

        long end = System.currentTimeMillis();
        System.out.println("All data fetched in " + (end - start) + " ms");
    }
}
