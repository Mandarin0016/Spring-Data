import java.sql.*;
import java.util.Scanner;

public class E09_IncreaseAgeStoredProcedure {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        Connection connection = JdbcConnection.getConnection();

        CallableStatement getOlderProcedure = connection.prepareCall("{CALL usp_get_older(?)}");

        int id = Integer.parseInt(scanner.nextLine());

        getOlderProcedure.setInt(1, id);
        getOlderProcedure.executeUpdate();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT CONCAT_WS(' ', `name`, `age`) FROM `minions` WHERE id LIKE ?;");
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        System.out.println(resultSet.getString(1));

        connection.close();
    }
}
