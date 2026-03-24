package app.routes;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.net.ServerSocket;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class RoutesTest {

    private static Javalin app;
    private static EntityManagerFactory emf;
    private static int port;

    @BeforeAll
    static void init() {

        emf = HibernateConfig.getEntityManagerFactory();
        Routes routes = new Routes();

        ApplicationConfig config = new ApplicationConfig(routes);
        port = findFreePort();
        app = config.startServer(port);

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.basePath = "/api";
    }

    @AfterAll
    static void stop() {
        app.stop();
    }

    @Test
    void testGetUsersRequiresAuth() {
        String token = registerAndLogin();

        given()
                .when()
                .get("/user/all")
                .then()
                .statusCode(401);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/user/all")
                .then()
                .statusCode(200);
    }

    @Test
    void testRegisterAndLogin() {
        String email = randomEmail();
        String password = "12345678";

        String userJson = """
    {
      "email" : "%s",
      "password" : "%s"
    }
    """.formatted(email, password);

        given()
                .contentType("application/json")
                .body(userJson)
                .when()
                .post("/security/register")
                .then()
                .statusCode(201)
                .body("msg", equalTo("Login Register Successful"));

        given()
                .contentType("application/json")
                .body(userJson)
                .when()
                .post("/security/login")
                .then()
                .statusCode(200)
                .body("email", equalTo(email))
                .body("token", notNullValue());
    }

    @Test
    void testRegisterEmptyBody() {

        given()
                .contentType("application/json")
                .body("")
                .when()
                .post("/security/register")
                .then()
                .statusCode(400);
    }

    private static String registerAndLogin() {
        String email = randomEmail();
        String password = "12345678";

        String userJson = """
    {
      "email" : "%s",
      "password" : "%s"
    }
    """.formatted(email, password);

        given()
                .contentType("application/json")
                .body(userJson)
                .when()
                .post("/security/register")
                .then()
                .statusCode(201);

        return given()
                .contentType("application/json")
                .body(userJson)
                .when()
                .post("/security/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    private static String randomEmail() {
        return "routes-test-" + UUID.randomUUID() + "@example.com";
    }

    private static int findFreePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        } catch (Exception e) {
            throw new RuntimeException("Could not find a free port for tests", e);
        }
    }
}