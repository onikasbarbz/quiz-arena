package com.quizarena;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class AdminLogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldEmail;
    private JTextField textFieldPassword;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminLogin frame = new AdminLogin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public AdminLogin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(183, 183, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Admin Login");
        lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblTitle.setBounds(146, 10, 169, 29);
        contentPane.add(lblTitle);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(94, 49, 228, 13);
        contentPane.add(lblEmail);

        textFieldEmail = new JTextField();
        textFieldEmail.setBounds(94, 63, 228, 29);
        contentPane.add(textFieldEmail);
        textFieldEmail.setColumns(10);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(95, 102, 227, 13);
        contentPane.add(lblPassword);

        textFieldPassword = new JPasswordField();
        textFieldPassword.setBounds(94, 114, 228, 29);
        contentPane.add(textFieldPassword);
        textFieldPassword.setColumns(10);
        
        JLabel Answer = new JLabel("");
        Answer.setFont(new Font("Tahoma", Font.PLAIN, 14));
        Answer.setBounds(28, 204, 398, 49);
        contentPane.add(Answer);

        JButton loginButton = new JButton("Login");
        loginButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		String email = textFieldEmail.getText();
                String password = textFieldPassword.getText();

                try {
                    DBConnector con = new DBConnector();
                    String result = con.login(email, password);
                    System.out.println(result);
                    Answer.setText(result);
                    if(result.equals("Logged In Successfully")){
                    	AdminDashboard frame = new AdminDashboard();
                        frame.setVisible(true);  
		                dispose(); 
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Answer.setText("An error occurred while logging in.");
                }
        	}
        });
        loginButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        loginButton.setBounds(215, 165, 107, 29);
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
        btnBack.setBounds(10, 14, 73, 29);
        contentPane.add(btnBack);
        
        
    }
}
