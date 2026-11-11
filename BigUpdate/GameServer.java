package BigUpdate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

public class GameServer {
    private static final int PORT = 8081;
    protected static Connection connection;
    private static int queueLength = 20;

    public static void main(String[] args) throws SQLException {
        System.out.println("Game Server Started");
        connection = DbConnection.getConnection();

        try {
            ServerSocket serverSocket = new ServerSocket(PORT, queueLength);
            System.out.println("Server listening on port: " + PORT);
            Socket socket = serverSocket.accept();
            System.out.println("Client Connected from: " + socket);

            ObjectOutputStream outp = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inp = new ObjectInputStream(socket.getInputStream());

            String inpMessage = (String) inp.readObject();
            String username = inpMessage.split("\\$", 2)[0];
            String password = inpMessage.split("\\$", 2)[1];
            System.out.println("Login Request from: " + username);


            int outcome = UserCRUD.verifyLogin(username, password); // we'll see if getUser will also handle creating new user
//             0 = Reoccuring user successfully logged in
//             1 = new user was created and will log in
//             2 = User failed to log in

            String message = "";
            User user;
            label:
            switch (outcome) {
                case 0: // Successful login
                    System.out.println("Case 0 in progress");
                    user = UserCRUD.getUser(username);
                    message = "Welcome back " + user.getUsername() + "!\n" +
                            "You have a high score of:\n" +
                            user.getScore();
                    break;

                case 1: // New User was made
                    System.out.println("Case 1 in progress");
                    user = new User(username, 0);
                    message = "Welcome " + user.getUsername() + "!\n" +
                            "You start off with a high score of:\n" +
                            user.getScore();
                    break;

                case 2: // FAILED
                    System.out.println("Case 2 in progress");
                    message = "Redo$Password was incorrect.";
                    outp.writeObject(message);
                    outp.flush();
                    for(int i = 0; i < 4; i++) {
                        inpMessage = (String) inp.readObject();
                        outcome = UserCRUD.verifyLogin(username, password);
                        if (outcome==0) {
                            user = UserCRUD.getUser(username);
                            message = "Pass$Welcome back " + user.getUsername() + "!\n" +
                                    "You have a high score of:\n" +
                                    user.getScore();
                            break label;
                        }
                    }
                    message = "Failed to login. Took too many tries.";
                    break;
            }

            outp.writeObject(message);
            outp.flush();

            System.out.println("Response sent to client. Closing connection...");
            socket.close();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
