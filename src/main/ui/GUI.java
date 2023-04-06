package ui;

// Spaced repetition flashcard application inspired by TellerApp https://github.students.cs.ubc.ca/CPSC210/TellerApp

import model.*;
import model.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writable;
import ui.guitools.ProcessPopUp;
import ui.guitools.Reviewer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Graphical user interface for Revision
public class GUI extends JFrame implements Writable, ActionListener, ListSelectionListener, WindowListener {
    private static final String JSON_STORE = "./data/workroom.json";
    private static ArrayList<Deck> decks = new ArrayList<Deck>();
    private static Deck currentDeck;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private JList<String> listModel;
    DefaultListModel<String> model;



    // EFFECTS: Runs the Revision application
    public GUI() {
        super("Revision");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        displaySplash();

    }

    // MODIFIES: this
    // EFFECTS: creates a splash screen which will disappear when clicked
    public void displaySplash() {
        JPanel splashPanel = new JPanel(new BorderLayout());
        ImageIcon splashIcon = new ImageIcon("data/app.jpg");
        JLabel splashLabel = new JLabel(splashIcon);
        splashPanel.add(splashLabel, BorderLayout.CENTER);

        JDialog splashDialog = new JDialog((Frame) null, false);
        splashDialog.setUndecorated(true);
        splashDialog.add(splashPanel);
        splashDialog.pack();
        splashDialog.setLocationRelativeTo(null);
        splashDialog.setVisible(true);

        splashDialog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                splashDialog.dispose();
                runRevision();
            }
        });
    }



    // MODIFIES: this
    // EFFECTS: processes user input. Inspired by TellerApp https://github.students.cs.ubc.ca/CPSC210/TellerApp
    //          class: TellerApp, method: runTellerApp
    public void runRevision() {
        initializeScanner();
        initializeGraphics();

    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this DrawingEditor will operate, and populates the tools to be used
    //           to manipulate this drawing
    private void initializeGraphics() {
        setLayout(new BorderLayout());

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(this);
        setVisible(true);
        add(makeButtonPanel(), BorderLayout.SOUTH);
        add(makeNamesPanel(), BorderLayout.NORTH);
    }


    // Effects: constructs a JScrollPane, which will be used to show the deck names
    public JScrollPane makeNamesPanel() {
        model = new DefaultListModel<>();
        listModel = new JList<>(model);
        listModel.addListSelectionListener(this);
        JScrollPane scrollPane = new JScrollPane(listModel);
        return scrollPane;

    }

    // EFFECTS: returns a JPanel of the buttons on the bottom
    public JPanel makeButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loadDecks = new JButton("Load Decks");
        loadDecks.setActionCommand("loadDecks");
        loadDecks.addActionListener(this);
        JButton saveDecks = new JButton("Save Decks");
        saveDecks.setActionCommand("saveDecks");
        saveDecks.addActionListener(this);
        JButton modifyDeck = new JButton("Process Deck");
        modifyDeck.setActionCommand("processDeck");
        modifyDeck.addActionListener(this);
        JButton addDeck = new JButton("Add Deck");
        addDeck.setActionCommand("addDeck");
        addDeck.addActionListener(this);
        JButton reviewer = new JButton("Reviewer");
        reviewer.setActionCommand("reviewer");
        reviewer.addActionListener(this);
        buttonPanel.add(loadDecks);
        buttonPanel.add(saveDecks);
        buttonPanel.add(modifyDeck);
        buttonPanel.add(addDeck);
        buttonPanel.add(reviewer);
        return buttonPanel;
    }


    // MODIFIES: this
    // EFFECTS: initializes the system
    private void initializeScanner() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


    // EFFECTS: Returns the name of all the decks
    private ArrayList<String> getAllDeckNames() {
        ArrayList<String> allDeckNames = new ArrayList<>();
        for (Deck deck : decks) {
            allDeckNames.add(deck.getDeckName());
        }
        return allDeckNames;
    }


    // MODIFIES: this
    // EFFECTS: deletes the deck deck
    // REQUIRES: deck is in decks
    public static void deleteDeck(Deck deck) {
        decks.remove(deck);
        currentDeck = null;

    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray decksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Deck d : decks) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }


    // EFFECTS: makes a json object with the decks
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("decks", decksToJson());
        return json;
    }

    // EFFECTS: saves the workroom to file
    private void saveDecks() {
        try {
            jsonWriter.open();
            jsonWriter.write(decks);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved decks to " + JSON_STORE);

        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
            JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE);

        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadDecks() {
        try {
            decks = jsonReader.read();
            JOptionPane.showMessageDialog(null, "Loaded decks from " + JSON_STORE);
            updateListModel();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE);

        }
    }

    // Modifies: this
    // Effects: updates model to fit all the current deck names
    private void updateListModel() {
        model.clear();
        for (String name : getAllDeckNames()) {
            model.addElement(name);
        }
    }

    // MODIFIES: this
    // EFFECTS: constructs a popup that allows the user to add a deck
    private void addDeck() {
        String deckName = JOptionPane.showInputDialog("Enter the deck name");
        if (deckName != null && !deckName.isEmpty() && !getAllDeckNames().contains(deckName)) {
            model.addElement(deckName);
            decks.add(new Deck(deckName));
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Deck Name");

        }
    }

    // MODIFIES: this
    // EFFECTS: Makes a modify deck popup if there is a deck selected
    private void processDeck() {
        if (currentDeck == null) {
            JOptionPane.showMessageDialog(null, "No Deck Selected!");
        } else {
            new ProcessPopUp(currentDeck, this);
            updateListModel();
        }
    }

    // MODIFIES: this
    // EFFECTS: listens to the buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "loadDecks":
                loadDecks();
                break;
            case "saveDecks":
                saveDecks();
                break;
            case "processDeck":
                processDeck();
                break;
            case "addDeck":
                addDeck();
                break;
            default:
                reviewDeck();
        }
    }

    // MODIFIES: this
    // EFFECTS: constructs a reviewer popup if there is a deck selected, and it has enough cards, otherwise produces a
    // warning
    private void reviewDeck() {
        if (currentDeck == null) {
            JOptionPane.showMessageDialog(null, "No Deck Selected!");
        } else {
            if (currentDeck.getAnswers().size() < 4) {
                JOptionPane.showMessageDialog(null, "Not enough cards! (Requires at least 4 "
                        + "cards)");
            } else {
                new Reviewer(currentDeck);
            }
        }
    }

    // REQUIRES: deckName is a valid name of a deck in decks
    // EFFECTS: returns the Deck that matches the name
    public Deck getDeck(String deckName) {
        List<String> names = getAllDeckNames();
        for (int i = 0; i < names.size(); i++) {
            if (decks.get(i).getDeckName() == deckName) {
                return decks.get(i);
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: sets the currentDeck to the deck selected in the names panel
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            if (!(listModel.getSelectedIndex() == -1)) {
                String selected = listModel.getSelectedValue();
                currentDeck = getDeck(selected);
            }
        }
    }

    // EFFECTS: does nothing
    @Override
    public void windowOpened(WindowEvent e) {

    }

    // EFFECTS: prints out all the events
    @Override
    public void windowClosing(WindowEvent e) {
        for (Event v : EventLog.getInstance()) {
            System.out.println(v.toString());
        }
    }

    // EFFECTS: does nothing
    @Override
    public void windowClosed(WindowEvent e) {

    }

    // EFFECTS: does nothing
    @Override
    public void windowIconified(WindowEvent e) {

    }

    // EFFECTS: does nothing
    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    // EFFECTS: does nothing
    @Override
    public void windowActivated(WindowEvent e) {

    }

    // EFFECTS: does nothing
    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
