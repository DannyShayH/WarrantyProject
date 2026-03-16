package app.routes;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

class RoutesTest {

    private static Javalin app;
    private static EntityManagerFactory emf;

    @BeforeAll
    static void init() {

        emf = HibernateConfig.getEntityManagerFactory();
        Routes routes = new Routes();

        ApplicationConfig config = new ApplicationConfig(routes);
        app = config.startServer(7070);

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7070;
        RestAssured.basePath = "/api";
    }

    @AfterAll
    static void stop() {
        app.stop();
    }

    @Test
    void testGetUsers() {
        given()
                .when()
                .get("/user/all")
                .then()
                .statusCode(200);
    }

    @Test
    void testCreateUser() {

        String userJson = """
    {
      "email" : "TestMail@gmail.com",
      "password" : "12345678",
      "createdAt" : "2026-02-05T13:20:00"
    }
    """;

        given()
                .contentType("application/json")
                .body(userJson)
                .when()
                .post("/user")
                .then()
                .statusCode(201)
                .body("email", equalTo("TestMail@gmail.com"))
                .body("password", equalTo("12345678"))
                .body("createdAt", equalTo("2026-02-05T13:20:00"));
    }

    @Test
    void testCreateUserEmptyBody() {

        given()
                .contentType("application/json")
                .body("")
                .when()
                .post("/user")
                .then()
                .statusCode(400);
    }
}