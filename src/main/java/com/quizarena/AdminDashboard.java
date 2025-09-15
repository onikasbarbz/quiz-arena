package com.quizarena;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.Font;
import java.awt.Color;

public class AdminDashboard extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField idField, questionField, option1Field, option2Field, option3Field, correctAnswerField;
    private JComboBox<String> difficultyComboBox;
    private JTable table;
    private DBConnector dbConnector = new DBConnector();

    public AdminDashboard() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(183, 183, 255));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 114, 760, 200);
        contentPane.add(scrollPane);
        
        JButton btnBeginner = new JButton("Beginner");
        btnBeginner.setBounds(259, 74, 100, 30);
        contentPane.add(btnBeginner);

        JButton btnIntermediate = new JButton("Intermediate");
        btnIntermediate.setBounds(369, 72, 120, 30);
        contentPane.add(btnIntermediate);

        JButton btnAdvanced = new JButton("Advanced");
        btnAdvanced.setBounds(499, 72, 100, 30);
        contentPane.add(btnAdvanced);

        JButton btnAllQuestions = new JButton("All Questions");
        btnAllQuestions.setBounds(129, 74, 120, 30);
        contentPane.add(btnAllQuestions);

        // Create the table with columns
        String[] columns = {"ID", "Question", "Option 1", "Option 2", "Option 3", "Correct Answer", "Difficulty"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel) {
            
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
                return false; // Make the table non-editable
            }};
        scrollPane.setViewportView(table);

        btnBeginner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadAllQuestionsByDifficulty("beginner");
            }
        });
        
        btnIntermediate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadAllQuestionsByDifficulty("intermediate");
            }
        });
        
        btnAdvanced.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadAllQuestionsByDifficulty("advanced");
            }
        });
        
        btnAllQuestions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadAllQuestions();
            }
        });
        
        loadAllQuestions();

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(10, 324, 50, 25);
        contentPane.add(lblId);

        idField = new JTextField();
        idField.setBounds(33, 324, 50, 25);
        contentPane.add(idField);

        JLabel lblQuestion = new JLabel("Question:");
        lblQuestion.setBounds(107, 324, 80, 25);
        contentPane.add(lblQuestion);

        questionField = new JTextField();
        questionField.setBounds(166, 324, 200, 25);
        contentPane.add(questionField);

        JLabel lblOption1 = new JLabel("Option 1:");
        lblOption1.setBounds(10, 382, 60, 25);
        contentPane.add(lblOption1);

        option1Field = new JTextField();
        option1Field.setBounds(70, 382, 100, 25);
        contentPane.add(option1Field);

        JLabel lblOption2 = new JLabel("Option 2:");
        lblOption2.setBounds(10, 419, 60, 25);
        contentPane.add(lblOption2);

        option2Field = new JTextField();
        option2Field.setBounds(70, 419, 100, 25);
        contentPane.add(option2Field);

        JLabel lblOption3 = new JLabel("Option 3:");
        lblOption3.setBounds(10, 461, 60, 25);
        contentPane.add(lblOption3);

        option3Field = new JTextField();
        option3Field.setBounds(70, 461, 100, 25);
        contentPane.add(option3Field);

        JLabel lblCorrectAnswer = new JLabel("Correct Answer:");
        lblCorrectAnswer.setBounds(213, 382, 100, 25);
        contentPane.add(lblCorrectAnswer);

        correctAnswerField = new JTextField();
        correctAnswerField.setBounds(323, 382, 100, 25);
        contentPane.add(correctAnswerField);

        JLabel lblDifficulty = new JLabel("Difficulty:");
        lblDifficulty.setBounds(419, 324, 70, 25);
        contentPane.add(lblDifficulty);

        String[] difficulties = {"Beginner", "Intermediate", "Advanced"};
        difficultyComboBox = new JComboBox<>(difficulties);
        difficultyComboBox.setBounds(517, 324, 100, 25);
        contentPane.add(difficultyComboBox);

        JButton btnInsert = new JButton("Insert Question");
        btnInsert.setBounds(10, 510, 150, 30);
        contentPane.add(btnInsert);

        JButton btnUpdate = new JButton("Update Question");
        btnUpdate.setBounds(178, 510, 150, 30);
        contentPane.add(btnUpdate);

        JButton btnDelete = new JButton("Delete Question");
        btnDelete.setBounds(350, 510, 150, 30);
        contentPane.add(btnDelete);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(670, 510, 100, 30);
        contentPane.add(btnLogout);

        JLabel lblAdminPanel = new JLabel("Admin System");
        lblAdminPanel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblAdminPanel.setBounds(286, 10, 304, 41);
        contentPane.add(lblAdminPanel);

        JButton btnLeaderboard = new JButton("LeaderBoard");
        btnLeaderboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<CompetitorList> beginnerScores = dbConnector.fetchPlayerScores("beginner");
                List<CompetitorList> intermediateScores = dbConnector.fetchPlayerScores("intermediate");
                List<CompetitorList> advancedScores = dbConnector.fetchPlayerScores("advanced");
                AdminLeaderBoard frame = new AdminLeaderBoard(beginnerScores, intermediateScores, advancedScores);
                frame.setVisible(true);
            }
        });
        btnLeaderboard.setBounds(509, 510, 150, 30);
        contentPane.add(btnLeaderboard);

        // Action Listeners
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertNewQuestion();
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateQuestion();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteQuestion();
            }
        });
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AdminLogin loginFrame = new AdminLogin();
                loginFrame.setVisible(true);
                dispose();
            }
        });
    }

    /** 
     * Load questions from the database and display them in the JTable.
     */
    private void loadAllQuestions() {
        try {
            List<Question> questions = dbConnector.getAllQuestions();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);  // Clear existing rows
            for (Question question : questions) {
                model.addRow(new Object[]{
                        question.getId(),
                        question.getQuestion(),
                        question.getOption1(),
                        question.getOption2(),
                        question.getOption3(),
                        question.getCorrectAnswer(),
                        question.getDifficulty()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while retrieving questions.");
        }
    }
    
    private void loadAllQuestionsByDifficulty(String difficulty) {
        try {
            List<Question> questions = dbConnector.getQuestionsByDifficulty(difficulty);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);  // Clear existing rows
            for (Question question : questions) {
                model.addRow(new Object[]{
                        question.getId(),
                        question.getQuestion(),
                        question.getOption1(),
                        question.getOption2(),
                        question.getOption3(),
                        question.getCorrectAnswer(),
                        question.getDifficulty()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while retrieving questions.");
        }
    }

    /** 
     * Insert a new question into the database based on the input fields.
     */
    private void insertNewQuestion() {
        String question = questionField.getText();
        String option1 = option1Field.getText();
        String option2 = option2Field.getText();
        String option3 = option3Field.getText();
        String correctAnswer = correctAnswerField.getText();
        String difficulty = (String) difficultyComboBox.getSelectedItem();

        if (question.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || correctAnswer.isEmpty() || difficulty == null || difficulty.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out!");
            return;
        }

        try {
            dbConnector.insertQuestion(question, option1, option2, option3, correctAnswer, difficulty);
            JOptionPane.showMessageDialog(this, "Question added successfully!");
            loadAllQuestions();  // Reload questions to reflect the change
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inserting question.");
        }
    }

    /** 
     * Update a question in the database based on the input fields.
     */
    private void updateQuestion() {
        try {
            int id = Integer.parseInt(idField.getText());
            String question = questionField.getText();
            String option1 = option1Field.getText();
            String option2 = option2Field.getText();
            String option3 = option3Field.getText();
            String correctAnswer = correctAnswerField.getText();
            String difficulty = (String) difficultyComboBox.getSelectedItem();

            dbConnector.updateQuestion(id, question, option1, option2, option3, correctAnswer, difficulty);
            JOptionPane.showMessageDialog(this, "Question updated successfully!");
            loadAllQuestions();  // Refresh after update
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating question.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.");
        }
    }

    /** 
     * Delete a question based on the ID field.
     */
    private void deleteQuestion() {
        try {
            int id = Integer.parseInt(idField.getText());
            dbConnector.deleteQuestion(id);
            JOptionPane.showMessageDialog(this, "Question deleted successfully!");
            loadAllQuestions();  // Refresh after deletion
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting question.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.");
        }
    }
}
