package com.quizarena;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class Homepage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel welcomeLabel;
    
    DBConnector dbConnector = new DBConnector();

    /**
     * Creates the Homepage frame for the user.
     * Sets up the UI components and event listeners.
     * @param userName The username of the player
     * @param difficulty The selected difficulty level
     * @param country The country selected by the user
     */
    public Homepage(String userName, String difficulty, String country) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(183, 183, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        // Display welcome message with the username
        welcomeLabel = new JLabel("Welcome, " + userName + "!");
        welcomeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        welcomeLabel.setBounds(138, 35, 257, 29);
        contentPane.add(welcomeLabel);
        // Button to start the game
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Tahoma", Font.BOLD, 10));
        playButton.setBounds(138, 74, 146, 30);
        contentPane.add(playButton);
        // Button to show the player's score
        JButton showScoreButton = new JButton("Show Your Score");
        showScoreButton.setBounds(280, 147, 146, 30);
        contentPane.add(showScoreButton);
        // Button to show the leaderboard
        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.setBounds(280, 187, 146, 30);
        contentPane.add(leaderboardButton);
        // Button to log out and go back to the login screen
        JButton leaderboardButton_1 = new JButton("Logout");
        leaderboardButton_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		CompetitorLogin frame = new CompetitorLogin();
                frame.setVisible(true);
                dispose(); 
        	}
        });
        leaderboardButton_1.setBounds(280, 227, 146, 30);
        contentPane.add(leaderboardButton_1);
        // Action for the 'Play' button - Starts a new game if not already played 5 rounds
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 int round = dbConnector.checkEmptyScores(userName, difficulty, country);

         		if (round != 0) {
         		    Game frame = new Game(userName,difficulty, country, round);
         			frame.setVisible(true);
         		    dispose();
         		} else {
         			JOptionPane.showMessageDialog(Homepage.this,"You have alrerady played 5 rounds");
         		}
            }
        });
        // Action for the 'Show Your Score' button - Displays the player's score details
        showScoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	CompetitorScoreDetail frame = new CompetitorScoreDetail(userName, difficulty, country);
                frame.setVisible(true);
            }
        });
        // Action for the 'Leaderboard' button - Displays the leaderboard for all difficulty levels
        leaderboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	List<CompetitorList> beginnerScores = dbConnector.fetchPlayerScores("beginner");
        		List<CompetitorList> intermediateScores = dbConnector.fetchPlayerScores("intermediate");
        		List<CompetitorList> advancedScores = dbConnector.fetchPlayerScores("advanced");
            	CompetitorLeaderBoard frame = new CompetitorLeaderBoard(beginnerScores,intermediateScores,advancedScores, userName, difficulty, country);
                frame.setVisible(true);
            }
        });
    }
}
