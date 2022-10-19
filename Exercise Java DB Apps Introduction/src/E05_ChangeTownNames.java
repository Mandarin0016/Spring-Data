import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class E05_ChangeTownNames {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        Connection connection = JdbcConnection.getConnection();

        String country = scanner.nextLine();

        PreparedStatement statement = connection.prepareStatement("""
                UPDATE `towns`
                SET `name` = UPPER(`name`)
                WHERE `country` LIKE ?;        
                """);
        statement.setString(1, country);

        if (statement.executeUpdate() == 0) {
            System.out.println("No town names were affected.");
        } else {
            statement = connection.prepareStatement("""
                    SELECT `name`
                    FROM `towns`
                    WHERE `country` LIKE ?;
                    """);
            statement.setString(1, country);

            ResultSet resultSet = statement.executeQuery();

            List<String> townNames = new ArrayList<>();
            while (resultSet.next()) {
                townNames.add(resultSet.getString(1));
            }

            System.out.printf("%d town names were affected.%n", townNames.size());
            System.out.println(townNames);
        }
    }
}
