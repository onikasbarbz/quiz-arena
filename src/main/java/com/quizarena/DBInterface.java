package com.quizarena;

import java.sql.SQLException;
import java.util.List;

public interface DBInterface {
    void insertAdmin(String email, String password) throws SQLException;
    String login(String email, String password) throws SQLException;

    void insertQuestion(String question, String option1, String option2, String option3, String correctAnswer, String difficulty) throws SQLException;
    void deleteQuestion(int id) throws SQLException;
    void updateQuestion(int id, String question, String option1, String option2, String option3, String correctAnswer, String difficulty) throws SQLException;
    List<Question> getAllQuestions() throws SQLException;
    List<Question> getQuestionsByDifficulty(String difficultyLevel) throws SQLException;

    // Player-related methods
    boolean doesPlayerExist(String name, String level, String country);
    int checkEmptyScores(String name, String level, String country);
    void storePlayerRoundScore(String userName, String level, String country, int score, int round);
    String getFormattedRoundScores(String userName, String level, String country);
    List<CompetitorList> fetchPlayerScores(String difficulty);

    // General methods
    void createTable(String createTableSQL);
}
