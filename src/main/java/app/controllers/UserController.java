package app.controllers;


import app.dto.UserDTO;
import app.services.entityServices.UserService;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

public class UserController {
    private final UserService userService;

    public UserController(EntityManagerFactory emf){
        this.userService = new UserService(emf);
    }

    public void create(Context ctx){
        UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);
        ctx.json(userService.create(userDTO));
        ctx.status(201);
    }

    public void getById(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        UserDTO userDTO = userService.getByID(id);
        ctx.json(userDTO);
    }

    public void update(Context ctx){
        UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);
        ctx.json(userService.update(userDTO));
    }

    public void delete(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        userService.delete(id);
        ctx.status(204);
    }

    public void getAll(Context ctx){
        ctx.json(userService.get());
    }
}
