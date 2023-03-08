package persistence;

import org.json.JSONArray;
import ui.Revision;
import model.Deck;


import org.json.JSONObject;


import java.io.*;
import java.util.ArrayList;

// Represents a writer that writes JSON representation of decks to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of decks to file
    public void write(ArrayList<Deck> decks) {
        JSONObject json = new JSONObject();
        json.put("decks", decksToJson(decks));


        saveToFile(json.toString(TAB));
    }

    // EFFECTS: returns decks in a JSON representation
    private JSONArray decksToJson(ArrayList<Deck> decks) {
        JSONArray jsonArray = new JSONArray();

        for (Deck d : decks) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
