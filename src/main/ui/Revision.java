package ui;

import model.Card;
import model.Deck;

import java.util.ArrayList;
import java.util.Scanner;

public class Revision {

    ArrayList<Deck> decks = new ArrayList<Deck>();
    private Scanner input;
    ArrayList<String> allDeckNames = new ArrayList<>();

    // EFFECTS: Runs the Revision application
    public Revision() {
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

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        command = command.toLowerCase();
        if (command.equals("s")) {
            doSelectDeck();
        } else if (command.equals("a")) {
            doAddDeck();
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
            System.out.println("\nYour current decks are:");
            for (String name : allDeckNames) {
                System.out.print(name + "\t");
            }
            String name = null;
            while (!allDeckNames.contains(name)) {
                System.out.println("\nWhich deck do you wish to select?");
                name = input.next();
            }
            processDeck(name);

        }
    }



    // Requires: name is a name of a deck in decks
    // MODIFIES: this
    // EFFECTS: allows the user select between rename, review, or delete the deck
    @SuppressWarnings("methodlength")
    public void processDeck(String name) {
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
            System.out.println("ID: " + card.getId() + "\n");
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
        allDeckNames.set(i, newName);
    }

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
        allDeckNames.remove(i);
        decks.remove(i);
    }

    // MODIFIES: this
    // EFFECTS: adds a deck for the user, making sure it is not a deck with a duplicate name
    private void doAddDeck() {
        System.out.println("What do you want to name your deck?");
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
}
