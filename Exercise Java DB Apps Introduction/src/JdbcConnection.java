import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnection {
    private static final Properties properties = new Properties();
    private static final String URL = "jdbc:mysql://localhost:3306/minions_db";
    private static final String user = "root";
    private static final String password = "";

    public static Connection getConnection() throws SQLException {
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        return DriverManager
                .getConnection(URL, properties);
    }
}