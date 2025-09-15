package com.quizarena;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class CompetitorScoreDetail extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea scoreDisplay;
    private JLabel nameLabel;
    private JLabel levelLabel;
    private JLabel lblNewLabel;
    private JButton btnNewButton;


    public CompetitorScoreDetail(String userName, String difficulty, String country) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400); 
        contentPane = new JPanel();
        contentPane.setBackground(new Color(183, 183, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        nameLabel = new JLabel("Name: " + userName);
        nameLabel.setBounds(10, 45, 200, 25);
        contentPane.add(nameLabel);

        levelLabel = new JLabel("Level: " + difficulty);
        levelLabel.setBounds(10, 80, 200, 25);
        contentPane.add(levelLabel);

        DBConnector dbConnector = new DBConnector();
        String scoreDetails = dbConnector.getFormattedRoundScores(userName, difficulty, country);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 115, 560, 240);
        contentPane.add(scrollPane);

        scoreDisplay = new JTextArea();
        scoreDisplay.setEditable(false);
        scoreDisplay.setText(scoreDetails);
        scrollPane.setViewportView(scoreDisplay);

        lblNewLabel = new JLabel("Personal Score");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 19));
        lblNewLabel.setBounds(208, 41, 175, 25);
        contentPane.add(lblNewLabel);

        btnNewButton = new JButton("<");
        btnNewButton.setBounds(10, 14, 65, 21);
        contentPane.add(btnNewButton);

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Homepage frame = new Homepage(userName,difficulty, country);
                frame.setVisible(true);
                dispose();
            }
        });
    }
}
