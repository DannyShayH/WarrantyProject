package app.controllers;

import app.daos.SecurityDAO;
import app.dto.UserDTO;
import app.entity.User;
import app.exceptions.ValidationException;
import app.services.securityService.SecurityControllerService;
import app.services.securityService.securityInterface.ISecurityController;
import app.services.tokenService.AuthUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class SecurityController implements ISecurityController {
    private final EntityManagerFactory emf;
    private SecurityDAO dao;
    private ObjectMapper mapper;
    private SecurityControllerService securityService;
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);
    private static final Logger debugLogger = LoggerFactory.getLogger("app");

    public SecurityController(EntityManagerFactory emf) {
        this.emf = emf;
        this.dao = new SecurityDAO(emf);
        this.mapper = new ObjectMapper();
        this.securityService = new SecurityControllerService(emf);
    }

    @Override
    public void login(Context ctx) {
        UserDTO user = ctx.bodyAsClass(UserDTO.class);
        try {
            User userEntity =  dao.getVerifiedUser(user.getEmail(), user.getPassword());
            String token = securityService.createToken(new AuthUserDTO(userEntity.getEmail(), userEntity.getRolesAsStrings()));
            ObjectNode node = mapper.createObjectNode();

            ctx.status(200).json(node
                    .put("token", token)
                    .put("email", userEntity.getEmail()));
        } catch (ValidationException ex) {
            throw new UnauthorizedResponse(ex.getMessage());
        }
    }

    @Override
    public void register(Context ctx) {
        logger.info("Creating User");
        debugLogger.debug("Received POST request with body {}", ctx.body());
        UserDTO user = ctx.bodyAsClass(UserDTO.class);
        dao.createUser(user.getEmail(), user.getPassword());
        ObjectNode node = mapper.createObjectNode();
        node.put("msg", "Login Register Successful");
        ctx.json(node).status(201);
        logger.info("Product Created Successfully");
    }

    @Override
    public void authenticate(Context ctx) {

        if (ctx.method().toString().equals("OPTIONS")) {
            ctx.status(200);
            return;
        }
        Set<String> allowedRoles = ctx.routeRoles()
                .stream()
                .map(role -> role.toString()
                .toUpperCase())
                .collect(Collectors.toSet());

        if (securityService.isOpenEndpoint(allowedRoles))
            return;

        AuthUserDTO verifiedTokenUser = securityService.validateAndGetUserFromToken(ctx);
        ctx.attribute("user", verifiedTokenUser);
    }

    @Override
    public void authorize(Context ctx) {
        Set<String> allowedRoles = ctx.routeRoles()
                .stream()
                .map(role -> role.toString()
                .toUpperCase())
                .collect(Collectors.toSet());

        if (securityService.isOpenEndpoint(allowedRoles))
            return;

        AuthUserDTO user = ctx.attribute("user");
        if (user == null) {
            throw new ForbiddenResponse("No user was added from the token");
        }

        if (!securityService.userHasAllowedRole(user, allowedRoles))
            throw new ForbiddenResponse("User was not authorized with roles: " +
                    user.roles() + ". Needed roles are: " + allowedRoles);
    }

    public void healthCheck(Context ctx) {
        ctx.status(200).json("{\"msg\": \"API is up and running\"}");
    }
}
