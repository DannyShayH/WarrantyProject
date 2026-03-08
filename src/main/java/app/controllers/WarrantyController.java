package app.controllers;


import app.dto.WarrantyDTO;
import app.services.entityServices.WarrantyService;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WarrantyController {
    private final WarrantyService warrantyService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private static final Logger debugLogger = LoggerFactory.getLogger("app");

    public WarrantyController(EntityManagerFactory emf){
        this.warrantyService = new WarrantyService(emf);
    }

    public void create(Context ctx){
        logger.info("Creating Warranty");
        debugLogger.debug("Received POST request with body {}", ctx.body());
        WarrantyDTO warrantyDTO = ctx.bodyAsClass(WarrantyDTO.class);
        ctx.json(warrantyService.create(warrantyDTO));
        ctx.status(201);
        logger.info("Warranty Created Successfully");
    }

    public void getById(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        debugLogger.debug("Fetching warranty with id {}", id);
        WarrantyDTO warrantyDTO = warrantyService.getByID(id);
        ctx.json(warrantyDTO);
    }

    public void update(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        debugLogger.debug("Updating warranty with id {}", id);
        WarrantyDTO warrantyDTO = ctx.bodyAsClass(WarrantyDTO.class);
        warrantyDTO.setId(id);
        ctx.json(warrantyService.update(warrantyDTO));
    }

    public void delete(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        logger.info("Deleting warranty with id {}", id);
        warrantyService.delete(id);
        ctx.status(204);
    }

    public void getAll(Context ctx){
        ctx.json(warrantyService.get());
    }
}
