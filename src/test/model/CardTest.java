package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class CardTest {
    Card card1;
    Card card2;
    @BeforeEach
    public void setup(){
        card1 = new Card("question", "answer");
        card2 = new Card("question1", "answer1");
    }
    @Test
    public void cardIDTest() {
        int card1ID = card1.getId();
        assertEquals(card1ID + 1,card2.getId());
    }

    @Test
    public void subtractTimeRemainingTest()
    {
        assertEquals(card1.getStartingEase() , card1.getTimeRemaining());
        card1.subtractTimeRemaining(card1.getTimeRemaining() - 1);
        assertEquals(1, card1.getTimeRemaining());
    }

    @Test
    public void updateCardTest() {
        card1.updateCard(true);
        assertEquals((int) (card1.getStartingEase()* card1.passedMultiplier),  card1.getTimeRemaining());
        assertEquals((int) (card1.getStartingEase()* card1.passedMultiplier),  card1.getEase());
        card1.updateCard(false);
        assertEquals((int) (card1.getStartingEase()* card1.passedMultiplier* card1.failedMultiplier),  card1.getTimeRemaining());
        assertEquals((int) (card1.getStartingEase()* card1.passedMultiplier* card1.failedMultiplier),  card1.getEase());
    }


    @Test
    public void changeQuestionTest() {
        card1.changeQuestion("newquestion");
        assertEquals("newquestion", card1.getQuestion());
    }

    @Test
    public void changeAnswerTest() {
        card1.changeAnswer("newanswer");
        assertEquals("newanswer", card1.getAnswer());
    }



}