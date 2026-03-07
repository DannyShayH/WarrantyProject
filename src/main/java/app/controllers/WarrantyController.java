package app.controllers;


import app.dto.WarrantyDTO;
import app.services.entityServices.WarrantyService;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

public class WarrantyController {
    private final WarrantyService warrantyService;

    public WarrantyController(EntityManagerFactory emf){
        this.warrantyService = new WarrantyService(emf);
    }

    public void create(Context ctx){
        WarrantyDTO warrantyDTO = ctx.bodyAsClass(WarrantyDTO.class);
        ctx.json(warrantyService.create(warrantyDTO));
        ctx.status(201);
    }

    public void getById(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        WarrantyDTO warrantyDTO = warrantyService.getByID(id);
        ctx.json(warrantyDTO);
    }

    public void update(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        WarrantyDTO warrantyDTO = ctx.bodyAsClass(WarrantyDTO.class);
        warrantyDTO.setId(id);
        ctx.json(warrantyService.update(warrantyDTO));
    }

    public void delete(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        warrantyService.delete(id);
        ctx.status(204);
    }

    public void getAll(Context ctx){
        ctx.json(warrantyService.get());
    }
}
