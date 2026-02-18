package app.services;

import app.daos.UserDAO;
import app.entity.User;

public class UserValidator {

    private UserDAO userDAO;
    private PasswordService passwordService;

    public void validate(String email, String password) {
        User user = userDAO.findByEmail(email);

        if(user == null || passwordService.verify(password, user.getPassword())){
            throw new IllegalArgumentException("Invalid password");
        }
    }
}
