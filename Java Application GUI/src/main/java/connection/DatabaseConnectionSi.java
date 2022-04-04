package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionSi {
    private static DatabaseConnectionSi instance;
    private final String url = "jdbc:mysql://localhost:3306/situation";
    private final String username = "root";
    private final String password = "password";
    private Connection connection;

    private DatabaseConnectionSi() {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public static DatabaseConnectionSi getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnectionSi();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnectionSi();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
