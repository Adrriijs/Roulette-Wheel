package persistence;

import model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Note : cited from WorkRoomApp and updated based on SimpleRouletteWheel code model.
public class JsonTest {
    protected void checkItem(String name, int price, Item item) {
        assertEquals(name, item.getItemName());
        assertEquals(price, item.getItemPrice());
    }
}
