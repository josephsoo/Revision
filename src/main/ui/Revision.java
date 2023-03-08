package ui;

import model.Card;
import model.Deck;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Spaced repetition flashcard application inspired by TellerApp https://github.students.cs.ubc.ca/CPSC210/TellerApp

public class Revision implements Writable {
    private static final String JSON_STORE = "./data/workroom.json";
    ArrayList<Deck> decks = new ArrayList<Deck>();
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Runs the Revision application
    public Revision() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runRevision();
    }

    // MODIFIES: this
    // EFFECTS: processes user input. Inspired by TellerApp https://github.students.cs.ubc.ca/CPSC210/TellerApp
    //          class: TellerApp, method: runTellerApp
    public void runRevision() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");

    }

    // MODIFIES: this by calling other methods
    // EFFECTS: processes user command
    private void processCommand(String command) {
        command = command.toLowerCase();
        if (command.equals("s")) {
            doSelectDeck();
        } else if (command.equals("a")) {
            doAddDeck();
        } else if (command.equals("sa")) {
            saveDecks();
        } else if (command.equals("l")) {
            loadDecks();
        } else {
            System.out.println("Selection not valid...");
        }
    }



    // MODIFIES: this
    // EFFECTS: initializes the system
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ts -> select deck");
        System.out.println("\ta -> add deck");
        System.out.println("\tsa -> save decks");
        System.out.println("\tl -> load decks");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays menu of options for the deck to user
    private void displayDeckMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\td -> delete deck");
        System.out.println("\tn -> rename deck");
        System.out.println("\tr -> review deck");
        System.out.println("\ta -> add card");
        System.out.println("\ts -> see all cards");
        System.out.println("\tb -> back");
    }


    // MODIFIES: this
    // EFFECTS: allows the user to do something with a deck
    private void doSelectDeck() {
        if (decks.size() == 0) {
            System.out.println("\nYou have no decks!");
        } else {
            ArrayList<String> allDeckNames = new ArrayList<>();
            for (Deck deck:decks) {
                allDeckNames.add(deck.getDeckName());
            }
            System.out.println("\nYour current decks are:");
            for (String name : allDeckNames) {
                System.out.print(name + "\t");
            }
            String name = null;
            while (!allDeckNames.contains(name)) {
                System.out.println("\nWhich deck do you wish to select?");
                name = input.next();
            }
            processDeck(name, allDeckNames);

        }
    }

    private ArrayList<String> getAllDeckNames() {
        ArrayList<String> allDeckNames = new ArrayList<>();
        for (Deck deck:decks) {
            allDeckNames.add(deck.getDeckName());
        }
        return allDeckNames;
    }



    // Requires: name is a name of a deck in decks
    // MODIFIES: this (by calling methods that modify this)
    // EFFECTS: allows the user select between rename, review, or delete the deck
    @SuppressWarnings("methodlength")
    public void processDeck(String name, ArrayList<String> allDeckNames) {
        displayDeckMenu();
        String command;
        int i = allDeckNames.indexOf(name);
        Deck deck = decks.get(i);
        Boolean keepGoing = true;
        while (keepGoing) {
            keepGoing = false;
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("d")) {
                deleteDeck(i);
            } else if (command.equals("n")) {
                renameDeck(i, deck);
            } else if (command.equals("r")) {
                reviewDeck(deck);
            } else if (command.equals("b")) {
                break;
            } else if (command.equals("a")) {
                deckCardAdder(deck);
            } else if (command.equals("s")) {
                printAllCards(deck);
            } else {
                keepGoing = true;
            }
        }
    }

    // EFFECTS: Prints every card's question, answer, and id
    private void printAllCards(Deck deck) {
        for (Card card: deck.getAllCards()) {
            System.out.println("Question: " + card.getQuestion());
            System.out.println("Answer: " + card.getAnswer());
        }
    }

    // MODIFIES: deck
    // EFFECTS: allows the user to add card to a given deck, and quit if needed
    private void deckCardAdder(Deck deck) {
        System.out.println("Press b to go back");
        while (true) {
            System.out.println("Card Question:");
            String question = input.next();
            if (question.equals("b")) {
                break;
            }
            System.out.println("Card Answer:");
            String answer = input.next();
            if (answer.equals("b")) {
                break;
            }
            deck.addCard(new Card(question, answer));
        }
    }

    // EFFECTS: renames the deck
    // REQUIRES: i is a valid index in decks: 0 <= i < decks.size()
    private void renameDeck(int i, Deck deck) {
        System.out.println("Rename deck to:");
        String newName = input.next();
        deck.renameDeck(newName);
    }

    // MODIFIES: deck
    // EFFECTS: allows user to review if deck is not empty
    private void reviewDeck(Deck deck) {
        if (deck.isEmpty()) {
            System.out.println("There are no cards in this deck");
        } else {
            deckPractice(deck);
        }
    }

    // REQUIRES: deck has at least one card
    // MODIFIES: deck
    // EFFECTS: allows the user to review the deck by giving answers to questions
    private void deckPractice(Deck deck) {
        System.out.println("Press b to go back");
        while (true) {
            printQuestionsAndAnswers(deck);
            String answer = input.next();
            if (answer.equals("b")) {
                break;
            } else {
                Boolean firstTry = deck.isCorrectAnswer(answer);
                Boolean passed = firstTry;
                while (!passed) {
                    System.out.println("That wasn't right. Try again?");
                    String newAnswer = input.next();
                    if (newAnswer.equals("b")) {
                        break;
                    }
                    passed = deck.isCorrectAnswer(newAnswer);
                }
                deck.rescheduleDeck(firstTry);
            }
        }
    }

    // REQUIRES: deck has at least 1 card
    // EFFECTS: gives the prompt to the user
    private void printQuestionsAndAnswers(Deck deck) {
        System.out.println(deck.getQuestion());
        ArrayList<String> answers = deck.getAnswers();
        for (String answer : answers) {
            System.out.print(answer + "\t");
        }
        System.out.println("\nType in your answer");
    }

    // EFFECTS: deletes the deck at index i
    // REQUIRES: i is a valid index in decks
    // MODIFIES: this
    private void deleteDeck(int i) {
        decks.remove(i);
    }

    // MODIFIES: this
    // EFFECTS: adds a deck for the user, making sure it is not a deck with a duplicate name
    private void doAddDeck() {
        System.out.println("What do you want to name your deck?");
        ArrayList<String> allDeckNames = getAllDeckNames();
        Boolean keepGoing = true;
        String deckName = null;
        while (keepGoing) {
            deckName = input.next();
            if (allDeckNames.contains(deckName)) {
                System.out.println("You can't have duplicate deck names!");
            } else {
                keepGoing = false;
            }
        }
        decks.add(new Deck(deckName));
        allDeckNames.add(deckName);
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
            System.out.println("Saved decks to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadDecks() {
        try {
            decks = jsonReader.read();
            System.out.println("Loaded decks from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
