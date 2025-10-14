package DatabaseLayer;


import java.sql.*;

public class DatabaseConnection {
    private final String URL = "jdbc:mysql://localhost:3306/clinic-management-system1";
    private final String username = "root";
    private final String password = "";

    private static DatabaseConnection instance;
    private Connection connection;
    public ResultSet resultSet;
    public PreparedStatement preparedStatement;

    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public static DatabaseConnection getSingleInstance() {
        try {
            if (instance == null) {
                instance = new DatabaseConnection();
            } else if (instance.connection.isClosed()) {
                instance = new DatabaseConnection();
            } else {
                return instance;
            }
            return instance;
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    public boolean ExecuteSQL(String sqlQuery) {
        try {
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(sqlQuery);
            return result > 0;
        } catch (SQLException e) {
            System.out.println("SQL execution failed: " + e.getMessage());
            return false;
        }
    }

    public void setPreparedStatement(String sql) {
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println("Failed to prepare statement: " + e.getMessage());
        }
    }

    public ResultSet ExecutePreparedStatement() {
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

    public Connection getConnection() {
        return connection;
    }

    public ResultSet executeSelectQuery(String sqlQ) {
        try {
            Statement st = connection.createStatement();
            return st.executeQuery(sqlQ); // Returns the result set
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
            return null;
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Failed to close connection: " + e.getMessage());
        }
    }

    public ResultSet ExecuteSQLResultSet(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("SQL execution failed: " + e.getMessage());
            return null;
        }
    }
}
