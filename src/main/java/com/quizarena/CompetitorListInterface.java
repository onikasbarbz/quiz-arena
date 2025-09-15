package com.quizarena;

/**
 * The CompetitorListInterface defines the methods required for a competitor's information.
 * This includes the competitor's ID, name, level, country, individual scores, and total score.
 */
public interface CompetitorListInterface {

    /**
     * Gets the competitor's unique ID.
     *
     * @return the competitor's ID as an Integer
     */
    Integer getCompetitorId();

    /**
     * Gets the name of the competitor.
     *
     * @return the competitor's name as a String
     */
    String getName();

    /**
     * Gets the level of the competitor.
     *
     * @return the competitor's level as a String
     */
    String getLevel();

    /**
     * Gets the country of the competitor.
     *
     * @return the competitor's country as a String
     */
    String getCountry();

    /**
     * Gets the first score of the competitor.
     *
     * @return the first score as an Integer
     */
    Integer getScore1();

    /**
     * Gets the second score of the competitor.
     *
     * @return the second score as an Integer
     */
    Integer getScore2();

    /**
     * Gets the third score of the competitor.
     *
     * @return the third score as an Integer
     */
    Integer getScore3();

    /**
     * Gets the fourth score of the competitor.
     *
     * @return the fourth score as an Integer
     */
    Integer getScore4();

    /**
     * Gets the fifth score of the competitor.
     *
     * @return the fifth score as an Integer
     */
    Integer getScore5();

    /**
     * Calculates the total score of the competitor by summing up all individual scores.
     *
     * @return the total score as an integer
     */
    int getTotalScore();

    /**
     * Provides a string representation of the competitor's details.
     *
     * @return a String representing the competitor's information
     */
    String toString();
}
