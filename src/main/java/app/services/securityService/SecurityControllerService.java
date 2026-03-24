package app.services.securityService;

import app.exceptions.ApiException;
import app.services.tokenService.AuthUserDTO;
import app.services.tokenService.JwtTokenService;
import app.utils.Utils;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class SecurityControllerService {
    private final EntityManagerFactory emf;
    private static final Logger logger = LoggerFactory.getLogger(SecurityControllerService.class);

    public SecurityControllerService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public String createToken(AuthUserDTO user) {
        try {
            String ISSUER;
            String TOKEN_EXPIRE_TIME;
            String SECRET_KEY;

            if (System.getenv("DEPLOYED") != null) {
                ISSUER = System.getenv("ISSUER");
                TOKEN_EXPIRE_TIME = System.getenv("TOKEN_EXPIRE_TIME");
                SECRET_KEY = System.getenv("SECRET_KEY");
            } else {
                ISSUER = Utils.getPropertyValue("ISSUER", "config.properties");
                TOKEN_EXPIRE_TIME = Utils.getPropertyValue("TOKEN_EXPIRE_TIME", "config.properties");
                SECRET_KEY = Utils.getPropertyValue("SECRET_KEY", "config.properties");
            }

            long tokenExpireTimeSeconds = Long.parseLong(TOKEN_EXPIRE_TIME);
            JwtTokenService tokenService = new JwtTokenService(SECRET_KEY, ISSUER, tokenExpireTimeSeconds);

            return tokenService.createToken(user);
        } catch (Exception e) {
            logger.error("Could not create token", e);

            throw new ApiException(500, "Could not create token " + e.getMessage());
        }
    }

    public AuthUserDTO verifyToken(String token) {
        boolean deployed = (System.getenv("DEPLOYED") != null);

        String issuer = deployed
                ? System.getenv("ISSUER")
                : Utils.getPropertyValue("ISSUER", "config.properties");

        String secret = deployed
                ? System.getenv("SECRET_KEY")
                : Utils.getPropertyValue("SECRET_KEY", "config.properties");

        String tokenExpireTime = deployed
                ? System.getenv("TOKEN_EXPIRE_TIME")
                : Utils.getPropertyValue("TOKEN_EXPIRE_TIME", "config.properties");

        long tokenExpireTimeSeconds = Long.parseLong(tokenExpireTime);

        try{
            JwtTokenService tokenService = new JwtTokenService(secret, issuer, tokenExpireTimeSeconds);
            return tokenService.parseAndValidate(token);
        } catch(IllegalArgumentException e){
            throw new UnauthorizedResponse("Unauthorized. Could not verify token");
        }
    }

    private static String getToken(Context ctx) {
        String header = ctx.header("Authorization");
        if (header == null || header.isBlank()) {
            throw new UnauthorizedResponse("Authorization header is missing");
        }
        if (!header.startsWith("Bearer ")) {
            throw new UnauthorizedResponse("Authorization header is malformed");
        }
        String token = header.substring(7).trim();
        if (token.isEmpty()) {
            throw new UnauthorizedResponse("Authorization header is malformed");
        }
        return token;
    }
    public AuthUserDTO validateAndGetUserFromToken(Context ctx) {
        String token = getToken(ctx);
        AuthUserDTO verifiedTokenUser = verifyToken(token);
        if (verifiedTokenUser == null) {
            throw new UnauthorizedResponse("Invalid user or token");
        }
        return verifiedTokenUser;
    }
    public boolean isOpenEndpoint(Set<String> allowedRoles) {
        if (allowedRoles.isEmpty())
            return true;

        if (allowedRoles.contains("ANYONE")) {
            return true;
        }
        return false;
    }
    public boolean userHasAllowedRole(AuthUserDTO user, Set<String> allowedRoles) {
        return user.roles().stream()
                .anyMatch(role -> allowedRoles.contains(role.toUpperCase()));
    }
}
