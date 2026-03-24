package app.services.securityService.securityInterface;

import app.entity.Role;
import app.entity.User;
import app.exceptions.ValidationException;


public interface ISecurityDAO {
    User getVerifiedUser(String username, String password) throws ValidationException; // used for login
    User createUser(String username, String password); // used for register
    Role createRole(String role);
    User addUserRole(String username, String role);
}
