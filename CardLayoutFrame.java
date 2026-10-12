import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CardLayoutFrame extends JFrame {
//    Buttons - - - - - - - - -
    private JButton startButton;
    private JButton exitButton;
    private JButton usernameButton;
    private JButton playButton;
    private JButton[] optionButtons;
    private JButton resetToMenuButton;
//    Main App Layout - - - - -
    private CardLayout cardLayout;
//    Panels for Layout - - - -
    private JPanel cardPanel;
    private JPanel welcomePanel;
    private JPanel gamePanel;
    private JPanel resultsPanel;
    private JPanel usernamePanel;
//    Misc - - - - - - - - - - -
    private String username = "Guest User";
    private JTextField inputField;
    private JLabel questionLabel;
    private JLabel resultsLabel;
    private int points = 0;
//    Questions - - - - - - - - -
    private List<List<String>> questionsList;
    private List<Integer> correctAnswers;
//              list of correct options based on currrentQuestionID
//              options 1-4 start on index 0, so indx 0 -> option 1, etc.
    private int currentQuestionID;

    public CardLayoutFrame() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        importQuestions();

        createWelcomePage();
        createUsernamePanel();
        createGamePage();
        createResultsPage();

        cardPanel.add(welcomePanel, "W");
        cardPanel.add(usernamePanel, "U");
        cardPanel.add(gamePanel, "Q");
        cardPanel.add(resultsPanel, "R");

        add(cardPanel);

        ButtonHandler handler = new ButtonHandler();
        startButton.addActionListener(handler);
        exitButton.addActionListener(handler);
        usernameButton.addActionListener(handler);
        playButton.addActionListener(handler);
        resetToMenuButton.addActionListener(handler);

    }

    private void createWelcomePage() {
        welcomePanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Welcome Screen", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        welcomePanel.add(title, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        startButton = new JButton("Start game!");
        buttonPanel.add(startButton);
        welcomePanel.add(buttonPanel,BorderLayout.SOUTH);
    }

    private void createUsernamePanel() {
        usernamePanel = new JPanel(new BorderLayout());

        inputField = new JTextField("Guest User");
        inputField.setFont(new Font("Arial", Font.PLAIN, 26));
        usernamePanel.add(inputField, BorderLayout.NORTH);

        JPanel buttonPanels = new JPanel(new FlowLayout());
        usernameButton = new JButton("Submit user name");
        buttonPanels.add(usernameButton);
        playButton = new JButton("Play Game");
        buttonPanels.add(playButton);
        usernamePanel.add(buttonPanels, BorderLayout.CENTER);
    }

    private void createGamePage() {
        gamePanel = new JPanel(new BorderLayout());

        currentQuestionID = 1;

        questionLabel = new JLabel("Quiz screen", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 32));
        gamePanel.add(questionLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton("Option " + i+1);
            optionButtons[i].addActionListener(new OptionButtonHandler(i));
            buttonPanel.add(optionButtons[i]);
        }
        gamePanel.add(buttonPanel, BorderLayout.CENTER);

        loadQuestion(0);

        exitButton = new JButton("Exit to Results");
        gamePanel.add(exitButton, BorderLayout.SOUTH);
    }

    private void importQuestions() {
        questionsList = new ArrayList<>();
        correctAnswers = new ArrayList<>();

        questionsList.add(List.of("Freebie! Please pick the Correct option.",
                "Incorrect",
                "Incorrect",
                "Correct",
                "Incorrect"));

        correctAnswers.add(2);

        questionsList.add(List.of("What is Ohm's Law?",
                "V = I * C",
                "I = R / V",
                "R = I * V",
                "V = I * R"));

        correctAnswers.add(3);

        questionsList.add(List.of("What is an integer?",
                "A whole number",
                "Any number above 0",
                "A bug",
                "Any number bellow 0"));

        correctAnswers.add(0);

        questionsList.add(List.of("What is an integer?",
                "A whole number",
                "Any number above 0",
                "A bug",
                "Any number bellow 0"));

        correctAnswers.add(0);

        questionsList.add(List.of("What is an integer?",
                "A whole number",
                "Any number above 0",
                "A bug",
                "Any number bellow 0"));

        correctAnswers.add(0);

        questionsList.add(List.of("What is an integer?",
                "A whole number",
                "Any number above 0",
                "A bug",
                "Any number bellow 0"));

        correctAnswers.add(0);

        questionsList.add(List.of("What is an integer?",
                "A whole number",
                "Any number above 0",
                "A bug",
                "Any number bellow 0"));

        correctAnswers.add(0);

        questionsList.add(List.of("What is an integer?",
                "A whole number",
                "Any number above 0",
                "A bug",
                "Any number bellow 0"));

        correctAnswers.add(0);

        questionsList.add(List.of("What is an integer?",
                "A whole number",
                "Any number above 0",
                "A bug",
                "Any number bellow 0"));

        correctAnswers.add(0);

        questionsList.add(List.of("What is an integer?",
                "A whole number",
                "Any number above 0",
                "A bug",
                "Any number bellow 0"));

        correctAnswers.add(0);

    }

    private void loadQuestion(int questionNumber) {
        currentQuestionID = questionNumber;
        if (questionsList == null || currentQuestionID > (questionsList.size()-1)) {
            cardLayout.show(cardPanel, "R");
            return;
        }
        questionLabel.setText(questionsList.get(currentQuestionID).get(0));
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(questionsList.get(currentQuestionID).get(i+1));
        }
    }

    private void createResultsPage() {
        resultsPanel = new JPanel(new BorderLayout());
        resultsLabel = new JLabel("Results Screen - " + username, SwingConstants.CENTER);
        resultsPanel.add(resultsLabel, BorderLayout.NORTH);

        resetToMenuButton = new JButton("Go back to Menu");
        resultsPanel.add(resetToMenuButton, BorderLayout.SOUTH);

    }

    private void setUser(String userConfig) {
        this.username = userConfig;
        resultsLabel.setText("Results Screen - " + username);
    }

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton) {
                cardLayout.show(cardPanel, "U");
            } else if (e.getSource() == usernameButton) {
                setUser(inputField.getText());
                JOptionPane.showMessageDialog(null, "Welcome " + username);
            } else if (e.getSource() == playButton) {
                cardLayout.show(cardPanel, "Q");
            } else if (e.getSource() == exitButton) {
                cardLayout.show(cardPanel, "R");
            } else if (e.getSource() == resetToMenuButton) {
                cardLayout.show(cardPanel,"W");
            }
        }
    }

    private class OptionButtonHandler implements ActionListener {
        private int optionID;
        private boolean isCorrect;

        OptionButtonHandler(int num) {
            this.optionID = num;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            Check if option picked was correct
            if (optionID == correctAnswers.get(currentQuestionID)) {
                JOptionPane.showMessageDialog(null, "Correct!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Incorrect.");
            }

            loadQuestion(currentQuestionID+1);
        }
    }
}
