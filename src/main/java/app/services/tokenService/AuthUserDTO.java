package app.services.tokenService;


import java.util.Set;

public record AuthUserDTO(String email, Set<String> roles){}
