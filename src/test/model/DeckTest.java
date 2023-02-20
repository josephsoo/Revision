package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DeckTest {

    private Deck deck1;
    private Card card1;
    private Card card2;
    private Card card3;
    private Card card4;
    private Card card5;
    private static final int NUMANSWERS = 4;


    @BeforeEach
    public void setup() {
        deck1 = new Deck("CPSC210");
    }

    @Test
    public void constructorTest() {
        assertEquals("CPSC210", deck1.getDeckName());
    }

    @Test
    public void addCardsTest() {
        card1 = new Card("q1", "a1");
        card2 = new Card("q2", "a2");
        card3 = new Card("q3", "a3");
        deck1.addCard(card1);
        deck1.addCard(card2);
        deck1.addCard(card3);
        ArrayList<Card> deck1Cards = new ArrayList<Card>();
        deck1Cards = deck1.getAllCards();
        assertTrue(deck1Cards.contains(card1));
        assertTrue(deck1Cards.contains(card2));
        assertTrue(deck1Cards.contains(card3));
    }

    @Test
    public void getLessAnswersTest() {
        card1 = new Card("q1", "a1");
        card2 = new Card("q2", "a2");
        card3 = new Card("q3", "a3");
        deck1.addCard(card1);
        deck1.addCard(card2);
        deck1.addCard(card3);
        ArrayList<String> deck1Answers = deck1.getAnswers();
        assertTrue(deck1Answers.contains(card1.getAnswer()));
        assertTrue(deck1Answers.contains(card2.getAnswer()));
        assertTrue(deck1Answers.contains(card3.getAnswer()));
    }

    @Test
    public void getMoreAnswersTest() {
        card1 = new Card("q1", "a1");
        card2 = new Card("q2", "a2");
        card3 = new Card("q3", "a3");
        card4 = new Card("q4", "a4");
        card5 = new Card("q5", "a5");
        deck1.addCard(card1);
        deck1.addCard(card2);
        deck1.addCard(card3);
        deck1.addCard(card4);
        deck1.addCard(card5);

        ArrayList<String> deck1Answers = deck1.getAnswers();
        ArrayList<String> deck1AllAnswers = new ArrayList<>();
        for (Card card : deck1.getAllCards()) {
            deck1AllAnswers.add(card.getAnswer());
        }

        ArrayList<String> addedAnswers = new ArrayList<>();

        for (String answer : deck1Answers) {
            assertTrue(deck1AllAnswers.contains(answer));
            assertFalse(addedAnswers.contains(answer));
            addedAnswers.add(answer);
        }

    }
    @Test
    public void removeCardTest() {
        card1 = new Card("q1", "a1");
        deck1.addCard(card1);
        ArrayList<Card> allCards = deck1.getAllCards();

        assertTrue(allCards.contains(card1));
        deck1.removeCard(card1);
        assertFalse(allCards.contains(card1));

    }
}