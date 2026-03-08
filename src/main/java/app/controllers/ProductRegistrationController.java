package app.controllers;


import app.dto.ProductRegistrationDTO;
import app.services.entityServices.ProductRegistrationService;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductRegistrationController {
    private final ProductRegistrationService productRegistrationService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private static final Logger debugLogger = LoggerFactory.getLogger("app");

    public ProductRegistrationController(EntityManagerFactory emf){
        this.productRegistrationService = new ProductRegistrationService(emf);
    }

    public void create(Context ctx){
        logger.info("Creating ProductRegistration");
        debugLogger.debug("Received POST request with body {}", ctx.body());
        ProductRegistrationDTO productRegistrationDTO = ctx.bodyAsClass(ProductRegistrationDTO.class);
        ctx.json(productRegistrationService.create(productRegistrationDTO));
        ctx.status(201);
        logger.info("ProductRegistration Created Successfully");
    }

    public void getById(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        debugLogger.debug("Fetching productRegistration with id {}", id);
        ProductRegistrationDTO productRegistrationDTO = productRegistrationService.getByID(id);
        ctx.json(productRegistrationDTO);
    }

    public void update(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        debugLogger.debug("Updating productRegistration with id {}", id);
        ProductRegistrationDTO productRegistrationDTO = ctx.bodyAsClass(ProductRegistrationDTO.class);
        productRegistrationDTO.setId(id);
        ctx.json(productRegistrationService.update(productRegistrationDTO));
    }

    public void delete(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        logger.info("Deleting productRegistration with id {}", id);
        productRegistrationService.delete(id);
        ctx.status(204);
    }

    public void getAll(Context ctx){
        ctx.json(productRegistrationService.get());
    }
}
