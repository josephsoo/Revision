package persistence;

import org.json.JSONObject;

// Inspired by JsonSerialization Demo
// represents a class that can write
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
