package behavioral.strategy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DatabaseLogger implements LoggerStrategy {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/projet_masi?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";

    public DatabaseLogger() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            conn.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS logs (id INT AUTO_INCREMENT PRIMARY KEY, message TEXT, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)"
            );
        } catch (Exception e) {
            System.err.println("DB init error: " + e.getMessage());
        }
    }

    @Override
    public void log(String message) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO logs (message) VALUES (?)");
            ps.setString(1, message);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("DB log error: " + e.getMessage());
        }
    }
}
