package com.quizarena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

class DatabaseConnectorTest {

    private static DBConnector dbConnector;
    /**
     * This method runs once before all the tests to set up the database.
     * It creates tables and inserts some initial data.
     */
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        dbConnector = new DBConnector();
        // Create the Admin table and insert an admin user
        dbConnector.createTable(dbConnector.createAdminTableSQL);
        dbConnector.insertAdmin("admin@admin.com", "admin");
        // Create the Player table and insert a test player score
        dbConnector.createTable(dbConnector.createTablePlayerSQL);
        dbConnector.storePlayerRoundScore("Test", "Intermediate","Nepal" ,1,1);
    }
    /**
     * This method runs after all tests to clean up and close the database.
     */
    @AfterAll
    static void tearDownAfterClass() throws Exception {
    	// Close the database connection after tests are done
    	dbConnector.closeDatabase();
    }
    /**
     * Tests if the login is successful with correct credentials.
     * It expects a success message when valid admin credentials are used.
     */
    @Test
    void testLogin_SuccessfulLogin() {
        try {
            String result = dbConnector.login("admin@admin.com", "admin");
            assertEquals("Logged In Successfully", result, "Login should be successful with correct credentials.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Tests if the login fails with invalid credentials.
     * It expects an error message when invalid login credentials are provided.
     */
    @Test
    void testLogin_InvalidCredentials() {
        try {
            String result = dbConnector.login("admin@admin.com", "wrongpassword");
            assertEquals("Invalid credentials. Please try again.", result, "Login should fail with incorrect credentials.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Tests if a player exists in the database based on the provided details.
     * It checks if the player "Test" from "Nepal" in "Intermediate" exists.
     */
    @Test
    void testDoesPlayerExist_PlayerExists() {
        boolean exists = dbConnector.doesPlayerExist("Test", "Intermediate", "Nepal");
		assertTrue(exists, "Player should exist.");
    }
    /**
     * Tests if a player does not exist in the database based on the provided details.
     * It checks if the player "Test" from "Nepal" in "Beginner" does not exist.
     */
    @Test
    void testDoesPlayerExist_PlayerDoesNotExist() {
        boolean exists = dbConnector.doesPlayerExist("Test", "Beginner", "Nepal");
		assertFalse(exists, "Player should not exist.");
	}
}
