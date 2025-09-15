package com.quizarena;

import java.util.List;
import java.util.Scanner;

public class CompetitorManagementSystem {
    private static DBConnector dbConnector = new DBConnector();
    static List<CompetitorList> playerScores = dbConnector.fetchAllPlayerScores();
    static List<CompetitorList> beginnerScores = dbConnector.fetchPlayerScores("beginner");
    static List<CompetitorList> intermediateScores = dbConnector.fetchPlayerScores("intermediate");
    static List<CompetitorList> advancedScores = dbConnector.fetchPlayerScores("advanced");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int choice = 0;

            while (choice != 5) {
                System.out.println("Competitor Management System");
                System.out.println("1. Generate Full Report");
                System.out.println("2. Display Top Performer");
                System.out.println("3. Generate Statistics");
                System.out.println("4. Search Competitor by ID");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        generateFullReport(playerScores);
                        break;
                    case 2:
                        displayTopPerformer(playerScores);
                        break;
                    case 3:
                        generateStatistics(playerScores);
                        break;
                    case 4:
                        searchCompetitorByID(scanner, playerScores);
                        break;
                    case 5:
                        System.out.println("Exiting");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                        break;
                }
            }
        }
    }

    /**
     * Generates a full report of all competitors.
     * @param playerScores List of all player scores
     */
    private static void generateFullReport(List<CompetitorList> playerScores) {
        int rankConsole = 1;

        System.out.println("Competitor Id | Rank | Name             | Level           | Score 1 | Score 2 | Score 3 | Score 4 | Total Score");
        System.out.println("-----------------------------------------------------------------------------------------------");

        for (CompetitorList player : playerScores) {
            System.out.println(String.format("%13s | %4d | %-16s | %-15s | %7s | %7s | %7s | %7s | %11d", 
            player.getCompetitorId(), rankConsole++, player.getName(), player.getLevel(),
            player.getScore1() != null ? player.getScore1() : "N/A", 
            player.getScore2() != null ? player.getScore2() : "N/A", 
            player.getScore3() != null ? player.getScore3() : "N/A", 
            player.getScore4() != null ? player.getScore4() : "N/A", 
            player.getTotalScore()));
        }
        
        System.out.println("-----------------------------------------------------------------------------------------------");
    }

    /**
     * Displays the top performer among the competitors.
     * @param playerScores List of all player scores
     */
    private static void displayTopPerformer(List<CompetitorList> playerScores) {
        CompetitorList topPerformer = null;
        int maxScore = Integer.MIN_VALUE;
        System.out.println("-----------------------------------------------------------------------------------------------");

        for (CompetitorList player : playerScores) {
            if (player.getTotalScore() > maxScore) {
                maxScore = player.getTotalScore();
                topPerformer = player;
            }
        }

        if (topPerformer != null) {
            System.out.println("Top Performer: " + topPerformer.getName() + " with total score of " + maxScore);
        } else {
            System.out.println("No performers found.");
        }
        System.out.println("-----------------------------------------------------------------------------------------------");
    }

    /**
     * Generates statistics about the competitors.
     * @param playerScores List of all player scores
     */
    private static void generateStatistics(List<CompetitorList> playerScores) {
    	 int totalParticipants = beginnerScores.size() + intermediateScores.size() + advancedScores.size();

         CompetitorList topBeginner = beginnerScores.isEmpty() ? null : beginnerScores.get(0);
         CompetitorList topIntermediate = intermediateScores.isEmpty() ? null : intermediateScores.get(0);
         CompetitorList topAdvanced = advancedScores.isEmpty() ? null : advancedScores.get(0);

         System.out.println("-----------------------------------------------------------------------------------------------");
         
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
         
         System.out.println("-----------------------------------------------------------------------------------------------");
     }
    
    /**
     * Searches for a competitor by their ID.
     *
     * @param scanner      The Scanner object for input
     * @param playerScores List of all player scores
     */
    private static void searchCompetitorByID(Scanner scanner, List<CompetitorList> playerScores) {
        System.out.print("Enter Competitor ID: ");
        int id = scanner.nextInt();
        boolean found = false;
        System.out.println("-----------------------------------------------------------------------------------------------");
        
        for (CompetitorList player : playerScores) {
            if (player.getCompetitorId() == id) {
                System.out.println("Competitor Found: " + player.getName());
                System.out.println("Level: " + player.getLevel());
                System.out.println("Score 1: " + (player.getScore1() != null ? player.getScore1() : "N/A"));
                System.out.println("Score 2: " + (player.getScore2() != null ? player.getScore2() : "N/A"));
                System.out.println("Score 3: " + (player.getScore3() != null ? player.getScore3() : "N/A"));
                System.out.println("Score 4: " + (player.getScore4() != null ? player.getScore4() : "N/A"));
                System.out.println("Total Score: " + player.getTotalScore());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Competitor with ID " + id + " not found.");
        }
        
        System.out.println("-----------------------------------------------------------------------------------------------");
    }
}