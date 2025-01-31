package model;

import org.json.JSONObject;

import persistence.Writable;

// Represent a bank account where the user can deposit and withdraw their money (in CAD) for betting
public class BankAccount implements Writable {
    private int balance;

    /*
     * REQUIRES: initialBalance must not be a negative number and not 0.
     * EFFECTS: balance is set to initialBalance.
     */
    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    /*
     * REQUIRES: amount must not be a 0 and not a negative number.
     * EFFECTS: Add amount to balance.
     * MODIFIES: this
     */
    public void deposit(int amount) {
        this.balance += amount;
    }
    
    /*
     * REQUIRES: amount must not be a 0, a negative number, and bigger than the current balance.
     * EFFECTS: Subtract balance by amount.
     * MODIFIES: this
     */
    public void subtract(int amount) {
        this.balance -= amount;
    }

    public int getBalance() {
        return this.balance;
    }

    // EFFECTS: returns this account as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("balance", balance);
        return json;
    }
}
