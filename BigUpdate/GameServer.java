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

            String username = (String) inp.readObject();
            System.out.println("Login Request from: " + username);

            User user = UserCRUD.getUser(username); // we'll see if getuser will also handle creating new user

            String message = "Welcome " + user.getUsername() + "!\n" +
                    "You have a high score of:\n" +
                    user.getScore();
            outp.writeObject(message);
            outp.flush();

            System.out.println("Response sent to client. Closing connection...");

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
