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
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class AdminLeaderBoard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblNewLabel;
    private JButton btnNewButton;
    private CardLayout cardLayout;
    private JPanel cards;

    public AdminLeaderBoard(List<CompetitorList> beginnerScores, List<CompetitorList> intermediateScores, List<CompetitorList> advancedScores) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 400); 
        contentPane = new JPanel();
        contentPane.setBackground(new Color(183, 183, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblNewLabel = new JLabel("Admin LeaderBoard");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(157, 41, 208, 25);
        contentPane.add(lblNewLabel);
        
        btnNewButton = new JButton("<");
        btnNewButton.setBounds(10, 14, 85, 21);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(btnNewButton);

        JButton beginnerButton = new JButton("Beginner");
        beginnerButton.setBounds(425, 45, 111, 21);
        contentPane.add(beginnerButton);

        JButton intermediateButton = new JButton("Intermediate");
        intermediateButton.setBounds(546, 45, 120, 21);
        contentPane.add(intermediateButton);

        JButton advancedButton = new JButton("Advanced");
        advancedButton.setBounds(676, 45, 100, 21);
        contentPane.add(advancedButton);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.setBounds(10, 81, 766, 239);
        contentPane.add(cards);

        JPanel beginnerPanel = createScorePanel(beginnerScores);
        JPanel intermediatePanel = createScorePanel(intermediateScores);
        JPanel advancedPanel = createScorePanel(advancedScores);

        cards.add(beginnerPanel, "Beginner");
        cards.add(intermediatePanel, "Intermediate");
        cards.add(advancedPanel, "Advanced");
        
        JButton btnReport = new JButton("View Report");
        btnReport.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int totalParticipants = beginnerScores.size() + intermediateScores.size() + advancedScores.size();

                CompetitorList topBeginner = beginnerScores.isEmpty() ? null : beginnerScores.get(0);
                CompetitorList topIntermediate = intermediateScores.isEmpty() ? null : intermediateScores.get(0);
                CompetitorList topAdvanced = advancedScores.isEmpty() ? null : advancedScores.get(0);

                StringBuilder report = new StringBuilder();
                report.append("Total Participants: ").append(totalParticipants).append("\n\n");

                if (topBeginner != null) {
                    report.append("Highest Score in Beginner: ").append(topBeginner.getName())
                          .append(" (Score: ").append(topBeginner.getTotalScore()).append(")\n");
                } else {
                    report.append("No participants in Beginner division.\n");
                }

                if (topIntermediate != null) {
                    report.append("Highest Score in Intermediate: ").append(topIntermediate.getName())
                          .append(" (Score: ").append(topIntermediate.getTotalScore()).append(")\n");
                } else {
                    report.append("No participants in Intermediate division.\n");
                }

                if (topAdvanced != null) {
                    report.append("Highest Score in Advanced: ").append(topAdvanced.getName())
                          .append(" (Score: ").append(topAdvanced.getTotalScore()).append(")\n");
                } else {
                    report.append("No participants in Advanced division.\n");
                }

                JOptionPane.showMessageDialog(contentPane, report.toString(), "Quiz Report", JOptionPane.INFORMATION_MESSAGE);

                System.out.println("----- Quiz Report -----");
                System.out.println("Total Participants: " + totalParticipants);

                if (topBeginner != null) {
                    System.out.println("Highest Score in Beginner: " + topBeginner.getName() + " (Total Score: " + topBeginner.getTotalScore() + ")");
                } else {
                    System.out.println("No participants in Beginner division.");
                }

                if (topIntermediate != null) {
                    System.out.println("Highest Score in Intermediate: " + topIntermediate.getName() + " (Total Score: " + topIntermediate.getTotalScore() + ")");
                } else {
                    System.out.println("No participants in Intermediate division.");
                }

                if (topAdvanced != null) {
                    System.out.println("Highest Score in Advanced: " + topAdvanced.getName() + " (Total Score: " + topAdvanced.getTotalScore() + ")");
                } else {
                    System.out.println("No participants in Advanced division.");
                }
            }
        });

        btnReport.setBounds(635, 330, 141, 21);
        contentPane.add(btnReport);

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
