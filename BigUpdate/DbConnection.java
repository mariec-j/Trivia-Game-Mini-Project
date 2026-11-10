package BigUpdate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String URL =
            "jdbc:mysql://localhost:3306/userdb";
    private static final String USER = "root";
    private static final String PASSWORD = "admin*1root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER, PASSWORD);
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        UserCRUD userCRUD = new UserCRUD(connection);
        userCRUD.listUsers();
//        userCRUD.addUser("newUser_inclass_1103");
    }
}
