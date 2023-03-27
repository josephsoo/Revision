package ui.guitools;

import model.Deck;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Reviewer {

    private Deck deck;

    public Reviewer(Deck deck) {

        this.deck = deck;
        // Create the frame
        JFrame frame = new JFrame("Reviewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the panel
        JPanel panel = new JPanel(new BorderLayout());

        // Create the label with the question
        JLabel questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(questionLabel, BorderLayout.CENTER);

        // Create the button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Create the "Answer1"
        JButton answer1 = new JButton();
        buttonPanel.add(answer1);

        // Create the "Answer2" button
        JButton answer2 = new JButton();
        buttonPanel.add(answer2);

        // Create the "Answer3" button
        JButton answer3 = new JButton();
        buttonPanel.add(answer3);

        // Create the "Answer4" button
        JButton answer4 = new JButton();
        buttonPanel.add(answer4);

        // Add the button panel to the main panel
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the panel to the frame
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

        updateButtons();
    }

    private void updateButtons() {
        List<String> answers = deck.getAnswers();
    }
}
