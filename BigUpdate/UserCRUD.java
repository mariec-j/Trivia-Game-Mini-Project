package BigUpdate;
/**
 * File: UserCRUD.java
 *
 * "Create Read Update and Delete"
 *
 * Class that utilizes java.sql to edit the user sql
 * database from the java program. Not to be confused
 * with User.java, which is a class to represent the
 * current user/client playing the game.
 *
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserCRUD {
    private Connection CRUDConnection;

    public UserCRUD(Connection connection) {
        this.CRUDConnection = connection;
    }

//    public static boolean verifyUser(String password) {
//        String sql = "SELECT password FROM userdb.users WHERE username = ?";
//        try {
//            /**
//             *  Verifies user by checking the password column
//             *  and seeing if it matches
//             *
//             *  if match
//             *  return true and whatever uses this function will
//             *  do the appropriate steps afterwards, such as providing
//             *  the welcome message and allowing client to continue with game
//             *
//             *  if not match
//             *  make the client reenter password for x amount of attempts
//             *  lowk idk what happens after too many attempts figure that out later
//             */
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }

    //CREATE - - - - - - - - - - - - - - - - - - - -
    public static User addUser(String username) {
        String sql = "INSERT INTO userdb.users(username) VALUES(?)";
        try {
            PreparedStatement preparedStatement =
                    GameServer.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            System.out.println("User added " + username);
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
            e.printStackTrace();
        }
        return new User(username, 0);
    }

    //READ - - - - - - - - - - - - - - - - - - - - -
    public void listUsers() {
        String sql = "SELECT * FROM users";
        try {
            Statement stmt = CRUDConnection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            System.out.println("Current users:");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id")
                        + "|"
                        + resultSet.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User getUser(String username) {
        String sql = "SELECT * FROM userdb.users WHERE username = ?";
        try {
            PreparedStatement selectStmt = GameServer.connection.prepareStatement(sql);
            selectStmt.setString(1, username);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                int score = rs.getInt("score");
                System.out.println("Welcome Back " + username + ". Highest Score = " + score);
                return new User(username, score);
            } else {
                return addUser(username);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new User(username, 0);
    }
}
