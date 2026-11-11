package BigUpdate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
//import java.util.Scanner;
import javax.swing.JOptionPane;

public class GameClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8081;

    public static void main(String[] args) throws IOException {
        System.out.println("Connecting to server...");

        try {
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            ObjectOutputStream outp = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inp = new ObjectInputStream(socket.getInputStream());

            String username = JOptionPane.showInputDialog("Enter your name:");
            String password = JOptionPane.showInputDialog("Enter your password:");

            // Sends username to server
            // You can also probably put password with username via this!
            outp.writeObject(username + "$" + password);
            outp.flush();

            // Recieve response
            String inpMessage = (String) inp.readObject();
            String message = inpMessage.split("\\$",2)[1];
            String key = inpMessage.split("\\$",2)[0];

            if (key.equals("Redo")) {
                for (int i = 0; i <4; i++) {
                    password = JOptionPane.showInputDialog("Reenter your password:");
                    outp.writeObject(password);
                    outp.flush();
                }
                inpMessage = (String) inp.readObject();
            }


            JOptionPane.showMessageDialog(null,"Server says: " + message);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
