package com.quizarena;

import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

import java.awt.CardLayout;
import java.awt.Font;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class CompetitorLeaderBoard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblNewLabel;
    private JButton btnNewButton;
    private CardLayout cardLayout;
    private JPanel cards;

    public CompetitorLeaderBoard(List<CompetitorList> beginnerScores, List<CompetitorList> intermediateScores, List<CompetitorList> advancedScores, String userName, String difficulty, String country) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 400); 
        contentPane = new JPanel();
        contentPane.setBackground(new Color(183, 183, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblNewLabel = new JLabel("Player LeaderBoard");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(164, 41, 191, 25);
        contentPane.add(lblNewLabel);
        
        btnNewButton = new JButton("<");
        btnNewButton.setBounds(10, 14, 72, 21);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Homepage frame = new Homepage(userName,difficulty, country);
            	frame.setVisible(true);
                dispose();
            }
        });
        contentPane.add(btnNewButton);

        JButton beginnerButton = new JButton("Beginner");
        beginnerButton.setBounds(425, 45, 111, 21);
        contentPane.add(beginnerButton);

        JButton intermediateButton = new JButton("Intermediate");
        intermediateButton.setBounds(546, 44, 120, 21);
        contentPane.add(intermediateButton);

        JButton advancedButton = new JButton("Advanced");
        advancedButton.setBounds(676, 44, 100, 21);
        contentPane.add(advancedButton);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.setBounds(10, 81, 766, 272);
        contentPane.add(cards);

        JPanel beginnerPanel = createScorePanel(beginnerScores);
        JPanel intermediatePanel = createScorePanel(intermediateScores);
        JPanel advancedPanel = createScorePanel(advancedScores);

        cards.add(beginnerPanel, "Beginner");
        cards.add(intermediatePanel, "Intermediate");
        cards.add(advancedPanel, "Advanced");

        beginnerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "Beginner");
            }
        });

        intermediateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "Intermediate");
            }
        });

        advancedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "Advanced");
            }
        });
        
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Homepage frame = new Homepage(userName, difficulty, country);
                frame.setVisible(true);
                dispose();
            }
        });
    }

    private JPanel createScorePanel(List<CompetitorList> playerScores) {
        Collections.sort(playerScores, (a, b) -> b.getTotalScore() - a.getTotalScore());

        String[] columnNames = {"Competitor Id", "Rank", "Name", "Level", "Country", "Score 1", "Score 2", "Score 3", "Score 4", "Total Score"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        int rank = 1;
        for (CompetitorList player : playerScores) {
            Object[] row = {
                player.getCompetitorId(),
                rank++, 
                player.getName(), 
                player.getLevel(), 
                player.getCountry(),
                player.getScore1(), 
                player.getScore2(), 
                player.getScore3(), 
                player.getScore4(), 
                player.getTotalScore()
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = new JPanel();
        panel.setLayout(new CardLayout());
        panel.add(scrollPane);

        return panel;
    }
}

