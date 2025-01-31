package model;

import org.json.JSONObject;
import persistence.Writable;

// Note : cited from WorkRoomApp and updated based on SimpleRouletteWheel code model.
// Represents an item with its name and price
public class Item implements Writable {
    private String itemName;
    private int itemPrice;

    /*
     * REQUIRES: name has a non-zero length and price must not be 0.
     * EFFECTS: itemName set to name and itemPrice set to price.
     */
    public Item(String name, int price) {
        this.itemName = name;
        this.itemPrice = price;
    }

    public String getItemName() {
        return this.itemName;
    }

    public int getItemPrice() {
        return this.itemPrice;
    }

    /*
     * REQUIRES: newName must has a non-zero length.
     * EFFECTS: Change the name of the item.
     * MODIFIES: this
     */
    public void renameItem(String newName) {
        String oldName = this.itemName; // Store the old name
        this.itemName = newName; // Update the name
        EventLog.getInstance().logEvent(new Event("Item renamed: " + oldName + " -> " + newName));
    }

    @Override
    public String toString() {
        return "Item Name [" + this.itemName + "], Item Price [$" + this.itemPrice + "]";
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Item name", this.itemName);
        json.put("Item price", this.itemPrice);
        return json;
    }
}
