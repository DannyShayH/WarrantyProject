package app.controllers;


import app.dto.ReceiptDTO;
import app.services.entityServices.ReceiptService;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceiptController {
    private final ReceiptService receiptService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private static final Logger debugLogger = LoggerFactory.getLogger("app");

    public ReceiptController(EntityManagerFactory emf){
        this.receiptService = new ReceiptService(emf);
    }

    public void create(Context ctx){
        logger.info("Creating Receipt");
        debugLogger.debug("Received POST request with body {}", ctx.body());
        ReceiptDTO receiptDTO = ctx.bodyAsClass(ReceiptDTO.class);
        ctx.json(receiptService.create(receiptDTO));
        ctx.status(201);
        logger.info("Receipt Created Successfully");
    }

    public void getById(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        debugLogger.debug("Fetching receipt with id {}", id);
        ReceiptDTO receiptDTO = receiptService.getByID(id);
        ctx.json(receiptDTO);
    }

    public void update(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        debugLogger.debug("Updating receipt with id {}", id);
        ReceiptDTO receiptDTO = ctx.bodyAsClass(ReceiptDTO.class);
        receiptDTO.setId(id);
        ctx.json(receiptService.update(receiptDTO));
    }

    public void delete(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        logger.info("Deleting receipt with id {}", id);
        receiptService.delete(id);
        ctx.status(204);
    }

    public void getAll(Context ctx){
        ctx.json(receiptService.get());
    }
}
