package com.quizarena;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;

public class Game extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel roundLabel;
    private JRadioButton option1Button, option2Button, option3Button;
    private ButtonGroup optionsGroup;
    private JButton nextButton;
    public String userName, difficulty,country;
    private DBConnector db = new DBConnector();

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    public int currentRound = 1;
    private int totalScore = 0;

    private JTextArea textArea;
    private JLabel questionLabel;
    /**
     * Creates the game window and sets up the game.
     * Initializes game components and loads questions.
     * @param userName The username of the player
     * @param difficulty The selected difficulty level
     * @param country The country selected by the user
     * @param round The current round number
     */
    public Game(String userName, String difficulty, String country,int round) {
        this.userName = userName;
        this.difficulty = difficulty;
        this.country = country;
        this.currentRound = round == -1 ? 1 : round;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 350);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(183, 183, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        // Round label displaying the current round
        roundLabel = new JLabel("Round 1");
        roundLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        roundLabel.setBounds(200, 10, 100, 25);
        contentPane.add(roundLabel);
        // Option buttons for selecting answers
        option1Button = new JRadioButton();
        option1Button.setBounds(20, 139, 450, 25);
        contentPane.add(option1Button);

        option2Button = new JRadioButton();
        option2Button.setBounds(20, 166, 450, 25);
        contentPane.add(option2Button);

        option3Button = new JRadioButton();
        option3Button.setBounds(20, 193, 450, 25);
        contentPane.add(option3Button);
        // Group the option buttons
        optionsGroup = new ButtonGroup();
        optionsGroup.add(option1Button);
        optionsGroup.add(option2Button);
        optionsGroup.add(option3Button);
        // Next button to move to the next question
        nextButton = new JButton("Next");
        nextButton.setBounds(380, 272, 90, 31);
        contentPane.add(nextButton);
        // Text area to display the question
        textArea = new JTextArea();
        textArea.setBounds(20, 57, 440, 53); 
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        contentPane.add(textArea);
        // Label displaying the question number
        questionLabel = new JLabel("Question");
        questionLabel.setBounds(20, 39, 82, 13);
        contentPane.add(questionLabel);

        loadQuestions();
        Collections.shuffle(questions);
        displayQuestion(currentQuestionIndex, currentRound);
        // Action listener for the Next button
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!option1Button.isSelected() && !option2Button.isSelected() && !option3Button.isSelected()) {
                    JOptionPane.showMessageDialog(Game.this, "Please select an answer before proceeding.");
                    return;
                }

                checkAnswer();
                currentQuestionIndex++;

                if (currentQuestionIndex < 5) {
                    displayQuestion(currentQuestionIndex, currentRound);
                } else {
                    JOptionPane.showMessageDialog(Game.this, "Round " + currentRound + " over! Your score: " + score);
                    totalScore += score;
                    
                    System.out.println(currentRound);
                    
                    db.storePlayerRoundScore(userName, difficulty,country, score, currentRound);
                    
                    if(currentRound == 5) {
                    	String scoreDetails = db.getFormattedRoundScores(userName, difficulty, country);
                    	JOptionPane.showMessageDialog(Game.this, scoreDetails);
                    }
                    
                    score = 0;
                    currentQuestionIndex = 0;
                    currentRound++;
                    if (currentRound <= 5) {
                        roundLabel.setText("Round " + currentRound);
                        Collections.shuffle(questions); 
                        displayQuestion(currentQuestionIndex, currentRound);
                    } else {
                        showFinalScore();
                    }
                }
            }
            
        });
        System.out.println("User: " + userName + ", Difficulty: " + difficulty + ", Country: " + country);

    }

    /**
     * Loads all the question from the difficulty selected.
     */
    private void loadQuestions() {
        try {
            questions = db.getQuestionsByDifficulty(difficulty);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the current question and its options.
     * @param index The index of the current question
     * @param round The current round number
     */
    private void displayQuestion(int index, int round) {
        if (index < 0 || index >= questions.size()) {
            System.out.println("Invalid question index");
            return;
        }

        Question q = questions.get(index);
        roundLabel.setText("Round " + (round));
        questionLabel.setText("Question " + (index + 1));

        textArea.setText(q.getQuestion());

        option1Button.setText(q.getOption1());
        option2Button.setText(q.getOption2());
        option3Button.setText(q.getOption3());

        optionsGroup.clearSelection(); 
    }

    /**
     * Checks if the selected answer matches the correct answer
     */
    private void checkAnswer() {
        Question q = questions.get(currentQuestionIndex);
        String selectedAnswer = null;

        if (option1Button.isSelected()) {
            selectedAnswer = option1Button.getText();
        } else if (option2Button.isSelected()) {
            selectedAnswer = option2Button.getText();
        } else if (option3Button.isSelected()) {
            selectedAnswer = option3Button.getText();
        }

        if (selectedAnswer != null && selectedAnswer.equals(q.getCorrectAnswer())) {
            score++;
        }
    }

    /**
     * Shows the final score
     */
    private void showFinalScore() {
        JOptionPane.showMessageDialog(this, "Quiz Over! Your total score: " + totalScore);
        Homepage frame = new Homepage(userName, difficulty, country);
        frame.setVisible(true);
        dispose();
    }
}
