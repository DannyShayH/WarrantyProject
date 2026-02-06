package app;

import app.config.HibernateConfig;
//import app.controllers.AdminController;
//import app.controllers.UserController;
import app.config.ThymeleafConfig;
import app.daos.UserDAO;
import app.entity.User;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;

public class App
{
    private static EntityManagerFactory emf;

    public static void initiate()
    {
        emf = HibernateConfig.getEntityManagerFactory();

        EntityManager em = emf.createEntityManager();
        UserDAO UserDAO = new UserDAO(emf);

            User u = User.builder().email("Shay@gmail.com").password("1234").createdAt(LocalDateTime.now())
                    .build();
            UserDAO.createUser(u);

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
