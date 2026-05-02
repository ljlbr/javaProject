package by.psu.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    private static final Properties properties = new Properties();

    static {
        try (InputStream is = ConnectionManager.class.getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (is == null) {
                throw new RuntimeException("db.properties not found");
            }

            properties.load(is);

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load DB config", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password")
        );
    }
}