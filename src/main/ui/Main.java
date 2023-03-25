package ui;

import model.Card;
import model.Deck;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// EFFECTS: Runs Revision
public class Main {
    public static void main(String[] args) {
        new GUI();
    }
}
