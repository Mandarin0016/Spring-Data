import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Properties properties = new Properties();
    private static final String URL = "jdbc:mysql://localhost:3306/diablo";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) throws SQLException {
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection = DriverManager.getConnection(URL, properties);

        String username = scanner.nextLine();

        PreparedStatement statement = connection.prepareStatement("""
                SELECT CONCAT('User: ', user_name) AS 'username', CONCAT(first_name, ' ', last_name, ' has played ', COUNT(game_id), ' games.') AS 'played_games'
                FROM users
                         INNER JOIN users_games `ug` on users.id = `ug`.user_id
                WHERE user_name LIKE ?
                GROUP BY user_id;
                """);
        statement.setString(1, username);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            System.out.println(resultSet.getString("username") + System.lineSeparator() + resultSet.getString("played_games"));
        } else {
            System.out.println("No such user exists");
        }

        connection.close();
    }
}