package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BackpackTest {
    private Backpack backpack;
    private Item item1; 
    private Item item2; 
    private Item item3;
    private BankAccount bankAccount;

    @BeforeEach
    void runBefore() {
        backpack = new Backpack();
        item1 = new Item("Doll", 10000);
        item2 = new Item("Car", 50000);
        item3 = new Item("Laptop", 10000);
        bankAccount = new BankAccount(1000);
    }

    @Test
    void testConstructor() {
        assertEquals(0, backpack.getItemsOwned().size());
    }

    @Test
    void addItemTest() {
        backpack.addItem(item1);
        assertEquals(1, backpack.getItemsOwned().size());
    }

    @Test
    void addMultipleItemsTest() {
        backpack.addItem(item1);
        assertEquals(1, backpack.getItemsOwned().size());

        backpack.addItem(item2);
        assertEquals(2, backpack.getItemsOwned().size());

        backpack.addItem(item3);
        assertEquals(3, backpack.getItemsOwned().size());
    }

    @Test
    void sellItemTest() {
        assertEquals(null, backpack.sellItem(1, bankAccount));
        assertEquals(null, backpack.sellItem(-1, bankAccount));

        backpack.addItem(item1);
        backpack.addItem(item2);
        backpack.addItem(item3);

        backpack.sellItem(1, bankAccount);
        assertEquals(2, backpack.getItemsOwned().size());
        assertTrue(backpack.getItemsOwned().get(0).equals(item1));
        assertFalse(backpack.getItemsOwned().get(1).equals(item2));
        assertTrue(backpack.getItemsOwned().get(1).equals(item3));

        assertEquals(41000, bankAccount.getBalance());
    }
}
