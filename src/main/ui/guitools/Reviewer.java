package ui.guitools;

import model.Deck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// represents a reviewer used to review a deck
public class Reviewer implements ActionListener {

    private Deck deck;
    JButton answer1;
    JButton answer2;
    JButton answer3;
    JButton answer4;
    JLabel questionLabel;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    Boolean firstTry;
    List<String> answers;

    // Modifies: deck
    // EFFECTS: constructs a reviewer that lets the user review the deck
    public Reviewer(Deck deck) {

        this.deck = deck;
        // Create the frame
        JFrame frame = new JFrame("Reviewer");

        // Create the panel
        JPanel panel = new JPanel(new BorderLayout());

        // Create the label with the question
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(questionLabel, BorderLayout.CENTER);

        // Create the button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Create the "Answer1"
        answer1 = new JButton();
        buttonPanel.add(answer1);


        // Create the "Answer2" button
        answer2 = new JButton();
        buttonPanel.add(answer2);

        // Create the "Answer3" button
        answer3 = new JButton();
        buttonPanel.add(answer3);

        // Create the "Answer4" button
        answer4 = new JButton();
        buttonPanel.add(answer4);

        setupButtons();



        // Add the button panel to the main panel
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the panel to the frame
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(WIDTH, HEIGHT);

        updateButtons();

    }

    // MODIFIES: this
    // EFFECTS: Sets up the button's action commands and action listeners
    private void setupButtons() {
        answer1.addActionListener(this);
        answer2.addActionListener(this);
        answer3.addActionListener(this);
        answer4.addActionListener(this);
        answer1.setActionCommand("0");
        answer2.setActionCommand("1");
        answer3.setActionCommand("2");
        answer4.setActionCommand("3");
    }

    // MODIFIES: this
    // EFFECTS: updates the label and buttons to match the current question
    private void updateButtons() {
        answers = deck.getAnswers();
        answer1.setText(answers.get(0));
        answer2.setText(answers.get(1));
        answer3.setText(answers.get(2));
        answer4.setText(answers.get(3));
        questionLabel.setText(deck.getQuestion());
        firstTry = true;
    }

    // MODIFIES: this
    // EFFECTS: listens to the buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if (deck.isCorrectAnswer(answers.get(Integer.parseInt(e.getActionCommand())))) {
            deck.rescheduleDeck(firstTry);
            updateButtons();
        } else {
            firstTry = false;
        }
    }
}

