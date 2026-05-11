package app.routes;

import app.config.HibernateConfig;
import app.controllers.*;
import app.enums.RouteRole;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.put;

public class Routes {
    private final EntityManagerFactory emf;

    public Routes() {
        this(HibernateConfig.getEntityManagerFactory());
    }

    public Routes(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EndpointGroup getRoutes() {
        ProductController productController = new ProductController(emf);
        WarrantyController warrantyController = new WarrantyController(emf);
        UserController userController = new UserController(emf);
        ReceiptController receiptController = new ReceiptController(emf);
        ProductRegistrationController productRegistrationController = new ProductRegistrationController(emf);
        SecurityController securityController = new SecurityController(emf);

        return () -> {
            beforeMatched(securityController::authenticate);
            beforeMatched(securityController::authorize);

            get("/", ctx -> ctx.result("Hello World"));

            path("product", () -> {
                post("/", productController::create, RouteRole.USER);
                get("/all", productController::getAll, RouteRole.USER);
                get("/user/{id}", productController::getAllByUser, RouteRole.USER);
                get("/{id}", productController::getById, RouteRole.USER);
                put("/{id}", productController::update, RouteRole.USER);
                delete("/{id}", productController::delete, RouteRole.USER);
            });

            path("warranty", () -> {
                post("/", warrantyController::create, RouteRole.USER);
                get("/all", warrantyController::getAll, RouteRole.USER);
                get("/{id}", warrantyController::getById, RouteRole.USER);
                put("/{id}", warrantyController::update, RouteRole.USER);
                delete("/{id}", warrantyController::delete, RouteRole.USER);
            });

            path("user", () -> {
                get("/all", userController::getAll, RouteRole.USER, RouteRole.ADMIN);
                get("/{id}", userController::getById, RouteRole.USER, RouteRole.ADMIN);
                put("/{id}", userController::update, RouteRole.USER, RouteRole.ADMIN);
                delete("/{id}", userController::delete, RouteRole.USER, RouteRole.ADMIN);
            });

            path("receipt", () -> {
                post("/", receiptController::create, RouteRole.USER);
                get("/all", receiptController::getAll, RouteRole.USER);
                get("/{id}", receiptController::getById, RouteRole.USER);
                put("/{id}", receiptController::update, RouteRole.USER);
                delete("/{id}", receiptController::delete, RouteRole.USER);
            });

            path("product-registration", () -> {
                post("/", productRegistrationController::create, RouteRole.USER);
                get("/all", productRegistrationController::getAll, RouteRole.USER);
                get("/{id}", productRegistrationController::getById, RouteRole.USER);
                put("/{id}", productRegistrationController::update, RouteRole.USER);
                delete("/{id}", productRegistrationController::delete, RouteRole.USER);
            });

            path("security", () -> {
               get("/healthcheck", securityController::healthCheck, RouteRole.ANYONE);
               post("/login", securityController::login, RouteRole.ANYONE);
               post("/register", securityController::register, RouteRole.ANYONE);
            });
        };
    }
}
