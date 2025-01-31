package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import persistence.*;

import model.*;

/**
 * Represents a simple roulette wheel game with betting and shop functionalities.
 */
public class SimpleRouletteWheel {
    private Scanner scanner;
    private RouletteWheel rouletteWheel;
    private BankAccount bankAccount;
    private RewardShop rewardShop;
    private Backpack backpack;

    private static final String JSON_STORE = "./data/progress.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    /**
     * EFFECTS: Initializes the app components and starts the game loop.
     */
    public SimpleRouletteWheel() {
        initializeApp();
        setUpStore();

        System.out.println("Welcome to the Simple Roulette Wheel Game!");
        boolean playing = true;

        while (playing) {
            System.out.println("\nYour current balance: $" + bankAccount.getBalance());
            System.out.println("1. Reload your Progress");
            System.out.println("2. Bet on Color");
            System.out.println("3. Bet on Number");
            System.out.println("4. Visit Shop");
            System.out.println("5. Open Backpack");
            System.out.println("6. Exit");
            System.out.print("\nChoose an option: ");
            
            playing = playingOptions();
        }

        scanner.close();
    }

    /**
     * EFFECTS: Initializes scanner, roulette wheel, bank account, reward shop, and backpack.
     */
    public void initializeApp() {
        scanner = new Scanner(System.in);
        rouletteWheel = new RouletteWheel();
        bankAccount = new BankAccount(10000);
        rewardShop = new RewardShop();
        backpack = new Backpack();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    /**
     * EFFECTS: Adds predefined items to the reward shop inventory.
     * MODIFIES: this
     */
    public void setUpStore() {
        Item item1 = new Item("Laptop", 35000);
        Item item2 = new Item("Hotel Ticket", 60000);
        Item item3 = new Item("Coke", 5000);
        Item item4 = new Item("Car", 180000);
        Item item5 = new Item("Nintendo Switch Oled", 25000);

        rewardShop.addItem(item1);
        rewardShop.addItem(item2);
        rewardShop.addItem(item3);
        rewardShop.addItem(item4);
        rewardShop.addItem(item5);
    }

    /**
     * EFFECTS: Displays menu options and processes user input.
     * MODIFIES: this
     * @return true if the game should continue, false otherwise.
     */
    public boolean playingOptions() {
        int choice = scanner.nextInt();
        System.out.println();

        switch (choice) {
            case 1:
                reloadProgress();
                break;
            case 2:
                betOnColor();
                break;
            case 3:
                betOnNumber();
                break;
            case 4:
                enterShop();
                break;
            case 5:
                openBackpack();
                break;
            case 6:
                return donePlaying();
        }
        return true; // Continue playing
    }

    /**
     * EFFECTS: Reloads user progress from a JSON file.
     * MODIFIES: this
     */
    public void reloadProgress() {
        try {
            Object[] data = jsonReader.read();
            backpack = (Backpack) data[0];
            bankAccount = (BankAccount) data[1];
            System.out.println("Your progress is Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    /**
     * REQUIRES: User must enter a valid color ("red" or "black" or "green").
     * EFFECTS: Places a bet on a color, updates balance based on win/loss.
     * MODIFIES: this, BankAccount
     */
    public void betOnColor() {
        System.out.print("Enter a color to bet on (red or black or green): ");
        String color = scanner.next();
        System.out.print("Enter amount to bet: ");
        int colorBet = scanner.nextInt();

        if (bankAccount.getBalance() >= colorBet) {
            bankAccount.subtract(colorBet);
            boolean win = rouletteWheel.betColor(color);
            System.out.println("\nBall is rolling...");
            System.out.println("It lands on: " + rouletteWheel.getWinningColor());

            if (win) {
                System.out.println("Congratulations! You won $" + colorBet * 2);
                bankAccount.deposit(colorBet * 2);
            } else {
                System.out.println("Sorry, you lost $" + colorBet);
            }
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    /**
     * REQUIRES: User must enter a valid number (0-36).
     * EFFECTS: Places a bet on a number, updates balance based on win/loss.
     * MODIFIES: this, BankAccount
     */
    public void betOnNumber() {
        System.out.print("Enter a number to bet on (0-36): ");
        int number = scanner.nextInt();
        System.out.print("Enter amount to bet: ");
        int numberBet = scanner.nextInt();

        if (bankAccount.getBalance() >= numberBet) {
            bankAccount.subtract(numberBet);
            boolean win = rouletteWheel.betNumber(number);
            System.out.println("\nBall is rolling...");
            System.out.println("It lands on: " + rouletteWheel.getWinningNumber());

            if (win) {
                System.out.println("Congratulations! You won $" + numberBet * 36);
                bankAccount.deposit(numberBet * 36);
            } else {
                System.out.println("Sorry, you lost $" + numberBet);
            }

        } else {
            System.out.println("Insufficient funds.");
        }
    }

        /**
     * EFFECTS: Displays shop items and allows the user to purchase items.
     * MODIFIES: this, BankAccount, Backpack
     */
    public void enterShop() {
        boolean flag = true;

        System.out.println("Here are the items available in the shop: ");
        for (int i = 0; i < rewardShop.getItems().size(); i++) {
            System.out.println((i + 1) + ". " + rewardShop.getItems().get(i));
        }

        while (flag) {
            System.out.println("\nOptions:");
            System.out.println("1. Purchase an item");
            System.out.println("2. Exit store");

            flag = shopOptions();
        }
    }

    /**
     * EFFECTS: Processes user input for shop options and performs the corresponding action.
     * MODIFIES: this, BankAccount, Backpack
     * @return true if the user stays in the shop, false otherwise.
     */
    public boolean shopOptions() {
        System.out.print("\nChoose an option: ");
        int options = scanner.nextInt();

        switch (options) {
            case 1:
                System.out.print("\nEnter the number of the item you want to buy: ");
                int item = scanner.nextInt();

                if (rewardShop.purchaseItem(item - 1, bankAccount, backpack)) {
                    System.out.println("We added [" + rewardShop.getItems().get(item - 1).getItemName() 
                            + "] to your backpack. Thank you!");
                } else {
                    System.out.println("Insufficient funds or invalid item.");
                }
                return true;

            case 2:
                return false;

            default:
                System.out.println("\nInvalid choice. Please try again.");
                return true;
        }
    }

    /**
     * EFFECTS: Displays the contents of the user's backpack and allows actions on the items.
     * MODIFIES: this
     */
    public void openBackpack() {
        if (backpack.getItemsOwned().size() == 0) {
            System.out.println("Your backpack is empty.");
        } else {
            System.out.println("These are your items in the backpack:");
            for (int i = 0; i < backpack.getItemsOwned().size(); i++) {
                System.out.println((i + 1) + ". " + backpack.getItemsOwned().get(i));
            }

            backpackOptions();
        }
    }

    /**
     * EFFECTS: Displays options for managing items in the backpack and performs corresponding actions.
     * MODIFIES: this, Backpack, BankAccount
     */
    public void backpackOptions() {
        boolean mark = true;

        while (mark) {
            printBackpackOptions();
            int choices = scanner.nextInt();
            System.out.println();

            switch (choices) {
                case 1:
                    sellAnItem();
                    if (backpack.getItemsOwned().size() == 0) {
                        mark = false;
                    }
                    break;

                case 2:
                    renameAnItem();
                    break;

                case 3:
                    mark = false; // Exit the loop
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * EFFECTS: Prints backpack management options to the console.
     */
    public void printBackpackOptions() {
        System.out.println("\nOptions:");
        System.out.println("1. Sell an item");
        System.out.println("2. Rename an item");
        System.out.println("3. Close Backpack");
        System.out.print("\nChoose an option: ");
    }

    /**
     * EFFECTS: Sells an item from the backpack, adds funds to the bank account, and removes the item from the backpack.
     * MODIFIES: this, Backpack, BankAccount
     */
    public void sellAnItem() {
        System.out.println("Which item do you want to sell? (number of the item in the list)");
        int sellAnItem = scanner.nextInt();

        int priceOfItem = (backpack.getItemsOwned().get(sellAnItem - 1).getItemPrice()) * 4 / 5;
        System.out.println("\nYou sold: " + backpack.sellItem(sellAnItem - 1, bankAccount) 
                + " for $" + priceOfItem);

        if (backpack.getItemsOwned().size() == 0) {
            System.out.println("\nYour backpack is empty.");
        } else {
            System.out.println("These are your remaining items in the backpack:");
            for (int i = 0; i < backpack.getItemsOwned().size(); i++) {
                System.out.println((i + 1) + ". " + backpack.getItemsOwned().get(i));
            }
        }        
    }

    /**
     * EFFECTS: Renames an item in the backpack based on user input.
     * MODIFIES: this, Backpack
     */
    public void renameAnItem() {
        System.out.println("Which item do you want to rename? (number of the item in the list)");
        int renamedItem = scanner.nextInt();
        System.out.println("\nWhat name do you want to rename [" 
                + backpack.getItemsOwned().get(renamedItem - 1).getItemName() + "] ?");
        System.out.print("Input new name: ");

        scanner.nextLine(); // Consume the leftover newline
        String newName = scanner.nextLine();

        if (newName.length() == 0) {
            System.out.println("You need to enter a new name!");
        } else {
            backpack.getItemsOwned().get(renamedItem - 1).renameItem(newName);
            System.out.println("\nYour [" 
                    + backpack.getItemsOwned().get(renamedItem - 1).getItemName() 
                    + "] is renamed to [" 
                    + backpack.getItemsOwned().get(renamedItem - 1).getItemName() + "]");
        }

        System.out.println("\nThese are your items in the backpack:");
        for (int i = 0; i < backpack.getItemsOwned().size(); i++) {
            System.out.println((i + 1) + ". " + backpack.getItemsOwned().get(i));
        }
    }

    /**
     * EFFECTS: Provides options to save progress or quit without saving.
     * MODIFIES: this
     * @return false when the game ends.
     */
    public boolean donePlaying() {
        System.out.println("\nOptions:");
        System.out.println("1. Save your Progress");
        System.out.println("2. Quit without saving your Progress");
        System.out.print("\nChoose an option: ");
        int choices = scanner.nextInt();
        System.out.println();

        switch (choices) {
            case 1:
                saveProgress();
                System.out.println("Goodbye! Thanks for playing.");
                return false;

            case 2:
                System.out.println("Goodbye! Thanks for playing.");
                return false;

            default:
                return true;
        }
    }

    /**
     * EFFECTS: Saves the user's progress to a JSON file.
     * MODIFIES: this
     */
    private void saveProgress() {
        try {
            jsonWriter.open();
            jsonWriter.write(backpack, bankAccount);
            jsonWriter.close();
            System.out.println("Your progress is saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    public static void main(String[] args) throws Exception {
        new SimpleRouletteWheel();
    }
}
