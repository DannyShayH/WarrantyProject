package app.services.validationServices;

import app.daos.UserDAO;
import app.entity.User;

public class UserValidator {

    private final UserDAO userDAO;
    private final PasswordService passwordService;

    public UserValidator(UserDAO userDAO, PasswordService passwordService){
        this.userDAO = userDAO;
        this.passwordService = passwordService;
    }
    public void validate(String email, String password) {
        User user = userDAO.findByEmail(email);

        if(user == null || !passwordService.verify(password, user.getPassword())){
            throw new IllegalArgumentException("Invalid password");
        }
    }
}
