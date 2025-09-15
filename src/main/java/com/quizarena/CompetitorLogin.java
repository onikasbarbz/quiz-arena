package com.quizarena;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.Color;

public class CompetitorLogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField nameField;
    private JComboBox<String> difficultyComboBox;
    private JButton loginButton;
    private JTextField countryField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CompetitorLogin frame = new CompetitorLogin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CompetitorLogin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(183, 183, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);  

        JLabel lblTitle = new JLabel("Player Login");
        lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblTitle.setBounds(144, 10, 169, 29);
        contentPane.add(lblTitle);

        JLabel lblName = new JLabel("Username");
        lblName.setBounds(103, 49, 218, 13);
        contentPane.add(lblName);

        nameField = new JTextField();
        nameField.setBounds(103, 63, 218, 29);
        contentPane.add(nameField);
        nameField.setColumns(10);

        JLabel lblDifficulty = new JLabel("Select Difficulty");
        lblDifficulty.setBounds(103, 151, 218, 13);
        contentPane.add(lblDifficulty);

        String[] difficultyLevels = {"Beginner", "Intermediate", "Advanced"};
        difficultyComboBox = new JComboBox<>(difficultyLevels);
        difficultyComboBox.setBounds(103, 164, 218, 29);
        contentPane.add(difficultyComboBox);

        JLabel resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        resultLabel.setBounds(10, 204, 398, 49);
        contentPane.add(resultLabel);

        loginButton = new JButton("Login");
        loginButton.setBounds(213, 203, 100, 31);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					handleLogin(resultLabel);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        contentPane.add(loginButton);
        
        JButton btnBack = new JButton("<");
        btnBack.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		Main frame = new Main();
                frame.setVisible(true);
                dispose();
        	}
        });
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnBack.setBounds(103, 203, 82, 31);
        contentPane.add(btnBack);
        
        countryField = new JTextField();
        countryField.setColumns(10);
        countryField.setBounds(103, 116, 218, 29);
        contentPane.add(countryField);
        
        JLabel lblCountry = new JLabel("Country");
        lblCountry.setBounds(103, 102, 218, 13);
        contentPane.add(lblCountry);
    }

    public void handleLogin(JLabel resultLabel) throws SQLException {
        String userName = nameField.getText();
        String difficulty = (String) difficultyComboBox.getSelectedItem();
        String country = countryField.getText();

        if (userName.isEmpty() || country.isEmpty() || difficulty == null || difficulty.isEmpty()) {
            resultLabel.setText("All fields must be filled out!");
            return;
        }
        
        Homepage frame = new Homepage(userName,difficulty, country);
        frame.setVisible(true);
        this.dispose();
    }
}
