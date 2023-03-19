package org.example;

import java.sql.*;

public class DatabaseConnector {

    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;
    Connection mysqlConnection = null; // Create the database connection

    public DatabaseConnector(String url, String user, String password) throws SQLException {
        this.URL = url;
        this.USERNAME = user;
        this.PASSWORD = password;
    }

    public void connect() {
        try {
            this.mysqlConnection = DriverManager.getConnection(this.URL, this.USERNAME, this.PASSWORD);
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect the database!", e);
        }
    }

    public void close() {
        if (mysqlConnection != null) {
            try {
                mysqlConnection.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    public Question getQuestion() throws SQLException {
        Question question = new Question();

        Statement stmt = mysqlConnection.createStatement();
        ResultSet queryResult = stmt.executeQuery("""
                        select question, answer from questions
                        order by rand()
                        limit 1
                        """);

        while (queryResult.next()) {
            String dbQuestion = queryResult.getString("question");
            String dbAnswer = queryResult.getString("answer");
            question.setQuestion(dbQuestion);
            question.setAnswer(dbAnswer);
        }

        return question;
    }
}
