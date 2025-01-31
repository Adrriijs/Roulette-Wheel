package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemTest {
    private Item item;

    @BeforeEach
    void runBefore() {
        item = new Item("Rolex Watch", 50000);
    }

    @Test
    void testConstructor() {
        assertEquals("Rolex Watch", item.getItemName());
        assertEquals(50000, item.getItemPrice());
    }

    @Test
    void renameItemTest() {
        item.renameItem("My Lovely Watch");
        assertEquals("My Lovely Watch", item.getItemName());
    }

    @Test
    void toStringTest() {
        String expectedResult = "Item Name [Rolex Watch], Item Price [$50000]";
        assertTrue(expectedResult.equals(item.toString()));
    }
}
