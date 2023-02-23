package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {
    private ArrayList<Card> cards;
    private String deckName;
    private static final int NUMANSWERS = 4;

    // EFFECTS: constructs an empty deck with no cards and a given name
    public Deck(String deckName) {
        this.deckName = deckName;
        cards = new ArrayList<Card>();
    }


    // EFFECTS: schedules the Card into cards by inserting it into the appropriate slot based on its time remaining
     // MODIFIES: this
    public void addCard(Card card) {
        boolean inserted = false;
        int index = 0;
        for (Card deckCard : cards) {
            if (card.getTimeRemaining() < deckCard.getTimeRemaining()) {
                inserted = true;
                cards.add(index, card);
                break;
            }
            index++;
        }
        if (!inserted) {
            cards.add(card);
        }
    }

    // REQUIRES: There is more than one card in the deck
    // EFFECTS: Returns 4 random answers from the deck, more if there is
    public ArrayList<String> getAnswers() {
        ArrayList<String> answers = new ArrayList<>();
        if (cards.size() < NUMANSWERS) {
            for (Card card : cards) {
                answers.add(card.getAnswer());
            }
        } else {
            answers.add(cards.get(0).getAnswer());
            ArrayList<Integer> addedCardsIndexes =  new ArrayList<Integer>(Arrays.asList(0));
            int n;
            while (answers.size() < NUMANSWERS) {
                n = ThreadLocalRandom.current().nextInt(0, cards.size());
                if (!addedCardsIndexes.contains(n)) {
                    answers.add(cards.get(n).getAnswer());
                    addedCardsIndexes.add(n);
                }
            }
        }
        Collections.shuffle(answers);
        return answers;
    }

    // REQUIRES: Deck is not empty (there is at least one card in the deck)
    // EFFECTS: updates and reschedules the card into the deck depending on if it passed or not
    public void rescheduleDeck(boolean isPassed) {
        Card cardAnswered = cards.get(0);
        cards.remove(0);
        int cardTimeRemaining = cardAnswered.getTimeRemaining();
        for (Card card : cards) {
            card.subtractTimeRemaining(cardTimeRemaining);
        }
        cardAnswered.updateCard(isPassed);
        this.addCard(cardAnswered);
    }

    // EFFECTS: Returns true if the answer is correct (it matches the answer of the first card)
    public boolean isCorrectAnswer(String answer) {
        return answer.equals(cards.get(0).getAnswer());
    }

    // EFFECTS: Returns the name of the deck
    public String getDeckName() {
        return deckName;
    }

    // EFFECTS: Changes the name of the deck to newDeckName
    // MODIFIES: this
    public void renameDeck(String newDeckName) {
        this.deckName = newDeckName;
    }

    //EFFECTS: Returns (a copy of) the deck's cards
    public ArrayList<Card> getAllCards() {
        ArrayList<Card> returnCards = new ArrayList<>();
        for (Card card : cards) {
            returnCards.add(card);
        }
        return returnCards;
    }

    // REQUIRES: cardToRemove is in cards
    // EFFECTS: Removes cardToRemove from the deck
    // MODIFIES: this
    public void removeCard(Card cardToRemove) {
        cards.remove(cardToRemove);
    }


    // REQUIRES: there is at least one question in the deck
    // EFFECTS: returns the first question in the deck (the card being reviewed)
    public String getQuestion() {
        String question = cards.get(0).getQuestion();
        return question;
    }

    // EFFECTS: returns true if the deck is empty
    public Boolean isEmpty() {
        return cards.size() == 0;
    }

}



