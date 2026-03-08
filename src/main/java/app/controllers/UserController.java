package app.controllers;


import app.dto.UserDTO;
import app.services.entityServices.UserService;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private static final Logger debugLogger = LoggerFactory.getLogger("app");

    public UserController(EntityManagerFactory emf){
        this.userService = new UserService(emf);
    }

    public void create(Context ctx){
        logger.info("Creating User");
        debugLogger.debug("Received POST request with body {}", ctx.body());
        UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);
        ctx.json(userService.create(userDTO));
        ctx.status(201);
        logger.info("Product Created Successfully");
    }

    public void getById(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        debugLogger.debug("Fetching user with id {}", id);
        UserDTO userDTO = userService.getByID(id);
        ctx.json(userDTO);
    }

    public void update(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        debugLogger.debug("Updating user with id {}", id);
        UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);
        userDTO.setId(id);
        ctx.json(userService.update(userDTO));
    }

    public void delete(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        logger.info("Deleting user with id {}", id);
        userService.delete(id);
        ctx.status(204);
    }

    public void getAll(Context ctx){
        ctx.json(userService.get());
    }
}
