package ui.guitools;

import model.Deck;
import ui.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

// A popup to modify deck
public class ModifyPopUp {

    // MODIFIES: selectedDeck, parent
    // EFFECTS: Constructs a modifypopup to let the user modify the selected deck
    public ModifyPopUp(Deck selectedDeck, JFrame parent) {
        // Create an array of options for the user to choose from
        String[] options = {"Cancel", "Delete Deck", "Add Cards", "Rename Deck"};


        // Display the dialog box and get the user's choice
        int choice = JOptionPane.showOptionDialog(null, "Choose an option:", "Modify deck",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        // Handle the user's choice
        switch (choice) {
            case 3:
                String deckName = JOptionPane.showInputDialog("Enter the new deck name");
                if (!(deckName == null) && !(deckName == "")) {
                    selectedDeck.renameDeck(deckName);
                }
                break;
            case 2:
                new CardAdder(selectedDeck, parent);
                break;
            case 1:
                GUI.deleteDeck(selectedDeck);
                break;
            default:
        }
    }

}
