package com.quizarena;
/**
 * Represents a multiple-choice question with options and difficulty level.
 */
public class Question {
    private int id;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String correctAnswer;
    private String difficulty;
    /**
     * Creates a Question object.
     *
     * @param id Unique ID of the question
     * @param question The question text
     * @param option1 First answer option
     * @param option2 Second answer option
     * @param option3 Third answer option
     * @param correctAnswer The correct answer
     * @param difficulty Difficulty level of the question
     */
    public Question(int id, String question, String option1, String option2, String option3, String correctAnswer, String difficulty) {
        this.id = id;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getDifficulty() {
        return difficulty;
    }
    /**
     * Returns a string representation of the question.
     *
     * @return Question details as a formatted string
     */
    @Override
    public String toString() {
        return "ID: " + id + ", Question: " + question + ", Option 1: " + option1 + ", Option 2: " + option2 + ", Option 3: " + option3 + ", Correct Answer: " + correctAnswer + ", Difficulty: " + difficulty;
    }
}
