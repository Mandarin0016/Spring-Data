import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class E04_AddMinion {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        Connection connection = JdbcConnection.getConnection();

        System.out.print("Minion: ");
        String[] minionInformation = scanner.nextLine().split("\\s+");
        System.out.print("Villain: ");
        String villainName = scanner.nextLine();

        addTownIfMissing(connection, minionInformation);
        addVillainIfMissing(connection, villainName);
        saveMinion(connection, minionInformation);
        addMinionToVillain(connection, minionInformation, villainName);

        System.out.printf("Successfully added %s to be minion of %s.%n", minionInformation[0], villainName);
        connection.close();
    }

    private static void addMinionToVillain(Connection connection, String[] minionInformation, String villainName) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("""
                INSERT INTO `minions_villains`(`minion_id`, `villain_id`)
                VALUES ((SELECT `id`
                         FROM `minions`
                         WHERE `name` LIKE ?),
                        (SELECT `id`
                         FROM `villains`
                         WHERE `name` LIKE ?));
                """);
        statement.setString(1, minionInformation[0]);
        statement.setString(2, villainName);
        statement.executeUpdate();
    }

    private static void saveMinion(Connection connection, String[] minionInformation) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("""
                INSERT INTO `minions`(`name`, `age`, `town_id`)
                VALUES (?, ?, (SELECT `id` FROM `towns` WHERE `towns`.`name` LIKE ?))
                """);
        statement.setString(1, minionInformation[0]);
        statement.setInt(2, Integer.parseInt(minionInformation[1]));
        statement.setString(3, minionInformation[2]);
        statement.executeUpdate();
    }

    private static void addVillainIfMissing(Connection connection, String villainName) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("""
                SELECT *
                FROM `villains`
                WHERE `name` LIKE ?;
                """);
        statement.setString(1, villainName);

        if (!statement.executeQuery().next()) {
            statement = connection.prepareStatement("INSERT INTO `villains`(`name`, `evilness_factor`) VALUES (?, 'evil')");
            statement.setString(1, villainName);
            statement.executeUpdate();
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }
    }

    private static void addTownIfMissing(Connection connection, String[] minionInformation) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("""
                SELECT *
                FROM `towns`
                WHERE `name` LIKE ?;
                """);
        statement.setString(1, minionInformation[2]);

        if (!statement.executeQuery().next()) {
            statement = connection.prepareStatement("INSERT INTO `towns`(`name`, `country`) VALUES (?, null)");
            statement.setString(1, minionInformation[2]);
            statement.executeUpdate();
            System.out.printf("Town %s was added to the database.%n", minionInformation[2]);
        }
    }
}
