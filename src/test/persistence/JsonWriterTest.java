package persistence;

import model.Backpack;
import model.BankAccount;
import model.Item;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

public class JsonWriterTest extends JsonTest {
    private Backpack backpack;
    private BankAccount bankAccount;

    @Test
    void testWriterInvalidFile() {
        try {
            backpack = new Backpack();
            bankAccount = new BankAccount(100); // Add a BankAccount with some initial balance
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyBackpack() {
        try {
            backpack = new Backpack();
            bankAccount = new BankAccount(100); // Initial balance for testing
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBackpack.json");
            writer.open();
            writer.write(backpack, bankAccount);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBackpack.json");
            Object[] objects = reader.read();
            backpack = (Backpack) objects[1];
            bankAccount = (BankAccount) objects[0];

            assertEquals(0, backpack.getItemsOwned().size());
            assertEquals(100, bankAccount.getBalance());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBackpack() {
        try {
            backpack = new Backpack();
            bankAccount = new BankAccount(500); // Add initial balance
            backpack.addItem(new Item("Coke", 5000));
            backpack.addItem(new Item("Laptop", 35000));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBackpack.json");
            writer.open();
            writer.write(backpack, bankAccount);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBackpack.json");
            Object[] objects = reader.read();
            backpack = (Backpack) objects[1];
            bankAccount = (BankAccount) objects[0];

            ArrayList<Item> itemsOwned = backpack.getItemsOwned();
            assertEquals(2, itemsOwned.size());
            checkItem("Coke", 5000, itemsOwned.get(0));
            checkItem("Laptop", 35000, itemsOwned.get(1));
            assertEquals(500, bankAccount.getBalance());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

