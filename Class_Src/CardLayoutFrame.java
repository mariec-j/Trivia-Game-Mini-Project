package Class_Src;

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
    private JLabel scoreBoard;
    //    Questions - - - - - - - - -
    private List<ArrayList<String>> questionsList;
    private List<Integer> correctAnswers;
//              list of correct options based on currrentQuestionID
//              options 1-4 start on index 0, so indx 0 -> option 1, etc.
    private int currentQuestionID;
    private QuestionsLoader qLoader;
    private float score;
    private int qAmnt;

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
        TextFieldHandler inputHandler = new TextFieldHandler();
        startButton.addActionListener(handler);
        exitButton.addActionListener(handler);
        usernameButton.addActionListener(handler);
        playButton.addActionListener(handler);
        resetToMenuButton.addActionListener(handler);
        inputField.addActionListener(inputHandler);

    }

    private void createWelcomePage() {
        welcomePanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Welcome Screen", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        welcomePanel.add(title, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        startButton = new JButton("Start game!");
        buttonPanel.add(startButton);
        welcomePanel.add(buttonPanel, BorderLayout.SOUTH);
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
        this.score = 0;
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
            optionButtons[i] = new JButton("Option " + i + 1);
            optionButtons[i].addActionListener(new OptionButtonHandler(i));
            buttonPanel.add(optionButtons[i]);
        }
        gamePanel.add(buttonPanel, BorderLayout.CENTER);

        loadQuestion(0);

        exitButton = new JButton("Quit to Results");
        gamePanel.add(exitButton, BorderLayout.SOUTH);
    }

    private void importQuestions() {
        questionsList = new ArrayList<>();
        correctAnswers = new ArrayList<>();

        qLoader = new QuestionsLoader("src\\Question_Text_Files\\questions.txt",
                "src\\Question_Text_Files\\rubric.txt");
        System.out.println(qLoader.map.get(0));
        System.out.println(qLoader.list.get(0));
        this.qAmnt = 5;

        for (int i = 0; i < qAmnt ; i++) {
            questionsList.add(qLoader.map.get(i+1));
            correctAnswers.add(qLoader.list.get(i));
        }
    }

    private void loadQuestion(int questionNumber) {
        currentQuestionID = questionNumber;
        if (questionsList == null || currentQuestionID > (questionsList.size() - 1)) {
            questionsList.clear();
            correctAnswers.clear();
            cardLayout.show(cardPanel, "R");
            return;
        }
        questionLabel.setText(questionsList.get(currentQuestionID).get(0));
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(questionsList.get(currentQuestionID).get(i + 1));
        }
    }

    private void createResultsPage() {
        resultsPanel = new JPanel(new BorderLayout());
        resultsLabel = new JLabel("Results Screen - " + username, SwingConstants.CENTER);
        resultsLabel.setFont(new Font("Arial", Font.BOLD, 32));
        resultsPanel.add(resultsLabel, BorderLayout.NORTH);

        scoreBoard = new JLabel();
        scoreBoard.setHorizontalAlignment(SwingConstants.CENTER);
        scoreBoard.setFont(new Font("Arial", Font.PLAIN, 18));
        scoreBoard.setText("<html><body><b>- - - - - SCORE - - - - -</b><br>" +
                score + " / " + qAmnt + "<br>" +
                "\nScore Percentage: " + score/qAmnt + "</body></html>");
        resultsPanel.add(scoreBoard);

        resetToMenuButton = new JButton("Go back to Menu");
        resultsPanel.add(resetToMenuButton, BorderLayout.SOUTH);

    }

    private void setUser(String userConfig) {
        this.username = userConfig;
        resultsLabel.setText("Results Screen - " + username);
    }

    private void updateScore(float newScore) {
        this.score = newScore;
        scoreBoard.setText("<html><body><b>- - - - - SCORE - - - - -</b><br>" +
                score + " / " + qAmnt + "<br>" +
                "\nScore Percentage: " + (score/qAmnt)*100 + "%</body></html>");
    };

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
                cardLayout.show(cardPanel, "W");
            }
        }
    }

    private class TextFieldHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == inputField) {
                setUser(String.format(e.getActionCommand()));
                JOptionPane.showMessageDialog(null, "Welcome " + username);
            }
        }
    }

    private class OptionButtonHandler implements ActionListener {
        private int optionID;

        OptionButtonHandler(int num) {
            this.optionID = num;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            Check if option picked was correct
            if (optionID == correctAnswers.get(currentQuestionID)) {
                JOptionPane.showMessageDialog(null, "Correct!");
                score++;
                updateScore(score);
            }
            else {
                JOptionPane.showMessageDialog(null, "Incorrect.");
            }

            loadQuestion(currentQuestionID+1);
        }
    }
}
