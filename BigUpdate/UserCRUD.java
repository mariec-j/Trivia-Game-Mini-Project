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
import java.util.NoSuchElementException;

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
    public static void addUser(String username, String password) {
        String sql = "INSERT INTO userdb.users(username, score, password) VALUES(?,?,?)";
        try {
            PreparedStatement preparedStatement =
                    GameServer.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, "0");
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();
            System.out.println("User added " + username);

        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
            e.printStackTrace();
        }
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

    /**
     *
     * @param username
     * @param password
     * @return int base on the outcome of the function
     * 0 = Reoccuring user successfully logged in
     * 1 = new user was created and will log in
     * 2 = User failed to log in
     */
    public static int verifyLogin(String username, String password) {
        String sql = "SELECT * FROM userdb.users WHERE username = ?";
        try {
            PreparedStatement selectStmt = GameServer.connection.prepareStatement(sql);
            selectStmt.setString(1, username);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                // User exists: check if the password matches
                String dbPassword = rs.getString("password");
                if (dbPassword != null && dbPassword.equals(password)) {
                    int score = rs.getInt("score");
                    System.out.println("User, " + username + " found. Highest Score = " + score);
                    return 0;
                } else {
                    System.out.println("Invalid password for user: " + username);
                    return 2; // Signals a failed login to GameServer
                }
            } else {
                // User does not exist: register them with the provided password
                addUser(username, password);
                System.out.println("Add user successfully done.");
                return 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 3;
    }

    /**
     *
     * @param username
     * @return User
     */
    public static User getUser(String username) throws NoSuchElementException {
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
                throw new NoSuchElementException("User somehow doesn't exist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new NoSuchElementException("User somehow doesn't exist.");
    }
}