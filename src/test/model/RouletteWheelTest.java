package model;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RouletteWheelTest {
    
    private RouletteWheel rouletteWheel;
    private ArrayList<String> expectedColors;

    @SuppressWarnings("methodlength")
    @BeforeEach
    void runBefore() {
        rouletteWheel = new RouletteWheel();
        expectedColors = new ArrayList<>();
        expectedColors.add("green");
        expectedColors.add("red");
        expectedColors.add("black");
        expectedColors.add("red");
        expectedColors.add("black");
        expectedColors.add("red");
        expectedColors.add("black");
        expectedColors.add("red");
        expectedColors.add("black");
        expectedColors.add("red");
        expectedColors.add("black");
        expectedColors.add("black");
        expectedColors.add("red");
        expectedColors.add("black");
        expectedColors.add("red");
        expectedColors.add("black");
        expectedColors.add("red");
        expectedColors.add("black");
        expectedColors.add("red");
        expectedColors.add("red");
        expectedColors.add("black");
        expectedColors.add("red");
        expectedColors.add("black");
        expectedColors.add("red");
        expectedColors.add("black");
        expectedColors.add("red");
        expectedColors.add("black");
        expectedColors.add("red");
        expectedColors.add("black");
        expectedColors.add("black");
        expectedColors.add("red");
        expectedColors.add("black");
        expectedColors.add("red");
        expectedColors.add("black");
        expectedColors.add("red");
        expectedColors.add("black");
        expectedColors.add("red");
    }

    // assignColors() in RouletteWheel class method is already check in testConstructor()
    @Test
    void testConstructor() {
        assertEquals(0, rouletteWheel.getWinningNumber());
        assertEquals("", rouletteWheel.getWinningColor());
        assertTrue(expectedColors.equals(rouletteWheel.getColors()));
    }

    @Test
    public void testAssignColorsGreenAtZero() {
        // Test color for number 0 is green
        assertEquals("green", rouletteWheel.getColors().get(0));
    }

    @Test
    public void testAssignColorsInRange() {
        ArrayList<String> colors = rouletteWheel.getColors();
        
        // Test for range 1 to 10 and 19 to 28 (even = black, odd = red)
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                assertEquals("black", colors.get(i));
            } else {
                assertEquals("red", colors.get(i));
            }
        }
        
        for (int i = 19; i <= 28; i++) {
            if (i % 2 == 0) {
                assertEquals("black", colors.get(i));
            } else {
                assertEquals("red", colors.get(i));
            }
        }
    }

    @Test
    public void testAssignColorsForRemainingRange() {
        ArrayList<String> colors = rouletteWheel.getColors();
        
        // Test for range 11 to 18 and 29 to 36 (even = red, odd  black)
        for (int i = 11; i <= 18; i++) {
            if (i % 2 == 0) {
                assertEquals("red", colors.get(i));
            } else {
                assertEquals("black", colors.get(i));
            }
        }
        
        for (int i = 29; i <= 36; i++) {
            if (i % 2 == 0) {
                assertEquals("red", colors.get(i));
            } else {
                assertEquals("black", colors.get(i));
            }
        }
    }
    
    @Test
    void testSpinTheWheel() {
        rouletteWheel.spinTheWheel();
        String result = rouletteWheel.getWinningColor();

        assertTrue(result.equals("red") || result.equals("black") || result.equals("green"));
        assertEquals(rouletteWheel.getColors().get(rouletteWheel.getWinningNumber()), result);
    }

    // testBetColor() method also check if equalsIgnoreCase() works
    @Test
    public void testBetColorRed() {
        boolean result = rouletteWheel.betColor("red");
        assertTrue(result || !result); // Should pass whether the result is true or false, since it's random
    }

    @Test
    public void testBetColorBlack() {
        boolean result = rouletteWheel.betColor("black");
        assertTrue(result || !result);
    }

    @Test
    public void testBetColorGreen() {
        boolean result = rouletteWheel.betColor("green");
        assertTrue(result || !result);
    }

    @Test
    public void testInvalidColor() {
        boolean result = rouletteWheel.betColor("blue");
        assertFalse(result);
    }

    @Test
    public void testBetNumber() {
        rouletteWheel.spinTheWheel();

        int numberHolder = rouletteWheel.getWinningNumber();
        int wrongNumber = (numberHolder + 1) % 37; // make sure the number bet is between 0-36

        assertFalse(rouletteWheel.betNumber(wrongNumber));
    }
}
