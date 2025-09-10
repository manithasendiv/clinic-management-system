package ServiceLayer;

import DatabaseLayer.DatabaseConnection;

public class LoginService {
    private DatabaseConnection singleConnection;

    public LoginService() {
        singleConnection = DatabaseConnection.getSingleInstance();
    }

    public boolean validateCredentials(String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            return singleConnection.isValidUser(query);
        } catch (Exception e) {
            System.out.println("Error in validating credentials: " + e.getMessage());
            return false;
        }
    }
}
