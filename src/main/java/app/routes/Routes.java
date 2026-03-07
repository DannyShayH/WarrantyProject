package app.routes;

import app.controllers.*;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.put;

public class Routes {
    private final ProductController productController;
    private final WarrantyController warrantyController;
    private final UserController userController;
    private final ReceiptController receiptController;
    private final ProductRegistrationController productRegistrationController;

    public Routes(ProductController productController,
                  WarrantyController warrantyController,
                  UserController userController,
                  ReceiptController receiptController,
                  ProductRegistrationController productRegistrationController) {

        this.productController = productController;
        this.warrantyController = warrantyController;
        this.userController = userController;
        this.receiptController = receiptController;
        this.productRegistrationController = productRegistrationController;
    }

    public EndpointGroup getRoutes() {
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

            path("productRegistration", () -> {
                post("/", productRegistrationController::create);
                get("/all", productRegistrationController::getAll);
                get("/{id}", productRegistrationController::getById);
                put("/{id}", productRegistrationController::update);
                delete("/{id}", productRegistrationController::delete);
            });
        };
    }
}
