package persistence;

import model.Backpack;
import model.BankAccount;
import model.Item;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

// Note : cited from WorkRoomApp and updated based on SimpleRouletteWheel code model.
public class JsonReaderTest extends JsonTest {
    private Backpack backpack;
    private BankAccount bankAccount;

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Object[] data = reader.read();
            backpack = (Backpack) data[0];
            bankAccount = (BankAccount) data[1];
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyBackpack() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBackpack.json");
        try {
            Object[] data = reader.read();
            backpack = (Backpack) data[1];
            bankAccount = (BankAccount) data[0];

            assertEquals(0, backpack.getItemsOwned().size());
            assertEquals(0, bankAccount.getBalance()); 
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralbackpack() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBackpack.json");
        try {
            Object[] data = reader.read();
            backpack = (Backpack) data[1];
            bankAccount = (BankAccount) data[0];

            ArrayList<Item> itemsOwned = backpack.getItemsOwned();
            assertEquals(2, itemsOwned.size());
            checkItem("Coke", 5000, itemsOwned.get(0));
            checkItem("Laptop", 35000, itemsOwned.get(1));

            assertEquals(10000, bankAccount.getBalance());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
