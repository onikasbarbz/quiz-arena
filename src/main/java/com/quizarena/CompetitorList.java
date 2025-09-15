package com.quizarena;
/**
 * Represents a competitor with ID, name, level, country, and scores.
 */
public class CompetitorList implements CompetitorListInterface{
    private int competitorId;
    private String name;
    private String level;
    private String country;
    private Integer score1;
    private Integer score2;
    private Integer score3;
    private Integer score4;
    private Integer score5;

    /**
     * Initializes a competitor with an ID, name, level, country, and scores for five rounds.
     *
     * @param competitorId Unique identifier for the competitor.
     * @param name Name of the competitor.
     * @param level Skill level of the competitor.
     * @param country Country of the competitor.
     * @param score1 Score achieved in round one.
     * @param score2 Score achieved in round two.
     * @param score3 Score achieved in round three.
     * @param score4 Score achieved in round four.
     * @param score5 Score achieved in round five.
     */
    public CompetitorList(Integer competitorId, String name, String level,String country, Integer score1, Integer score2, Integer score3, Integer score4, Integer score5) {
        this.competitorId = competitorId;
        this.name = name;
        this.level = level;
        this.country = country;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.score4 = score4;
        this.score5 = score5;
    }

    /**
     * Gets the ID of the competitor.
     *
     * @return The ID of the competitor.
     */
    public Integer getCompetitorId() {
        return competitorId;
    }

    /**
     * Gets the name of the competitor.
     *
     * @return The name of the competitor.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the country of the competitor.
     *
     * @return The country name of the competitor.
     */
    public String getCountry() {
        return country;
    }
    
    /**
     * Gets the level of the competitor.
     *
     * @return The level of the competitor.
     */
    public String getLevel() {
        return level;
    }

    /**
     * Gets the score of the competitor in the first round.
     *
     * @return The score of the competitor in the first round.
     */
    public Integer getScore1() {
        return score1;
    }

    /**
     * Gets the score of the competitor in the second round.
     *
     * @return The score of the competitor in the second round.
     */
    public Integer getScore2() {
        return score2;
    }

    /**
     * Gets the score of the competitor in the third round.
     *
     * @return The score of the competitor in the third round.
     */
    public Integer getScore3() {
        return score3;
    }

    /**
     * Gets the score of the competitor in the fourth round.
     *
     * @return The score of the competitor in the fourth round.
     */
    public Integer getScore4() {
        return score4;
    }

    /**
     * Gets the score of the competitor in the fifth round.
     *
     * @return The score of the competitor in the fifth round.
     */
    public Integer getScore5() {
        return score5;
    }

    /**
     * Gets the total score of the competitor.
     *
     * @return The total score of the competitor.
     */
    public int getTotalScore() {
        int total = 0;
        total += (score1 != null) ? score1 : 0;
        total += (score2 != null) ? score2 : 0;
        total += (score3 != null) ? score3 : 0;
        total += (score4 != null) ? score4 : 0;
        total += (score5 != null) ? score5 : 0;
        return total;
    }

    /**
     * Returns a string representation of the PlayerScore object.
     *
     * @return A string representation of the PlayerScore object.
     */
    @Override
    public String toString() {
        return "Player: " + name + ", Level: " + level + ", Scores: [" + 
               (score1 != null ? score1 : "N/A") + ", " +
               (score2 != null ? score2 : "N/A") + ", " +
               (score3 != null ? score3 : "N/A") + ", " +
               (score4 != null ? score4 : "N/A") + ", " +
               (score5 != null ? score5 : "N/A") + 
               "], Total Score: " + getTotalScore();
    }
}
