package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionC {
    private static DatabaseConnectionC instance;
    private final String url = "jdbc:mysql://localhost:3306/courses";
    private final String username = "root";
    private final String password = "password";
    private Connection connection;

    private DatabaseConnectionC() {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public static DatabaseConnectionC getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnectionC();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnectionC();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
