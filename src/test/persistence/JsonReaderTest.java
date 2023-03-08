package persistence;

import model.Card;
import model.Deck;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ArrayList<Deck> d = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDecks() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDecks.json");
        try {
            ArrayList<Deck> decks = reader.read();
            assertEquals(0, decks.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDecks() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDecks.json");
        try {
            ArrayList<Deck> decks = reader.read();
            Deck deck1 = decks.get(0);
            Deck deck2 = decks.get(1);
            Deck deck1Expected = new Deck("chem");
            Deck deck2Expected = new Deck("math");
            deck1Expected.addCard(new Card("O", "oxygen"));
            deck1Expected.addCard(new Card("C", "carbon"));
            deck2Expected.addCard(new Card("1+1", "2"));
            deck2Expected.addCard(new Card("1+2", "3"));

            checkDeck(deck1Expected, deck1);
            checkDeck(deck2Expected, deck2);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}