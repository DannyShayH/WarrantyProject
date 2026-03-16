package app.routes;

import app.config.HibernateConfig;
import app.controllers.*;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.put;

public class Routes {
    final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public EndpointGroup getRoutes() {
        ProductController productController = new ProductController(emf);
        WarrantyController warrantyController = new WarrantyController(emf);
        UserController userController = new UserController(emf);
        ReceiptController receiptController = new ReceiptController(emf);
       ProductRegistrationController productRegistrationController = new ProductRegistrationController(emf);

        return () -> {
            get("/", ctx -> ctx.result("Hello World"));

            path("product", () -> {
                post("/", productController::create);
                get("/all", productController::getAll);
                get("/{id}", productController::getById);
                put("/{id}", productController::update);
                delete("/{id}", productController::delete);
            });

            path("warranty", () -> {
                post("/", warrantyController::create);
                get("/all", warrantyController::getAll);
                get("/{id}", warrantyController::getById);
                put("/{id}", warrantyController::update);
                delete("/{id}", warrantyController::delete);
            });

            path("user", () -> {
                post("/", userController::create);
                get("/all", userController::getAll);
                get("/{id}", userController::getById);
                put("/{id}", userController::update);
                delete("/{id}", userController::delete);
            });

            path("receipt", () -> {
                post("/", receiptController::create);
                get("/all", receiptController::getAll);
                get("/{id}", receiptController::getById);
                put("/{id}", receiptController::update);
                delete("/{id}", receiptController::delete);
            });

            path("product-registration", () -> {
                post("/", productRegistrationController::create);
                get("/all", productRegistrationController::getAll);
                get("/{id}", productRegistrationController::getById);
                put("/{id}", productRegistrationController::update);
                delete("/{id}", productRegistrationController::delete);
            });
        };
    }
}
