package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class interviewPreparationGUI extends JFrame{
    private JPanel mainPanel;
    private JButton nextQuestionButton;
    private JTextArea questionTextArea;
    private JTextArea answerTextArea;

    public interviewPreparationGUI(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        nextQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnector database = null;
                try {
                    // instantiate object
                    database = new DatabaseConnector(
                            "jdbc:mysql://localhost:3306/flashcards",
                            "root",
                            "mysql");
                    // connect to database
                    database.connect();
                    // query database
                    Question question = database.getQuestion();
                    // set GUI text
                    questionTextArea.setText(question.getQuestion());
                    answerTextArea.setText(question.getAnswer());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } finally {
                    database.close();
                }
            }
        });
    }

    public static void main (String[] args) {
        JFrame frame = new interviewPreparationGUI("Java interview preparation");
        frame.setVisible(true);
    }

    String test = """
            JDK stands for Java Development Kit. It contains the tools and
            libraries for development of Java programs. It also contains
            compilers and debuggers needed to compile Java program,
            JRE stands for Java Runtime Environment. This is included in JDK.
            JRE provides libraries and JVM that is required to run a Java
            program.
            """;
}
