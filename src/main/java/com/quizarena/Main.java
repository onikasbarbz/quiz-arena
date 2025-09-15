package com.quizarena;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     * Initializes and displays the main frame.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the main frame.
     * Sets up the UI components and event listeners.
     */
    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(183, 183, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Welcome to QUIZ");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(147, 62, 188, 61);
        contentPane.add(lblNewLabel);

      
        
        JButton btnPlay = new JButton("Play Game");
        btnPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
        	    CompetitorLogin frame = new CompetitorLogin();
                frame.setVisible(true);
                dispose(); 
            }
        });
        btnPlay.setBounds(157, 133, 120, 40);
        contentPane.add(btnPlay);

        JButton btnAdmin = new JButton("Admin");
        btnAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AdminLogin loginFrame = new AdminLogin(); 
                loginFrame.setVisible(true);    
                dispose(); 
            }
        });
        btnAdmin.setBounds(325, 10, 101, 40);
        contentPane.add(btnAdmin);
    }
}
