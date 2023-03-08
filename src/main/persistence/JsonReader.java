package persistence;

import model.Card;
import model.Deck;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

import static java.lang.Integer.parseInt;

// Represents a reader that reads the decks from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads deck from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<Deck> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDecks(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private ArrayList<Deck> parseDecks(JSONObject jsonObject) {
        ArrayList<Deck> decks = new ArrayList<Deck>();
        addDecks(decks, jsonObject.getJSONArray("decks"));
        return decks;
    }

    // MODIFIES: decks
    // EFFECTS: adds decks from json array to decks
    private void addDecks(ArrayList<Deck> decks, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextDeck = (JSONObject) json;
            addDeck(decks, nextDeck);
        }
    }

    // MODIFIES: decks
    // EFFECTS: adds decks from json array to decks
    private void addDeck(ArrayList<Deck> decks, JSONObject jsonObject) {
        String deckName = jsonObject.getString("deckName");
        Deck d = new Deck(deckName);
        addCards(d, jsonObject);
        decks.add(d);
    }

    // MODIFIES: d
    // EFFECTS: parses cards from JSON object and adds them to deck
    private void addCards(Deck d, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cards");
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addCard(d, nextCard);
        }
    }

    // MODIFIES: d
    // EFFECTS: parses card from JSON object and adds it to deck
    private void addCard(Deck d, JSONObject jsonObject) {
        String question = jsonObject.getString("question");
        String answer = jsonObject.getString("answer");
        int ease = parseInt(String.valueOf(jsonObject.getInt("ease")));
        int timeRemaining = parseInt(String.valueOf(jsonObject.getInt("timeRemaining")));
        Card card = new Card(question, answer, ease, timeRemaining);
        d.addCard(card);
    }
}


