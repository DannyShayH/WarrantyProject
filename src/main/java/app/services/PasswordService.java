package app.services;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordService {

    public String hash(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verify(String password, String hash){
        return BCrypt.checkpw(password, hash);
    }
}
