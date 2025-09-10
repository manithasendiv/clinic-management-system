package ServiceLayer;

import DatabaseLayer.DatabaseConnection;
import Models.User;
import org.mindrot.jbcrypt.BCrypt;

public class RegistrationService {
    private DatabaseConnection singleConnection;

    public RegistrationService() {
        singleConnection = DatabaseConnection.getSingleInstance();
    }

    public boolean registerUser(User user) {
        try{
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
            String query = "INSERT INTO user(username, password, email, role) VALUES('"+user.getUsername()+ "','"+ hashedPassword +"','" +user.getEmail()+ "','"+user.getRole()+"')";
            return singleConnection.ExecuteSQL(query);
        }
        catch(Exception e) {
            System.out.println("Error in adding user" + e.getMessage());
            return false;
        }
    }
}
