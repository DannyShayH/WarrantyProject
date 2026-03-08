package app.controllers;

import app.dto.ProductDTO;
import app.services.entityServices.ProductService;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductController {
    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private static final Logger debugLogger = LoggerFactory.getLogger("app");

    public ProductController(EntityManagerFactory emf){
        this.productService = new ProductService(emf);
    }

    public void create(Context ctx){
        logger.info("Creating Product");
        debugLogger.debug("Received POST request with body {}", ctx.body());
        ProductDTO productDTO = ctx.bodyAsClass(ProductDTO.class);
        ctx.json(productService.create(productDTO));
        ctx.status(201);
        logger.info("Product Created Successfully");
    }

    public void getById(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        debugLogger.debug("Fetching product with id {}", id);
        ProductDTO productDTO = productService.getByID(id);
        ctx.json(productDTO);
    }

    public void update(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        debugLogger.debug("Updating product with id {}", id);
        ProductDTO productDTO = ctx.bodyAsClass(ProductDTO.class);
        productDTO.setId(id);
        ctx.json(productService.update(productDTO));
    }

    public void delete(Context ctx){
        long id = Long.parseLong(ctx.pathParam("id"));
        logger.info("Deleting product with id {}", id);
        productService.delete(id);
        ctx.status(204);
    }

    public void getAll(Context ctx){
        ctx.json(productService.get());
    }
}
