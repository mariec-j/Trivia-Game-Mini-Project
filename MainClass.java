import Class_Src.CardLayoutFrame;
import javax.swing.JFrame;

public class MainClass {
    public static void main(String[] args) {
        CardLayoutFrame frame = new CardLayoutFrame();
        frame.setTitle("Trivia Game");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
