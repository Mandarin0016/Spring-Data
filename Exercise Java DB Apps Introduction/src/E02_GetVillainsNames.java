import java.sql.*;

public class E02_GetVillainsNames {

    public static void main(String[] args) throws SQLException {
        Connection connection = JdbcConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement("""
                SELECT `name`, COUNT(DISTINCT `minion_id`) AS 'minions_count'
                FROM `villains`
                         INNER JOIN `minions_villains` `mv` on `villains`.`id` = `mv`.`villain_id`
                GROUP BY `name`
                HAVING `minions_count` > 15
                ORDER BY `minions_count` DESC;
                """);

        ResultSet set = statement.executeQuery();

        while (set.next()) {
            System.out.println(set.getString(1) + " " + set.getString(2));
        }

        connection.close();
    }


    private static void addMinion() throws SQLException {

    }
}