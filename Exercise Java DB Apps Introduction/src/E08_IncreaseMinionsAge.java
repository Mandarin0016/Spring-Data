import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class E08_IncreaseMinionsAge {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        Connection connection = JdbcConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement("""
                UPDATE `minions`
                SET `age`  = `age` + 1,
                    `name` = LOWER(`name`)
                WHERE `id` = ?;
                """);
        int[] ids = Arrays.stream(scanner.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        for (int id : ids) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }

        statement = connection.prepareStatement("""
                SELECT CONCAT_WS(' ', `name`, `age`)
                FROM `minions`;
                """);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }

        connection.close();
    }
}
