package DatabaseLayer;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/clinic-management-system";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static DatabaseConnection instance;
    private Connection connection;
    public ResultSet resultSet;
    public PreparedStatement preparedStatement;

    // Private constructor for Singleton
    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    // Singleton instance getter
    public static DatabaseConnection getSingleInstance() {
        try {
            if (instance == null || instance.connection.isClosed()) {
                instance = new DatabaseConnection();
            }
            return instance;
        } catch (SQLException e) {
            System.out.println("Connection check failed: " + e.getMessage());
            return null;
        }
    }

    // Execute SQL (Insert, Update, Delete)
    public boolean executeSQL(String sqlQuery) {
        try (Statement statement = connection.createStatement()) {
            int result = statement.executeUpdate(sqlQuery);
            return result > 0;
        } catch (SQLException e) {
            System.out.println("SQL execution failed: " + e.getMessage());
            return false;
        }
    }

    // Prepare statement
    public void setPreparedStatement(String sql) {
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println("Failed to prepare statement: " + e.getMessage());
        }
    }

    // Execute prepared statement (Select)
    public ResultSet executePreparedStatement() {
        try {
            if (preparedStatement != null) {
                resultSet = preparedStatement.executeQuery();
                return resultSet;
            } else {
                System.out.println("Prepared statement is not set.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute prepared statement: " + e.getMessage());
            return null;
        }
    }

    // Get raw connection if needed
    public Connection getConnection() {
        return connection;
    }

    // Close connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Failed to close connection: " + e.getMessage());
        }
    }
}
