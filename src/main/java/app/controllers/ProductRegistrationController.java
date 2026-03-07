package app.controllers;


import app.dto.ProductRegistrationDTO;
import app.services.entityServices.ProductRegistrationService;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

public class ProductRegistrationController {
    private final ProductRegistrationService productRegistrationService;

    public ProductRegistrationController(EntityManagerFactory emf){
        this.productRegistrationService = new ProductRegistrationService(emf);
    }

    public void create(Context ctx){
        ProductRegistrationDTO productRegistrationDTO = ctx.bodyAsClass(ProductRegistrationDTO.class);
        ctx.json(productRegistrationService.create(productRegistrationDTO));
        ctx.status(201);
    }

    public void getById(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        ProductRegistrationDTO productRegistrationDTO = productRegistrationService.getByID(id);
        ctx.json(productRegistrationDTO);
    }

    public void update(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        ProductRegistrationDTO productRegistrationDTO = ctx.bodyAsClass(ProductRegistrationDTO.class);
        productRegistrationDTO.setId(id);
        ctx.json(productRegistrationService.update(productRegistrationDTO));
    }

    public void delete(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        productRegistrationService.delete(id);
        ctx.status(204);
    }

    public void getAll(Context ctx){
        ctx.json(productRegistrationService.get());
    }
}
