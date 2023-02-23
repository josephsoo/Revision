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
        assertTrue(deck1.isEmpty());
        deck1.addCard(card1);
        deck1.addCard(card2);
        deck1.addCard(card3);
        ArrayList<Card> deck1Cards = new ArrayList<Card>();
        deck1Cards = deck1.getAllCards();
        assertTrue(deck1Cards.contains(card1));
        assertTrue(deck1Cards.contains(card2));
        assertTrue(deck1Cards.contains(card3));
        assertFalse(deck1.isEmpty());
    }

    @Test
    public void isCorrectAnswerTest() {
        card1 = new Card("q1", "a1");
        card2 = new Card("q2", "a2");
        card3 = new Card("q3", "a3");
        deck1.addCard(card1);
        deck1.addCard(card2);
        deck1.addCard(card3);
        assertTrue(deck1.isCorrectAnswer("a1"));
        assertFalse(deck1.isCorrectAnswer("a2"));
        deck1.rescheduleDeck(true);
        assertTrue(deck1.isCorrectAnswer("a2"));
        assertFalse(deck1.isCorrectAnswer("a1"));
    }

    @Test
    public void addCardsDifferentScheduleTest() {
        card1 = new Card("q1", "a1");
        card2 = new Card("q2", "a2");
        card3 = new Card("q3", "a3");
        card1.updateCard(true);
        card3.updateCard(false);
        deck1.addCard(card1);
        deck1.addCard(card2);
        deck1.addCard(card3);
        ArrayList<Card> deck1Cards = new ArrayList<Card>();
        deck1Cards = deck1.getAllCards();
        assertEquals(deck1Cards.get(0), card3);
        assertEquals(deck1Cards.get(1), card2);
        assertEquals(deck1Cards.get(2), card1);

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
        assertFalse(deck1.getAllCards().contains(card1));
    }

    @Test
    public void renameDeckTest() {

        assertEquals("CPSC210", deck1.getDeckName());
        deck1.renameDeck("CPSC121");
        assertEquals("CPSC121", deck1.getDeckName());
    }

    @Test
    public void getQuestionTest() {
        card1 = new Card("q1", "a1");
        card2 = new Card("q2", "a2");
        card3 = new Card("q3", "a3");
        card4 = new Card("q4", "a4");
        card5 = new Card("q5", "a5");
        deck1.addCard(card1);
        assertEquals("q1", deck1.getQuestion());
        deck1.addCard(card2);
        deck1.addCard(card3);
        deck1.addCard(card4);
        deck1.addCard(card5);
        assertEquals("q1", deck1.getQuestion());
    }

    @Test
    public void rescheduleDeckTest() {
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
        deck1.rescheduleDeck(true);
        assertEquals(card1, deck1.getAllCards().get(4));
        assertEquals(card2, deck1.getAllCards().get(0));
        int expectedEase = (int) (card1.getStartingEase()*card1.passedMultiplier);
        assertEquals(expectedEase, card1.getEase());
        int expectedTimeRemaining = 0;
        assertEquals(expectedTimeRemaining, card2.getTimeRemaining());
    }
}