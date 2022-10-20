import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class E03_GetMinionsNames {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        Connection connection = JdbcConnection.getConnection();

        int villainId = Integer.parseInt(scanner.nextLine());

        PreparedStatement statementVillainName = connection.prepareStatement("""
                SELECT `name`
                FROM `villains`
                WHERE `id` LIKE ?;
                """);
        statementVillainName.setInt(1, villainId);

        ResultSet set = statementVillainName.executeQuery();

        if (!set.next()) {
            System.out.println("No villain with ID " + villainId + " exists in the database.");
            return;
        } else {
            System.out.println("Villain: " + set.getString(1));
        }

        PreparedStatement statementMinionNames = connection.prepareStatement("""
                SELECT CONCAT((@`cnt` := @`cnt` + 1), '. ')  AS 'rowNumber',
                       CONCAT(`minions`.`name`, ' ', `age`) AS 'minion_information'
                FROM `villains`
                         INNER JOIN `minions_villains` `mv` on `villains`.`id` = `mv`.`villain_id`
                         INNER JOIN `minions` ON `mv`.`minion_id` = `minions`.`id`
                         CROSS JOIN (SELECT @`cnt` := 0) as `c`
                WHERE `villain_id` LIKE ?;
                  """);

        statementMinionNames.setInt(1, villainId);

        set = statementMinionNames.executeQuery();
        while (set.next()) {
            System.out.println(set.getString(1) + set.getString(2));
        }
        connection.close();
    }
}
