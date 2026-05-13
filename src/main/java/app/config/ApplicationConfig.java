package app.config;

import app.routes.Routes;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.json.JavalinJackson;
import io.javalin.validation.ValidationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ApplicationConfig {

    private final Routes routes;

    public ApplicationConfig(Routes routes) {
        this.routes = routes;
    }

    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

    public void configuration(JavalinConfig config){
        config.bundledPlugins.enableRouteOverview("/routes");
        config.bundledPlugins.enableCors(cors -> {
            cors.addRule(rule -> {
                rule.anyHost();
            });
        });
        config.router.contextPath = "/api";
        config.jsonMapper(new JavalinJackson().updateMapper(objectMapper -> {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }));

        config.routes.apiBuilder(routes.getRoutes());
        config.routes.exception(ValidationException.class, (e, ctx) -> {

            logger.error("Unhandled validation occurred", e);

            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(Map.of(
                    "message", "Path parameter must be a number",
                    "path", ctx.path()
            ));
        });
        config.routes.exception(MismatchedInputException.class, (e, ctx) -> {

            logger.error("Unhandled mismatchedInput occurred", e);

            ctx.status(HttpStatus.BAD_REQUEST).json(Map.of("message", "invalid or empty request body",
                    "status", 400, "path", ctx.path()));
        });

        config.routes.exception(IllegalAccessException.class, (e, ctx) -> {

            logger.error("Unhandled illegalAccess occurred", e);

            ctx.status(HttpStatus.BAD_REQUEST).json(Map.of("message", "Invalid token"));
        });

        config.routes.exception(EntityNotFoundException.class, (e, ctx) -> {

            logger.error("Unhandled entityNotFound occurred", e);

            ctx.status(HttpStatus.NOT_FOUND).json(Map.of("message", e.getMessage()));
        });

        config.routes.exception(PersistenceException.class, (e, ctx) -> {

            logger.error("Unhandled persistence occurred", e);

            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(Map.of("message", "Database error"));
        });
    }

    public Javalin startServer(int port) {
        var app = Javalin.create(this::configuration);
        app.start(port);
        return app;
    }

    public void stopServer(Javalin app) {
        app.stop();
    }
}
