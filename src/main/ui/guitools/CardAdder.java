package ui.guitools;

import model.Card;
import model.Deck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a popup to add cards
public class CardAdder extends JDialog implements ActionListener {
    private JTextField question;
    private JTextField answer;
    private JButton addCard;
    private Deck deck;

    // MODIFIES: deck
    // REQUIRES: parent is the main JFrame
    // EFFECTS: Constructs a popup for adding cards
    CardAdder(Deck deck, JFrame parent) {
        super(parent, true);
        this.deck = deck;


        // Create the text fields
        question = new JTextField();
        answer = new JTextField();

        // Create the buttons
        addCard = new JButton("Add Card");
        addCard.addActionListener(this);

        // Add the components to the dialog
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Question"));
        panel.add(question);
        panel.add(new JLabel("Answer"));
        panel.add(answer);
        panel.add(addCard);
        add(panel);

        // Set the dialog properties
        setTitle("Adding cards to " + deck.getDeckName());
        pack();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);


    }

    // MODIFIES: this
    // EFFECTS: listens to the add card button and guards against empty question and answers. Produces a warning if
    // question or answer is empty
    @Override
    public void actionPerformed(ActionEvent e) {
        String question = this.question.getText();
        String answer = this.answer.getText();
        if (question.isEmpty() || answer.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Question or Answer cannot be blank");
        } else {
            deck.addCard(new Card(question, answer));
            this.question.setText("");
            this.answer.setText("");
        }
    }
}
