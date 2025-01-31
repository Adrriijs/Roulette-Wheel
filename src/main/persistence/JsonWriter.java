package persistence;

import model.Backpack;
import model.BankAccount;

import org.json.JSONObject;

import java.io.*;

// Note : cited from WorkRoomApp and updated based on SimpleRouletteWheel code model.
// Represent a writer that writes JSON representation of backpack to file.
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: construct writer to write to destination file.
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    /*
     *  EFFECTS: opens writer and throw FileNotFoundException if destination file cannot 
     *           be opened for writing.
     */ 
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    /*
     * MODIFIES: this
     * EFFECTS: write JSON representation of backpack to file.
     */
    public void write(Backpack backpack, BankAccount bankAccount) {
        JSONObject json = new JSONObject();
        JSONObject json2 = new JSONObject();
        json = bankAccount.toJson();
        json2 = backpack.toJson();
        mergeJsonObjects(json, json2);
        saveToFile(json.toString(TAB));
    }

    public static void mergeJsonObjects(JSONObject target, JSONObject source) {
        for (String key : source.keySet()) {
            target.put(key, source.get(key)); // This overwrites existing keys in target
        }
    }

    // MODIFIES: this
    // EFFECTS: closes writer.
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file.
    private void saveToFile(String json) {
        writer.print(json);
    }
}
