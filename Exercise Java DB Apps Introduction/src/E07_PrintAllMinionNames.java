import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class E07_PrintAllMinionNames {
    public static void main(String[] args) throws SQLException {
        Connection connection = JdbcConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement("""
                SELECT name
                FROM minions;
                """);

        List<String> names = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            names.add(resultSet.getString(1));
        }

        for (int i = 0; i < names.size() / 2; i++) {
            System.out.println(names.get(i));
            System.out.println(names.get(names.size() - i - 1));
        }

        connection.close();
    }
}
