package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Note : cited from WorkRoomApp and updated based on SimpleRouletteWheel code model.
// Represent a backpack which user can store items inside or sell items from backpack
public class Backpack implements Writable {
    private ArrayList<Item> itemsOwned;

    /*
     * EFFECTS: Make an empty list of items that user owned.
     */
    public Backpack() {
        this.itemsOwned = new ArrayList<>();
    }

    public ArrayList<Item> getItemsOwned() {
        return itemsOwned;
    }

    /*
     * REQUIRES: item must not null.
     * EFFECTS: Add item to the backpack.
     * MODIFIES: this
     */
    public void addItem(Item item) {
        this.itemsOwned.add(item);
        EventLog.getInstance().logEvent(new Event("Item added to backpack: " + item.getItemName()));
    }

    /*
     * REQUIRES: itemIdx must be < itemsOwned.size() and itemsOwned.size() > 0
     * EFFECT: Sell the choosen item from backpack with 80% of the original price and remove the item from the backpack.
     * MODIFIES: this, BankAccount
     */
    public Item sellItem(int itemIdx, BankAccount bankAccount) {
        Item soldItem;

        if (itemIdx < 0 || itemIdx >= itemsOwned.size()) {
            EventLog.getInstance().logEvent(new Event("Fail to sell item"));
            return null;
        } else {
            soldItem = itemsOwned.get(itemIdx);
            int itemPrice = soldItem.getItemPrice() / 5 * 4;
            bankAccount.deposit(itemPrice);
            itemsOwned.remove(itemIdx);
            EventLog.getInstance().logEvent(new Event("Item sold from backpack: " 
                    + soldItem.getItemName() + " for $" + itemPrice));
        }

        return soldItem;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Items owned", itemsOwnedToJson());
        return json;
    }

    // EFFECTS: returns things in this backpack as a JSON array
    private JSONArray itemsOwnedToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item item : this.itemsOwned) {
            jsonArray.put(item.toJson());
        }
        return jsonArray;
    }
}
