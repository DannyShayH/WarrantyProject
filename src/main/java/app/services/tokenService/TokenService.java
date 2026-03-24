package app.services.tokenService;

public interface TokenService {

    String createToken(AuthUserDTO user);
    AuthUserDTO parseAndValidate(String token);
}
