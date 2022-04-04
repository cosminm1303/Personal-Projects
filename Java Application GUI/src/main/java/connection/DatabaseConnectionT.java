package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionT {
    private static DatabaseConnectionT instance;
    private final String url = "jdbc:mysql://localhost:3306/teachers";
    private final String username = "root";
    private final String password = "password";
    private Connection connection;

    private DatabaseConnectionT() {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public static DatabaseConnectionT getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnectionT();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnectionT();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
