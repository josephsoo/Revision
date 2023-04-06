package ui.guitools;

import model.Deck;
import ui.GUI;

import javax.swing.*;

// A popup to modify deck
public class ProcessPopUp {

    // MODIFIES: selectedDeck, parent
    // EFFECTS: Constructs a processpopup to let the user modify the selected deck
    public ProcessPopUp(Deck selectedDeck, JFrame parent) {
        // Create an array of options for the user to choose from
        String[] options = {"Cancel", "Delete Deck", "Add Cards", "Rename Deck", "View Cards"};


        // Display the dialog box and get the user's choice
        int choice = JOptionPane.showOptionDialog(null, "Choose an option:", "Modify deck",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        // Handle the user's choice
        switch (choice) {
            case 4:
                new CardViewer(selectedDeck);
                break;
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
