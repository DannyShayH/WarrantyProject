package app.controllers;

import app.dto.ProductDTO;
import app.services.entityServices.ProductService;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

public class ProductController {
    private final ProductService productService;

    public ProductController(EntityManagerFactory emf){
        this.productService = new ProductService(emf);
    }

    public void create(Context ctx){
        ProductDTO productDTO = ctx.bodyAsClass(ProductDTO.class);
        ctx.json(productService.create(productDTO));
        ctx.status(201);
    }

    public void getById(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        ProductDTO productDTO = productService.getByID(id);
        ctx.json(productDTO);
    }

    public void update(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        ProductDTO productDTO = ctx.bodyAsClass(ProductDTO.class);
        productDTO.setId(id);
        ctx.json(productService.update(productDTO));
    }

    public void delete(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        productService.delete(id);
        ctx.status(204);
    }

    public void getAll(Context ctx){
        ctx.json(productService.get());
    }
}
