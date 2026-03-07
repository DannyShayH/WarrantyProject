package app.controllers;


import app.dto.ReceiptDTO;
import app.services.entityServices.ReceiptService;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(EntityManagerFactory emf){
        this.receiptService = new ReceiptService(emf);
    }

    public void create(Context ctx){
        ReceiptDTO receiptDTO = ctx.bodyAsClass(ReceiptDTO.class);
        ctx.json(receiptService.create(receiptDTO));
        ctx.status(201);
    }

    public void getById(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        ReceiptDTO receiptDTO = receiptService.getByID(id);
        ctx.json(receiptDTO);
    }

    public void update(Context ctx){
        ReceiptDTO receiptDTO = ctx.bodyAsClass(ReceiptDTO.class);
        ctx.json(receiptService.update(receiptDTO));
    }

    public void delete(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        receiptService.delete(id);
        ctx.status(204);
    }

    public void getAll(Context ctx){
        ctx.json(receiptService.get());
    }
}
