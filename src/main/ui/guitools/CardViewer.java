package ui.guitools;

import model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static ui.GUI.HEIGHT;
import static ui.GUI.WIDTH;


public class CardViewer {
    private JList<String> listModel;
    private DefaultListModel<String> model;
    private Deck selectedDeck;
    private JFrame frame;

    public CardViewer(Deck selectedDeck) {
        this.selectedDeck = selectedDeck;
        frame = new JFrame(selectedDeck.getDeckName());
        frame.setVisible(true);
        frame.add(makeNamesPanel());
        frame.setSize(WIDTH, HEIGHT);
    }

    public JScrollPane makeNamesPanel() {
        model = new DefaultListModel<>();
        listModel = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(listModel);
        for (String name : getCardData()) {
            model.addElement(name);
        }
        return scrollPane;

    }

    public List<String> getCardData() {
        List<String> cards = new ArrayList<>();
        for (Card card : selectedDeck.getAllCards()) {
            cards.add("Question: " + card.getQuestion() + " Answer:" + card.getAnswer());
        }
        return cards;
    }

}
