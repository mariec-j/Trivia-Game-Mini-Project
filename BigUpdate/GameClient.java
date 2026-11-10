package BigUpdate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8081;

    public static void main(String[] args) throws IOException {
        System.out.println("Connecting to server...");

        try {
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            ObjectOutputStream outp = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inp = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your username: ");
            String username = scanner.nextLine();

            // Sends username to server
            // You can also probably put password with username via this!
            outp.writeObject(username);
            outp.flush();

            // Recieve response
            String message = (String) inp.readObject();
            System.out.println("Server says: " + message);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
