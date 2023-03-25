package ui;

// Spaced repetition flashcard application inspired by TellerApp https://github.students.cs.ubc.ca/CPSC210/TellerApp

import model.Deck;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GUI extends JFrame implements Writable, ActionListener, ListSelectionListener {
    private static final String JSON_STORE = "./data/workroom.json";
    ArrayList<Deck> decks = new ArrayList<Deck>();
    Deck currentDeck;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private JList<String> listOfDeckNamesModel;

    // EFFECTS: Runs the Revision application
    public GUI() {
        super("Revision");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        displaySplash();

    }

    public void displaySplash() {
        JPanel splashPanel = new JPanel(new BorderLayout());
        ImageIcon splashIcon = new ImageIcon("data/tobs.jpg");
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
        init();
        initializeGraphics();

    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this DrawingEditor will operate, and populates the tools to be used
    //           to manipulate this drawing
    private void initializeGraphics() {
        setLayout(new BorderLayout());

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        add(makeButtonPanel(), BorderLayout.SOUTH);
        add(makeNamesPanel(), BorderLayout.NORTH);
    }

    public JPanel makeNamesPanel() {
        JPanel namesPanel = new JPanel();
        namesPanel.setLayout(new BoxLayout(namesPanel, BoxLayout.Y_AXIS));

        // Add each name to the panel
        for (String name : getAllDeckNames()) {
            JLabel label = new JLabel(name);
            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    // Handle mouse click on the label
                    currentDeck = getDeck(name);
                    System.out.println(name);
                }
            });
            namesPanel.add(label);
        }
        return namesPanel;
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
        JButton modifyDeck = new JButton("Modify Deck");
        modifyDeck.setActionCommand("modifyDeck");
        modifyDeck.addActionListener(this);
        JButton addDeck = new JButton("Add Deck");
        addDeck.setActionCommand("");
        addDeck.addActionListener(this);
        JButton reviewer = new JButton("Reviewer");
        reviewer.setActionCommand("reviewer");
        reviewer.addActionListener(this);
        buttonPanel.add(loadDecks);
        buttonPanel.add(saveDecks);
        buttonPanel.add(modifyDeck);
        buttonPanel.add(reviewer);
        return buttonPanel;
    }


    // MODIFIES: this
    // EFFECTS: initializes the system
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }




    private ArrayList<String> getAllDeckNames() {
        ArrayList<String> allDeckNames = new ArrayList<>();
        for (Deck deck : decks) {
            allDeckNames.add(deck.getDeckName());
        }
        return allDeckNames;
    }


    // EFFECTS: deletes the deck at index i
    // REQUIRES: i is a valid index in decks
    // MODIFIES: this
    private void deleteDeck(int i) {
        decks.remove(i);
    }


    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray decksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Deck d : decks) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }


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
            initializeGraphics();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE);

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "loadDecks":
                loadDecks();
                break;
            case "saveDecks":
                saveDecks();
                break;
            case "modifyDeck":
                JOptionPane.showMessageDialog(null, "Button 3 clicked");
                break;
            case "reviewer":
                JOptionPane.showMessageDialog(null, "Button 4 clicked");
        }
    }

    public Deck getDeck(String deckName) {
        List<String> names = getAllDeckNames();
        for (int i = 0; i < names.size(); i++) {
            if (decks.get(i).getDeckName() == deckName) {
                return decks.get(i);
            }
        }
        return null;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}