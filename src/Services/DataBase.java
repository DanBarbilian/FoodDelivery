package Services;
import java.sql.*;

public class DataBase {
    private static Connection connection;
    private static DataBase db = null;

    private DataBase() {
        String url = "jdbc:mysql://localhost:3306/delivery";
        String username = "java";
        String password = "password";
        try {
            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static DataBase getDatabase() {
        if (db == null) {
            db = new DataBase();
        }
        return db;
    }

    public static Connection getConnection() {
        return connection;
    }

}
