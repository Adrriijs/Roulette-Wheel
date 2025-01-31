package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RewardShopTest {
    private RewardShop rewardShop;
    private Item item1; 
    private Item item2; 
    private Item item3;
    private BankAccount bankAccount;
    private Backpack backpack;

    @BeforeEach
    void runBefore() {
        rewardShop = new RewardShop();
        item1 = new Item("Doll", 10000);
        item2 = new Item("Car", 50000);
        item3 = new Item("Laptop", 10000);
        bankAccount = new BankAccount(40000);
        backpack = new Backpack();
    }

    @Test
    void testConstructor() {
        assertEquals(0, rewardShop.getItems().size());
    }

    @Test 
    void addItemTest() {
        rewardShop.addItem(item1);
        assertEquals(1, rewardShop.getItems().size());
        assertEquals("Doll", rewardShop.getItems().get(0).getItemName());
    }

    @Test
    void addMultipleItemsTest() {
        rewardShop.addItem(item1);
        rewardShop.addItem(item2);
        rewardShop.addItem(item3);

        assertEquals(3, rewardShop.getItems().size());
        assertEquals(10000, rewardShop.getItems().get(0).getItemPrice());
        assertEquals("Car", rewardShop.getItems().get(1).getItemName());
        assertEquals("Laptop", rewardShop.getItems().get(2).getItemName());
    }

    @Test
    void purchasItemTest() {
        rewardShop.addItem(item1);
        rewardShop.addItem(item2);
        rewardShop.addItem(item3);

        rewardShop.purchaseItem(0, bankAccount, backpack);
        assertEquals(30000, bankAccount.getBalance());
        assertEquals("Doll", backpack.getItemsOwned().get(0).getItemName());

        rewardShop.purchaseItem(1, bankAccount, backpack);
        assertEquals(30000, bankAccount.getBalance());
        assertEquals(1, backpack.getItemsOwned().size());
    }
}
