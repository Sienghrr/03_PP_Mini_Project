package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL      = "jdbc:postgresql://localhost:%s/%s"
            .formatted(System.getenv("db_port"),System.getenv("db_name"));
    private static final String USER     = System.getenv("user_name");
    private static final String PASSWORD = System.getenv("password");

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Postgresql JDBC Driver not found: " + e.getMessage());
            }
        }
        return connection;
    }
}
