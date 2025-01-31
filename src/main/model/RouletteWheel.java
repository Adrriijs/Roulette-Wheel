package model;

import java.util.Random;
import java.util.ArrayList;

// Represents a roulette wheel that has a range of number (0-36) and each number has color (red, black, or green)
public class RouletteWheel {
    private ArrayList<String> colors;
    private int winningNumber;
    private String winningColor;

    /*
     * EFFECTS: Initializes the RouletteWheel object with an array of colors
     *          of the numbers from 0-36; Each number will be assigned a coresponding color
     *          ("red","black",or"green"); Set winningNumber to initial value which is 0;
     *          Set winningColor to empty string.
     */
    public RouletteWheel() {
        this.winningNumber = 0;
        this.winningColor = "";
        this.colors = new ArrayList<>();
        assignColors();
    }

    /*
     * EFFECTS: Assigns a colors to number from 0-36 based on conditions below:
     *         - Number 0 is green.
     *         - Number 1-10 and 19-28: odd numbers are red and even numbers are black.
     *         - Number 11-18 and 29-36: odd numbers are black and even numbers are red.
     * MODIFIES: this
     * 
     * note: The "else if" condition cannot be checked because the index is impossible to reach and should be skipped.
     */
    public void assignColors() {
        for (int i = 0; i <= 36; i++) {
            if (i == 0) {
                this.colors.add("green");
            } else if ((i >= 1 && i <= 10) || (i >= 19 && i <= 28)) {
                if (i % 2 == 0) {
                    this.colors.add("black");
                } else {
                    this.colors.add("red");
                }
            } else {
                if (i % 2 == 0) {
                    this.colors.add("red");
                } else {
                    this.colors.add("black");
                }
            }
            
        }
    }

    /*
     * EFFECTS: Spinning the wheel, returns a random winning number from 0-36
     *          and record the winning number also record the winning color based on the winning number.
     * MODIFIES: this
     */
    public void spinTheWheel() {
        Random random = new Random();
        this.winningNumber = random.nextInt(37);
        this.winningColor = this.colors.get(winningNumber);
    }

    public int getWinningNumber() {
        return this.winningNumber;
    }

    public ArrayList<String> getColors() {
        return this.colors;
    }

    public String getWinningColor() {
        return this.winningColor;
    }

    /*
     * REQUIRES: Color must be "red", "black", or "green"
     * EFFECTS: Return true if the color from the spin matches the bet color, otherwise
     *          return false.
     */
    public boolean betColor(String color) {
        spinTheWheel();
        return this.winningColor.equalsIgnoreCase(color);
    }

    /*
     * REQUIRES: Number must be between 0 and 36 inclusive
     * EFFECTS: Return true if the number from the spin matches the bet number, otherwise
     *          return false.
     */
    public boolean betNumber(int number) {
        spinTheWheel();
        return number == this.winningNumber;
    }
}
