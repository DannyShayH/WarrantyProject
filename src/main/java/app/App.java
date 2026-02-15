package app;

import app.config.HibernateConfig;
//import app.controllers.AdminController;
//import app.controllers.UserController;
import app.config.ThymeleafConfig;
import app.utils.Populator;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import jakarta.persistence.EntityManagerFactory;


public class App
{

    public static void initiate()
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        Populator populator = new Populator(emf);
        populator.populate().forEach((k, v) -> System.out.println(k + ": " + v));

        Javalin app = Javalin.create(config ->
        {
            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
            config.staticFiles.add("/templates");
        }).start(7071);

        //UserController.addRoutes(app);
        //AdminController.addRoutes(app);
    }
}
