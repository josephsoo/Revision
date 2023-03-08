package persistence;

import model.Deck;
import model.Card;
import ui.Revision;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends persistence.JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            ArrayList<Deck> decks = new ArrayList<Deck>();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDecks() {
        try {
            ArrayList<Deck> decks = new ArrayList<Deck>();
            JsonWriter writer = new JsonWriter("./data/testReaderEmptyDecks.json");
            writer.open();
            writer.write(decks);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderEmptyDecks.json");
            decks = reader.read();
            assertEquals(0, decks.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralDecks() {
        try {
            ArrayList<Deck> decks = new ArrayList<Deck>();
            Deck deck1 = new Deck("chem");
            Deck deck2 = new Deck("math");
            deck1.addCard(new Card("O", "oxygen"));
            deck2.addCard(new Card("1+1", "2"));

            decks.add(deck1);
            decks.add(deck2);
            JsonWriter writer = new JsonWriter("./data/testReaderGeneralDecks.json");
            writer.open();
            writer.write(decks);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderGeneralDecks.json");
            decks = reader.read();
            assertEquals("chem", decks.get(0).getDeckName());
            assertEquals("math", decks.get(1).getDeckName());


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}