import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class E06_RemoveVillain {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        Connection connection = JdbcConnection.getConnection();

        int villainId = Integer.parseInt(scanner.nextLine());
        PreparedStatement statement = connection.prepareStatement("""
                SELECT `name`
                FROM `villains`
                WHERE `id` = ?;
                """);
        statement.setInt(1, villainId);
        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            System.out.println("No such villain was found");
            connection.close();
            return;
        }

        String villainName = resultSet.getString(1);

        statement = connection.prepareStatement("""
                DELETE `minions_villains`
                FROM `minions_villains`
                WHERE `villain_id` = ?;
                """);
        statement.setInt(1, villainId);
        int deletedRows = statement.executeUpdate();

        statement = connection.prepareStatement("""
                DELETE `villains`
                FROM `villains`
                WHERE `id` LIKE ?;
                """);
        statement.setInt(1, villainId);
        statement.executeUpdate();

        System.out.println(villainName + " was deleted");
        System.out.println(deletedRows + " minions released");

        connection.close();
    }
}
