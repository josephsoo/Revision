package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {
    private ArrayList<Card> cards;
    private String deckName;
    private static final int NUMANSWERS = 4;


    public Deck(String deckName) {
        this.deckName = deckName;
        cards = new ArrayList<Card>();
    }

    /*
    REQUIRES:
    EFFECTS: schedules the Card into cards by inserting it into the appropriate slot based on its time remaining
     */
    public void addCard(Card card) {
        boolean inserted = false;
        int index = 0;
        for (Card deckCard : cards) {
            if (deckCard.getTimeRemaining() < card.getTimeRemaining()) {
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
    //REQUIRES: There is more than one card in the deck
    public ArrayList<String> getAnswers() {
        ArrayList<String> answers = new ArrayList<String>();
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

    //EFFECTS: Returns the name of the deck
    public String getDeckName() {
        return deckName;
    }

    //EFFECTS: Changes the name of the deck to newDeckName
    public void renameDeck(String newDeckName) {
        this.deckName = newDeckName;
    }

    public ArrayList<Card> getAllCards() {
        return this.cards;
    }

    // REQUIRES: cardToRemove is in cards
    // EFFECTS: Removes cardToRemove from the deck
    public void removeCard(Card cardToRemove) {
        cards.remove(cardToRemove);
    }

}



