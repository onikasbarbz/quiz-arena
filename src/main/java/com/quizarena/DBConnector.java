package com.quizarena;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBConnector implements DBInterface {
    static final String DB_URL = "jdbc:mysql://localhost/quiz";
    static final String USERNAME = "root";
    static final String PASSWORD = "";

    Connection connection;

    String createAdminTableSQL = "CREATE TABLE IF NOT EXISTS Admin (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "Email VARCHAR(100) NOT NULL, " +
            "Password VARCHAR(100) NOT NULL" +
            ");";
    
    String createTableQuestionSQL = "CREATE TABLE IF NOT EXISTS Questions (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "question VARCHAR(100) NOT NULL, " +
            "option1 VARCHAR(100) NOT NULL, " +
            "option2 VARCHAR(100) NOT NULL, " +
            "option3 VARCHAR(100) NOT NULL, " +
            "correctAnswer VARCHAR(100) NOT NULL, " +
            "difficulty VARCHAR(100) NOT NULL" +
            ");";
    
    String createTablePlayerSQL = "CREATE TABLE IF NOT EXISTS Player (" +
            "CompetitorID INT AUTO_INCREMENT PRIMARY KEY , " +
            "Name VARCHAR(100) NOT NULL, " +
            "Level VARCHAR(50) NOT NULL, " +
            "Country VARCHAR(50) NOT NULL, " +
            "Score1 INT, " +
            "Score2 INT, " +
            "Score3 INT, " +
            "Score4 INT, " +
            "Score5 INT" +
            ");";

    
    String insertQuestionSQL = "INSERT INTO Questions (question, option1, option2, option3, correctAnswer, difficulty) VALUES (?, ?, ?, ?, ?, ?);";
    String insertAdminSQL = "INSERT INTO Admin (Email, Password) VALUES (?, ?);";
    String getAllQuestionSQL = "SELECT * FROM Questions;";
    String deleteQuestionSQL = "DELETE FROM Questions WHERE id = ?;";
    String updateQuestionSQL = "UPDATE Questions SET question = ?, option1 = ?, option2 = ?, option3 = ?, correctAnswer = ?, difficulty = ? WHERE id = ?;";

    /**
     * This constructor initialises the database.
     */
    public DBConnector() {
        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            createTable(createAdminTableSQL);
            createTable(createTableQuestionSQL);
            createTable(createTablePlayerSQL);
            
            insertAdmin("admin@admin.com", "admin");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * This method creates table.
     * @param createTableSql query to create table
     */
    public void createTable(String createTableSQL) {
        try (PreparedStatement createStmt = connection.prepareStatement(createTableSQL)) {
            createStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }
    
    /**
     * This method deletes row from the questions table.
     * @param id to delete from the row
     */
    public void deleteQuestion(int id) throws SQLException {
        String deleteQuestionSQL = "DELETE FROM Questions WHERE id = ?;";
        
        try (PreparedStatement pstmt = connection.prepareStatement(deleteQuestionSQL)) {
            pstmt.setInt(1, id); 
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("Question with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No question found with ID " + id + ".");
            }
        }
    }

    /**
     * Inserts an admin into the Admin table.
     * @param email The email of the admin.
     * @param password The password of the admin.
     */
    public void insertAdmin(String email, String password) {
        String checkAdminSQL = "SELECT COUNT(*) FROM Admin WHERE email = ?";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkAdminSQL)) {
            checkStmt.setString(1, email);
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) == 0) {
                try (PreparedStatement insertStmt = connection.prepareStatement(insertAdminSQL)) {
                    insertStmt.setString(1, email);
                    insertStmt.setString(2, password);
                    insertStmt.executeUpdate();
                }
            } 
        } catch (SQLException e) {
            System.out.println("Error inserting admin: " + e.getMessage());
        }
    }

    
    /**
     * Inserts a question into the Questions table.
     * @param question The question text.
     * @param option1 The first option for the question.
     * @param option2 The second option for the question.
     * @param option3 The third option for the question.
     * @param correctAnswer The correct answer for the question.
     * @param difficulty The difficulty level of the question.
     */
    public void insertQuestion(String question, String option1, String option2, String option3, String correctAnswer, String difficulty) throws SQLException {
        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuestionSQL)) {
            insertStmt.setString(1, question);
            insertStmt.setString(2, option1);
            insertStmt.setString(3, option2);
            insertStmt.setString(4, option3);
            insertStmt.setString(5, correctAnswer);
            insertStmt.setString(6, difficulty);
            insertStmt.executeUpdate();
        } catch(SQLException e) {
        	System.out.println("Error inserting question" + e.getMessage());
        }
    }

    /**
     * Checks admin login credentials.
     * @param email The admin's email.
     * @param password The admin's password.
     * @return A login status message.
     */
    public String login(String email, String password) throws SQLException {
        String fetchDataSQL = "SELECT email FROM Admin WHERE email = ? AND password = ?";
        StringBuilder result = new StringBuilder();

        try (PreparedStatement searchStmt = connection.prepareStatement(fetchDataSQL)) {
            searchStmt.setString(1, email);
            searchStmt.setString(2, password);

            try (ResultSet resultSet = searchStmt.executeQuery()) {
                if (resultSet.next()) {
                    result.append("Logged In Successfully");
                } else {
                    result.append("Invalid credentials. Please try again.");
                }
            }
        }

        return result.toString();
    }
    
    /**
     * Retrieves all questions from the database.
     * @return A list of all questions.
     */
    public List<Question> getAllQuestions() throws SQLException {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            int id = rs.getInt("id");
            String question = rs.getString("question");
            String option1 = rs.getString("option1");
            String option2 = rs.getString("option2");
            String option3 = rs.getString("option3");
            String correctAnswer = rs.getString("correctAnswer");
            String difficulty = rs.getString("difficulty");

            questions.add(new Question(id, question, option1, option2, option3, correctAnswer, difficulty));
        }

        
        return questions;
    }
    
    /**
     * Retrieves questions filtered by difficulty from the database.
     * @param difficultyLevel The difficulty level of the questions.
     * @return A list of questions matching the difficulty level.
     */
    public List<Question> getQuestionsByDifficulty(String difficultyLevel) throws SQLException {
        String query = "SELECT * FROM Questions WHERE LOWER(difficulty) = LOWER(?)";
        List<Question> questions = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, difficultyLevel);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String question = rs.getString("question");
                    String option1 = rs.getString("option1");
                    String option2 = rs.getString("option2");
                    String option3 = rs.getString("option3");
                    String correctAnswer = rs.getString("correctAnswer");
                    String difficulty = rs.getString("difficulty");

                    // Create a new Question object and add it to the list
                    Question q = new Question(id, question, option1, option2, option3, correctAnswer, difficulty);
                    questions.add(q);
                }
            }
        }

        Collections.shuffle(questions);

        return questions;
    }

    /**
     * Updates an existing question in the database.
     * @param id The ID of the question to update.
     * @param question The new question text.
     * @param option1 The new first option.
     * @param option2 The new second option.
     * @param option3 The new third option.
     * @param correctAnswer The new correct answer.
     * @param difficulty The new difficulty level.
     */
    public void updateQuestion(int id, String question, String option1, String option2, String option3, String correctAnswer, String difficulty) throws SQLException {
    	try (PreparedStatement pstmt = connection.prepareStatement(updateQuestionSQL)) {
            pstmt.setString(1, question);
            pstmt.setString(2, option1);
            pstmt.setString(3, option2);
            pstmt.setString(4, option3);
            pstmt.setString(5, correctAnswer);
            pstmt.setString(6, difficulty);
            pstmt.setInt(7, id);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
            	System.out.println("Question updated successfully!");
            } else {
            	System.out.println("No question found with ID " + id);
            }
        }
    	
    }
    
    /**
     * This method checks if a player with the specified name and level exists in the database.
     * @param name  The name of the player.
     * @param level The level of the player.
     * @return      True if the player exists, false otherwise.
     */
    public boolean doesPlayerExist(String name, String level, String country) {
        String checkPlayerSQL = "SELECT 1 FROM Player WHERE Name = ? AND Level = ? AND LOWER(country) = LOWER(?) LIMIT 1";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkPlayerSQL)) {
            checkStmt.setString(1, name);
            checkStmt.setString(2, level);
            checkStmt.setString(3, country.toLowerCase());

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking player existence: " + e.getMessage());
        }

        return false;
    }

    /**
     * This method checks if there are any empty scores for the specified player with the given name and level in the database.
     * @param name  The name of the player.
     * @param level The level of the player.
     * @return      The index of the first empty score slot.
     */
    public int checkEmptyScores(String name, String level, String country) {
        String checkScoresSQL = "SELECT Score1, Score2, Score3, Score4, Score5 FROM Player WHERE Name = ? AND Level = ? AND LOWER(country) = LOWER(?)";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkScoresSQL)) {
            checkStmt.setString(1, name);
            checkStmt.setString(2, level);
            checkStmt.setString(3, country.toLowerCase());

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    for (int i = 1; i <= 5; i++) {
                        if (rs.getObject("Score" + i) == null) {
                            return i; 
                        }
                    }
                    return 0;
                } else {
                    System.out.println("Player not found.");
                    return -1;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking scores: " + e.getMessage());
            return -1; 
        }
    }
    
    /**
     * This method stores the score of a player for a specific round in the database.
     * @param userName The name of the player.
     * @param level    The level of the player.
     * @param score    The score of the player for the round.
     * @param round    The round number.
     */
    public void storePlayerRoundScore(String userName, String level, String country, int score, int round) {
    	System.out.println("Storing Score - User: " + userName + ", Level: " + level + ", Country: " + country + ", Score: " + score + ", Round: " + round);

        System.out.println("This is at round => " + round);

        String insertQuery = "INSERT INTO Player (Name, Level, country, Score1) VALUES (?, ?, ?,?)";
        String updateQuery = "UPDATE Player SET Score" + round + " = ? WHERE Name = ? AND Level = ? AND LOWER(country) = LOWER(?)";

        try (PreparedStatement stmt = connection.prepareStatement(round == 1 ? insertQuery : updateQuery)) {
            if (round == 1) {
                stmt.setString(1, userName);
                stmt.setString(2, level);
                stmt.setString(3,country.toLowerCase());
                stmt.setInt(4, score);
            } else {
                stmt.setInt(1, score);
                stmt.setString(2, userName);
                stmt.setString(3, level);
                stmt.setString(4,country.toLowerCase());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method retrieves the round scores of a player from the Player table in the database.
     * @param userName The name of the player.
     * @param level    The level of the player.
     * @return         A string containing the formatted round scores and total score.
     */
    public String getFormattedRoundScores(String userName, String level, String country) {
        String query = "SELECT Score1, Score2, Score3, Score4, Score5 FROM Player WHERE Name = ? AND Level = ? AND LOWER(country) = LOWER(?)";
        StringBuilder scoreDetails = new StringBuilder();
        int totalScore = 0;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userName);
            stmt.setString(2, level);
            stmt.setString(3,country.toLowerCase());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    for (int i = 1; i <= 5; i++) {
                        int roundScore = rs.getInt("Score" + i);
                        scoreDetails.append("Round ").append(i).append(" score: ").append(roundScore).append("\n");
                        totalScore += roundScore;
                    }
                    scoreDetails.append("\nTotal Score: ").append(totalScore);
                } else {
                    scoreDetails.append("No scores found for ").append(userName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scoreDetails.toString();
    }

    /**
     * This method retrieves the scores of players filtered by the specified difficulty level from the Player table in the database.
     * @param difficulty The difficulty level to filter the players by.
     * @return           A list of PlayerScore objects containing the scores of the players.
     */
    public List<CompetitorList> fetchPlayerScores(String difficulty) {
    List<CompetitorList> playerScores = new ArrayList<>();
    try {
        Statement stmt = connection.createStatement();
        String query = "SELECT CompetitorID, name, level, country, score1, score2, score3, score4, score5 " +
                       "FROM player WHERE level = '" + difficulty + "'";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            int competitorID = rs.getInt("CompetitorID");
            String name = rs.getString("name");
            String level = rs.getString("level");
            String country = rs.getString("country");
            int score1 = rs.getInt("score1");
            int score2 = rs.getInt("score2");
            int score3 = rs.getInt("score3");
            int score4 = rs.getInt("score4");
            int score5 = rs.getInt("score5");

            CompetitorList player = new CompetitorList(competitorID, name, level,country, score1, score2, score3, score4, score5);
            playerScores.add(player);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return playerScores;
}
    /**
     * Closes the database connection.
     * @throws SQLException If closing the connection fails.
     */
    public void closeDatabase() throws SQLException {
    	connection.close();
    }
    
public List<CompetitorList> fetchAllPlayerScores() {
    List<CompetitorList> playerScores = new ArrayList<>();
    try {
        Statement stmt = connection.createStatement();
        String query = "SELECT CompetitorID, name, level, country, score1, score2, score3, score4, score5 " +
                       "FROM player";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            int competitorID = rs.getInt("CompetitorID");
            String name = rs.getString("name");
            String level = rs.getString("level");
            String country = rs.getString("country");
            int score1 = rs.getInt("score1");
            int score2 = rs.getInt("score2");
            int score3 = rs.getInt("score3");
            int score4 = rs.getInt("score4");
            int score5 = rs.getInt("score5");

            CompetitorList player = new CompetitorList(competitorID, name, level,country, score1, score2, score3, score4, score5);
            playerScores.add(player);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return playerScores;
}
}
