package Class_Src;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class CardLayoutFrame extends JFrame {
//    Main App Layout - - - - - - - - - -
    private CardLayout cardLayout;
//    Buttons - - - - - - - - - - - - - -
    private JButton startButton;
    private JButton exitButton;
    private JButton usernameButton;
    private JButton playECEQ;
    private JButton playDeadlockQ;
    private JButton[] optionButtons;
    private Random randomNum = new Random();
    private JButton resetToMenuButton;
//    Panels for Layout - - - - - - - - -
    private JPanel cardPanel;
    private JPanel welcomePanel;
    private JPanel gamePanel;
    private JPanel resultsPanel;
    private JPanel usernamePanel;
//    Results page  - - - - - - - - - - - - - - -
    private String username = "Guest User";
    private JTextField inputField;
    private JLabel questionLabel;
    private JLabel resultsLabel;
    private JLabel scoreBoard;
//    Timer - - - - - - - - - - - - - - -
    private Timer questionTimer;
    private int timerSeconds;
    private JLabel timerLabel;
//    Questions - - - - - - - - - - - - -
    private List<String> pathsQuestion = List.of("src\\Question_Text_Files\\ECEQuestions.txt",
        "src\\Question_Text_Files\\DeadlockQuestions.txt");
    private List<String> pathsRubric = List.of("src\\Question_Text_Files\\ECERubric.txt",
            "src\\Question_Text_Files\\DeadlockRubric.txt");
    private int questionSet;
    private List<ArrayList<String>> questionsList;
    private List<Integer> correctAnswers;
//              list of correct options based on currentQuestionID
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
        timerSetUp();

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
        playECEQ.addActionListener(handler);
        playDeadlockQ.addActionListener(handler);
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
        playECEQ = new JButton("Play ECE Questions");
        buttonPanels.add(playECEQ);
        playDeadlockQ = new JButton("Play Deadlock Questions");
        buttonPanels.add(playDeadlockQ);
        usernamePanel.add(buttonPanels, BorderLayout.CENTER);

        this.score = 0;
    }

    private void createGamePage() {
        gamePanel = new JPanel(new BorderLayout());

        currentQuestionID = 1;

        JPanel topPanel = new JPanel(new FlowLayout());
        questionLabel = new JLabel("Quiz screen", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(questionLabel);

        timerLabel = new JLabel("" + timerSeconds, SwingConstants.CENTER);
        timerLabel.setVerticalTextPosition(SwingConstants.CENTER);
        timerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        timerLabel.setHorizontalAlignment(FlowLayout.RIGHT);
        topPanel.add(timerLabel);

        gamePanel.add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton("Option " + i + 1);
            optionButtons[i].setFont(new Font("Times New Roman", Font.BOLD, 20));
            optionButtons[i].addActionListener(new OptionButtonHandler(i));
            buttonPanel.add(optionButtons[i]);
        }
        gamePanel.add(buttonPanel, BorderLayout.CENTER);

        exitButton = new JButton("Quit to Results");
        gamePanel.add(exitButton, BorderLayout.SOUTH);
    }

    private void timerSetUp() {
        TimerHandler tHandler = new TimerHandler();
        questionTimer = new Timer(1000, tHandler);
    }

    private void importQuestions() {
        questionsList = new ArrayList<>();
        correctAnswers = new ArrayList<>();

        qLoader = new QuestionsLoader(pathsQuestion.get(questionSet),
                pathsRubric.get(questionSet));
//        System.out.println(qLoader.map.get(0));
//        System.out.println(qLoader.list.get(0));
        this.qAmnt = 10;

        for (int i = 0; i < qAmnt ; i++) {
            questionsList.add(qLoader.map.get(i+1));
            correctAnswers.add(qLoader.list.get(i));
        }
    }

    private void loadQuestion(int questionNumber) {
        currentQuestionID = questionNumber;
        questionTimer.restart();
        if (questionsList == null || currentQuestionID > (questionsList.size() - 1)) {
            questionsList.clear();
            correctAnswers.clear();
            questionTimer.restart();
            questionTimer.stop();
            checkScore();
            cardLayout.show(cardPanel, "R");
            return;
        }
        questionTimer.start();
        timerSeconds = 15;
        timerLabel.setText("" + timerSeconds);
        questionLabel.setText(questionsList.get(currentQuestionID).get(0));
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setBackground(randomLightColor());
            optionButtons[i].setText(questionsList.get(currentQuestionID).get(i + 1));
        }
        System.out.println("On question: " + currentQuestionID);
    }

    private Color randomLightColor() {
        Color lightColor = new Color(randomNum.nextInt(54)+200,
                randomNum.nextInt(54)+200,randomNum.nextInt(54)+200);
        return lightColor;
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

//    used before showing results page to color background to be...
//    a color between green and red

    private void checkScore() {
        float gradMulti = (score/qAmnt);
        int r = gradMulti < 0.5? 255:(int)(310 - (110*gradMulti));
        int g = gradMulti < 0.5? (int)(200 + (110*gradMulti)):255;
        resultsPanel.setBackground(new Color(r, g,200));

    }

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton) {
                cardLayout.show(cardPanel, "U");
            } else if (e.getSource() == usernameButton) {
                String newUser = inputField.getText();
                if (newUser.length() > 15) {
                    JOptionPane.showMessageDialog(null, "Too long of a name",
                            "", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                setUser(newUser);
                JOptionPane.showMessageDialog(null, "Welcome " + username);
            } else if (e.getSource() == playECEQ) {
                questionSet = 0;
                updateScore(0);
                importQuestions();
                loadQuestion(0);
                cardLayout.show(cardPanel, "Q");
            } else if (e.getSource() == playDeadlockQ) {
                questionSet = 1;
                updateScore(0);
                importQuestions();
                loadQuestion(0);
                cardLayout.show(cardPanel, "Q");
            } else if (e.getSource() == exitButton) {
                questionTimer.restart();
                questionTimer.stop();
                questionsList.clear();
                correctAnswers.clear();
                checkScore();
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
                String newUser = String.format(e.getActionCommand());
                if (newUser.length() > 15) {
                    JOptionPane.showMessageDialog(null, "Too long of a name",
                            "", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                setUser(newUser);
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
                questionTimer.stop();
                JOptionPane.showMessageDialog(null, "Correct!",
                        "", JOptionPane.INFORMATION_MESSAGE);
                score++;
                updateScore(score);
            }
            else {
                questionTimer.stop();
                JOptionPane.showMessageDialog(null, "Incorrect.",
                        "", JOptionPane.ERROR_MESSAGE);
            }

            loadQuestion(currentQuestionID+1);
        }
    }

    private class TimerHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timerSeconds--;
            if (timerSeconds > 0) {
                timerLabel.setText("" + timerSeconds);
                System.out.println("Time Left: " +  timerSeconds);
            }
            else {
                timerLabel.setText("" + 0);
                questionTimer.restart();
                questionTimer.stop();
                JOptionPane.showMessageDialog(null, "Time Out :(",
                        "", JOptionPane.WARNING_MESSAGE);
                loadQuestion(currentQuestionID+1);
            }
        }
    }
}
