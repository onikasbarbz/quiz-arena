-- Quiz Arena Database Schema
-- Run this in MySQL before starting the app

CREATE DATABASE IF NOT EXISTS quiz;
USE quiz;

CREATE TABLE IF NOT EXISTS Admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    Email VARCHAR(100) NOT NULL,
    Password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question VARCHAR(255) NOT NULL,
    option1 VARCHAR(100) NOT NULL,
    option2 VARCHAR(100) NOT NULL,
    option3 VARCHAR(100) NOT NULL,
    correctAnswer VARCHAR(100) NOT NULL,
    difficulty VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Player (
    CompetitorID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Level VARCHAR(50) NOT NULL,
    Country VARCHAR(50) NOT NULL,
    Score1 INT,
    Score2 INT,
    Score3 INT,
    Score4 INT,
    Score5 INT
);
