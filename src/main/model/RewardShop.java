package model;

import java.util.ArrayList;

// Represents a reward shop that the user can use their money after winning the roulette game to buy items
public class RewardShop {
    private ArrayList<Item> items;

    /*
     * EFFECT: Make an empty list of items that sthe shop sell.
     */
    public RewardShop() {
        items = new ArrayList<>();
    }

    /*
     * REQUIRES: item must not null.
     * EFFECTS: Add item to the shop.
     * MODIFIES: this
     */
    public void addItem(Item item) {
        items.add(item);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    /*
     * REQUIRES: itemIdx must be smaller than items.size()
     * EFFECTS: Adding an item that the user choose if they have enough balance in their bankAccount;
     *          Subtract balance by item price; Adding the item to the backpack. return true if the user can 
     *          purchase an item, otherwise return false.
     * MODIFIES: this, BankAccount, Backpack
     */
    public boolean purchaseItem(int itemIdx, BankAccount bankAccount, Backpack backpack) {
        Item item = items.get(itemIdx);
        int itemPrice = item.getItemPrice();

        if (bankAccount.getBalance() < itemPrice) {
            return false;
        } else {
            bankAccount.subtract(itemPrice);
            backpack.addItem(item);
        }

        return true;
    }
}
