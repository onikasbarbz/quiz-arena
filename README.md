# Quiz Arena 

A desktop quiz game I built to get more comfortable with Java Swing and database-driven applications. Players compete across multiple rounds, earn scores, and can see how they rank against others on a leaderboard. There's also a separate admin side where you can manage the question bank.

It's nothing fancy on the outside — just Java Swing — but under the hood it's got a full MySQL backend, role-based access (player vs admin), multi-round scoring logic, and a console-based reporting tool.

---

## What it does

**As a player:**
- Enter your name, country, and pick a difficulty — Beginner, Intermediate, or Advanced
- Play through 5 rounds, 5 questions each, all pulled randomly from the database
- Your score for each round gets saved as you go, so you can close and come back
- Check your score breakdown or see where you stand on the leaderboard

**As an admin:**
- Log in through the admin portal (default: `admin@admin.com` / `admin`)
- Add, edit, or delete quiz questions
- View the leaderboard across all difficulty levels
- See stats like total participants and top scorer per category

**From the terminal (CompetitorManagementSystem):**
- Generate a full competitor report
- Find the top performer
- Get statistics broken down by difficulty
- Search for any competitor by their ID

---

## Tech stack

- **Java 17** — core language
- **Java Swing** — desktop GUI
- **MySQL** — stores questions, players, scores, and admin credentials
- **JDBC** — database connectivity
- **Interface-driven design** — `DBInterface`, `CompetitorListInterface`

---

## Project structure

```
quiz-arena/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── quizarena/
│                   ├── Main.java                      # Entry point, landing screen
│                   ├── CompetitorLogin.java           # Player login screen
│                   ├── Homepage.java                  # Player dashboard
│                   ├── Game.java                      # Core game logic + UI
│                   ├── CompetitorScoreDetail.java     # Player score breakdown
│                   ├── CompetitorLeaderBoard.java     # Player-facing leaderboard
│                   ├── CompetitorList.java            # Competitor data model
│                   ├── CompetitorListInterface.java   # Competitor contract
│                   ├── CompetitorManagementSystem.java# Console reporting tool
│                   ├── AdminLogin.java                # Admin login screen
│                   ├── AdminDashboard.java            # Admin question manager
│                   ├── AdminLeaderBoard.java          # Admin leaderboard view
│                   ├── Question.java                  # Question data model
│                   ├── DBConnector.java               # All database operations
│                   ├── DBInterface.java               # Database contract
│                   └── DatabaseConnectorTest.java     # DB connection test
├── database/
│   └── schema.sql                                     # Database setup script
├── .gitignore
└── README.md
```

---

## Running it locally

You'll need **Java 17+**, **MySQL**, and the **MySQL JDBC driver** on your classpath.

### 1. Clone the repo

```bash
git clone https://github.com/yourusername/quiz-arena.git
cd quiz-arena
```

### 2. Set up the database

Open MySQL and run the schema file:

```sql
source database/schema.sql;
```

This creates a database called `quiz` with the three tables the app needs — `Admin`, `Questions`, and `Player`.

By default the app connects to:
- Host: `localhost`
- Database: `quiz`
- User: `root`
- Password: *(empty)*

If your MySQL setup is different, update these four lines in `DBConnector.java`:

```java
static final String DB_URL = "jdbc:mysql://localhost/quiz";
static final String USERNAME = "root";
static final String PASSWORD = "";
```

### 3. Add the JDBC driver

Download the [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) jar and add it to your classpath when compiling and running.

### 4. Compile

From the project root:

```bash
javac -cp ".:/path/to/mysql-connector.jar" -d out src/main/java/com/quizarena/*.java
```

### 5. Run the GUI app

```bash
java -cp "out:/path/to/mysql-connector.jar" com.quizarena.Main
```

### 6. Run the console reporting tool (optional)

```bash
java -cp "out:/path/to/mysql-connector.jar" com.quizarena.CompetitorManagementSystem
```

### Default admin credentials

```
Email:    admin@admin.com
Password: admin
```

These are seeded automatically on first run.

---

## Adding questions

Log in as admin and use the dashboard to add questions — each one has a question body, three options, the correct answer, and a difficulty tag (beginner / intermediate / advanced). Questions are served randomly to players at their chosen difficulty.

---

## Notes

- A player can play a maximum of 5 rounds per profile (name + country + difficulty combo)
- Scores are stored round by round, so partial progress is saved
- The leaderboard ranks players by total score within each difficulty tier
