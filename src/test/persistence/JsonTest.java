package persistence;

import model.Card;
import model.Deck;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkCard(String question, String answer, int ease, int timeRemaining, Card card) {
        assertEquals(question, card.getQuestion());
        assertEquals(answer, card.getAnswer());
        assertEquals(ease, card.getEase());
        assertEquals(timeRemaining, card.getTimeRemaining());
    }

    protected void checkDeck(Deck deck1, Deck deck2) {
        assertEquals(deck1.getDeckName(), deck2.getDeckName());
        List<Card> cards1 = deck1.getAllCards();
        List<Card> cards2 = deck2.getAllCards();
        assertEquals(cards1.size(), cards2.size());
        for (int i = 0; i < cards1.size(); i++) {
            Card card1 = cards1.get(i);
            checkCard(card1.getQuestion(), card1.getAnswer(), card1.getEase(), card1.getTimeRemaining(), cards2.get(i));
        }
    }
}
