package persistence;

import model.Backpack;
import model.BankAccount;
import model.Item;

import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Note : cited from WorkRoomApp and updated based on SimpleRouletteWheel code model.
// Represent a reader that reads workroom from JSON data stored in file.
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader to read from source file.
    public JsonReader(String source) {
        this.source = source;
    }

    /*
     * EFFECTS: reads backpack from file and returns it.
     *          throw IOException if an error occurs reading data from file.
     */
    public Object[] read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        Backpack backpack = parseBackpack(jsonObject);
        BankAccount bankAccount = parseBankAccount(jsonObject);
        
        return new Object[]{bankAccount, backpack};
    }


    /*
     * EFFECTS: reads source file as string and returns it.
     *          throw IOException if an error occurs reading data from file.
     */
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parse items from JSON object and returns it.
    private Backpack parseBackpack(JSONObject jsonObject) {
        Backpack backpack = new Backpack();
        addItemsOwned(backpack, jsonObject);
        return backpack;
    }

    /*
     * MODIFIES: backpack
     * EFFECTS: parse items from JSON object and add it to backpack.
     */
    private void addItemsOwned(Backpack backpack, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Items owned");
        
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(backpack, nextItem);
        }
    }

    /*
     * MODIFIES: backpack
     * EFFECTS: parse item from JSON object and add it to backpack.
     */
    private void addItem(Backpack backpack, JSONObject jsonObject) {
        String itemName = jsonObject.getString("Item name");
        int itemPrice = jsonObject.getInt("Item price");
        Item item = new Item(itemName, itemPrice);
        backpack.addItem(item);
    }

    // EFFECTS: parses the bank account balance from the JSON object and returns a BankAccount object.
    private BankAccount parseBankAccount(JSONObject jsonObject) {
        int balance = jsonObject.getInt("balance");
        return new BankAccount(balance);
    }
}

