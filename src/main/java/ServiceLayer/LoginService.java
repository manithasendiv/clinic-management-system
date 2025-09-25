package ServiceLayer;

import DatabaseLayer.DatabaseConnection;
import Models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginService {
    private DatabaseConnection singleConnection;

    public LoginService() {
        singleConnection = DatabaseConnection.getSingleInstance();
    }

    public User validateCredentials(String username, String password) {
        try {
            String query = "SELECT username, password, role, email FROM user WHERE username = ?";
            PreparedStatement stmt = singleConnection.getConnection().prepareStatement(query);
            stmt.setString(1, username);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");

                // Verify password
                if (BCrypt.checkpw(password, hashedPassword)) {
                    // Build User object
                    String uname = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String role = resultSet.getString("role");

                    return new User(uname, null, email, role);
                    // password set to null (or skip) for security
                }
            }

            return null; // Invalid login
        } catch (Exception e) {
            System.out.println("Error in validating credentials: " + e.getMessage());
            return null;
        }
    }


}
