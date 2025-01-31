package ui;

import javax.swing.*;

import model.*;
import model.exception.LogException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Represents a simple roulette wheel game with betting and shop functionalities in GUI.
 */
public class SimpleRouletteWheelUI extends JFrame implements ActionListener {
    private RouletteWheel rouletteWheel;
    private BankAccount bankAccount;
    private Backpack backpack;
    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;
    private JButton storeButton;
    private JButton backpackButton;
    private JButton menuButton;
    private RoundedPanel payoutPanel;
    private JLabel balance;
    private JTextField numberResultField;
    private JTextField colorResultField;
    private JTextField amountField;
    private JTextField betField;
    protected Object[] objects;
    private static final String JSON_STORE = "./data/progress.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // REQUIRES: valid bet selection and amount entered
    // EFFECTS: constructs the roulette game UI, initializes components and displays the main window
    public SimpleRouletteWheelUI(Backpack backpack, BankAccount bankAccount) {
        super("SIMPLE ROULETTE WHEEL");
        rouletteWheel = new RouletteWheel();
        this.bankAccount = bankAccount;
        this.backpack = backpack;

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); // Prevent immediate close

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleWindowClosing(); // Use ScreenPrinter to display logs
            }
        });

        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setLayout(null);

        addNavigationComponent();
        makeBetChoiceLabel();
        addResultComponent();
        addBetComponent();

        updateBalanceDisplay(bankAccount);

        setVisible(true);
    }

    // REQUIRES: bankAccount is not null
    // MODIFIES: this
    // EFFECTS: updates the balance display with the current balance from bankAccount
    private void updateBalanceDisplay(BankAccount bankAccount) {
        balance.setText("$" + bankAccount.getBalance());
    }

    // EFFECTS: adds the result display components to the UI
    public void addResultComponent() {
        JPanel resultPanel = new JPanel();
        resultPanel.setBackground(new Color(199, 255, 171));
        resultPanel.setBounds(0, 0, 900, 400);
        resultPanel.setLayout(null);

        ImageIcon rouletteWheel = new ImageIcon(getClass().getClassLoader().getResource("ui/Images/roulette.png"));
        JLabel wheelLabel = new JLabel(rouletteWheel);
        wheelLabel.setBounds(0, 0, 400, 400);
        resultPanel.add(wheelLabel);

        addResult(resultPanel);
        makeRollButton(resultPanel);
        add(resultPanel);
    }

    // REQUIRES: resultPanel is not null
    // MODIFIES: resultPanel
    // EFFECTS: creates and adds a roll button to the result panel
    public void makeRollButton(JPanel resultPanel) {
        JButton rollButton = new JButton("ROLL");
        rollButton.setFont(new Font("Arial", Font.BOLD, 50));
        rollButton.setPreferredSize(new Dimension(190, 20));
        rollButton.setBounds(400, 300, 180, 80);
        rollButton.setBackground(Color.GREEN);
        rollButton.setForeground(Color.WHITE);
        rollButton.addActionListener(e -> handleRoll());
        resultPanel.add(rollButton);
    }

    // REQUIRES: resultPanel is not null
    // MODIFIES: resultPanel
    // EFFECTS: creates and adds the result label and result fields to the result panel
    public void addResult(JPanel resultPanel) {
        JLabel resultLabel = new JLabel("RESULT");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 35));
        resultLabel.setBounds(425, 70, 150, 30);
        resultPanel.add(resultLabel);

        numberResultField = new JTextField();
        numberResultField.setEditable(false);
        numberResultField.setBounds(443, 120, 100, 50);
        numberResultField.setFont(new Font("Arial", Font.PLAIN, 18));
        numberResultField.setHorizontalAlignment(JTextField.CENTER);
        resultPanel.add(numberResultField);

        colorResultField = new JTextField();
        colorResultField.setEditable(false);
        colorResultField.setBounds(443, 190, 100, 50);
        colorResultField.setHorizontalAlignment(JTextField.CENTER);
        colorResultField.setFont(new Font("Arial", Font.PLAIN, 18));
        resultPanel.add(colorResultField);
    }

    // MODIFIES: this
    // EFFECTS: adds the bet table and color buttons to the UI
    public void addBetComponent() {
        JPanel betPanel = new JPanel();
        betPanel.setBackground(Color.DARK_GRAY);
        betPanel.setBounds(0, 435, 900, 136);
        betPanel.setLayout(null);

        addBetTable(betPanel);

        add(betPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates and adds the bet selection and amount input panel to the UI
    public void makeBetChoiceLabel() {
        JPanel choicePanel = new JPanel();
        choicePanel.setBackground(Color.DARK_GRAY);
        choicePanel.setBounds(0, 400, 900, 37);
        choicePanel.setLayout(null);

        JLabel playerBetLabel = new JLabel("You bet on : ");
        playerBetLabel.setFont(new Font("Arial", Font.BOLD, 15));
        playerBetLabel.setBounds(10, 5, 100, 30);
        playerBetLabel.setForeground(Color.WHITE);
        choicePanel.add(playerBetLabel);

        betField = new JTextField();
        betField.setEditable(false);
        betField.setBounds(100, 5, 100, 27);
        choicePanel.add(betField);

        makeAmountToBetLabel(choicePanel);

        add(choicePanel);
    }

    // REQUIRES: choicePanel is not null
    // MODIFIES: choicePanel
    // EFFECTS: creates and adds the amount input label and field to the choice panel
    public void makeAmountToBetLabel(JPanel choicePanel) {
        JLabel labelStrip = new JLabel("|||");
        labelStrip.setFont(new Font("Arial", Font.BOLD, 25));
        labelStrip.setBounds(213, 3, 30, 30);
        labelStrip.setForeground(Color.WHITE);
        choicePanel.add(labelStrip);
        
        JLabel amountBetLabel = new JLabel("Place the amount of bet : ");
        amountBetLabel.setFont(new Font("Arial", Font.BOLD, 15));
        amountBetLabel.setBounds(245, 5, 200, 30);
        amountBetLabel.setForeground(Color.WHITE);
        choicePanel.add(amountBetLabel);

        amountField = new JTextField();
        amountField.setBounds(428, 5, 165, 27);
        amountField.setHorizontalAlignment(JTextField.CENTER);
        amountField.setFont(new Font("Arial", Font.PLAIN, 18));
        choicePanel.add(amountField);
    }

    // REQUIRES: betPanel is not null
    // MODIFIES: betPanel
    // EFFECTS: creates and adds the number bet buttons and color buttons to the bet panel
    public void addBetTable(JPanel betPanel) {
        JPanel table = new JPanel();
        table.setBounds(5, 2, 592, 95);
        table.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 3));

        makeNumberTable(table);

        betPanel.add(table);
        betPanel.add(addColorButton());
    }

    // REQUIRES: table is not null
    // MODIFIES: table
    // EFFECTS: creates and adds number buttons to the bet table
    public void makeNumberTable(JPanel table) {
        for (int i = 0; i <= 36; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.setPreferredSize(new Dimension(53, 20));
            button.setForeground(Color.WHITE);
            if (i == 0) {
                button.setBackground(Color.GREEN);
            } else if ((i <= 10) || ((i >= 19) && (i <= 28))) {
                button.setBackground(i % 2 == 0 ? Color.BLACK : Color.RED);
            } else {
                button.setBackground(i % 2 == 0 ? Color.RED : Color.BLACK);
            }

            button.addActionListener(e -> handleBet(button.getText()));
            table.add(button);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates and adds color buttons to the UI
    public JPanel addColorButton() {
        JPanel colorTable = new JPanel();
        colorTable.setBounds(5, 95, 592, 26);
        colorTable.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
    
        String[] colors = {"BLACK", "RED", "GREEN"};
        for (String color : colors) {
            JButton colorButton = new JButton(color);
            colorButton.setPreferredSize(new Dimension(190, 20));
            colorButton.setForeground(Color.WHITE);

            Color colorNeeded = null;
            if (color.equals("RED")) {
                colorNeeded = Color.RED;
            } else if (color.equals("BLACK")) {
                colorNeeded = Color.BLACK;
            } else {
                colorNeeded = Color.GREEN;
            }

            colorButton.setBackground(colorNeeded);

            colorButton.addActionListener(e -> handleBet(color));
            colorTable.add(colorButton);
        }
        return colorTable;
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: creates a navigation panel, adds balance panel, payout panel, 
    //          store button, backpack button, and menu button 
    //          to the navigation panel, and adds it to the main UI
    public void addNavigationComponent() {
        RoundedPanel result = new RoundedPanel(40);
        result.setBackground(new Color(0, 117, 75));
        result.setBounds(602, 4, 278, 551);
        result.setLayout(null);

        result.add(balancePanel());
        result.add(payoutPanel());
        result.add(storeButton());
        result.add(backpackButton());
        result.add(menuButton());
        add(result);
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: creates and returns a RoundedPanel displaying the user's balance,
    //          with a title label and the current balance amount in the center
    public RoundedPanel balancePanel() {
        RoundedPanel balancePanel = new RoundedPanel(40);
        balancePanel.setBounds(15, 20, 250, 80);
        balancePanel.setBackground(Color.BLACK);
        balancePanel.setLayout(new BoxLayout(balancePanel, BoxLayout.Y_AXIS));

        // Balance title
        JLabel balanceTitle = new JLabel("BALANCE:"); 
        balanceTitle.setFont(new Font("Arial", Font.BOLD, 20));
        balanceTitle.setForeground(Color.WHITE);
        balanceTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        balancePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        balancePanel.add(balanceTitle);

        // Balance value
        balance = new JLabel("$0");
        balance.setFont(new Font("Arial", Font.PLAIN, 18));
        balance.setForeground(Color.WHITE);
        balance.setAlignmentX(Component.CENTER_ALIGNMENT);
        balancePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        balancePanel.add(balance);

        return balancePanel;
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: creates and returns a RoundedPanel displaying payout rates for different bets,
    //          with payout labels for each category
    public RoundedPanel payoutPanel() {
        payoutPanel = new RoundedPanel(40);
        payoutPanel.setBounds(15, 115, 250, 180);
        payoutPanel.setLayout(new BoxLayout(payoutPanel, BoxLayout.Y_AXIS));
        payoutPanel.setBackground(Color.DARK_GRAY);

        addPayoutLabels();
        return payoutPanel;
    }

    // REQUIRES: payoutPanel is initialized
    // MODIFIES: payoutPanel
    // EFFECTS: adds labels for payout information to the payout panel
    private void addPayoutLabels() {
        Font font = new Font("Arial", Font.BOLD, 18);

        payoutPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Top padding
        payoutPanel.add(createLabel("PAYOUT", font, Color.YELLOW));
        payoutPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing
        payoutPanel.add(createLabel("Green = 1:35", font, Color.WHITE));
        payoutPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        payoutPanel.add(createLabel("Black = 1:1", font, Color.WHITE));
        payoutPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        payoutPanel.add(createLabel("Red = 1:1", font, Color.WHITE));
        payoutPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        payoutPanel.add(createLabel("Number = 1:35", font, Color.WHITE));
    }

    // REQUIRES: text is not null, font is not null, color is not null
    // EFFECTS: creates and returns a JLabel with the given text, font, and color,
    //          aligned to the center horizontally
    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: creates and returns a button labeled "STORE", 
    //          configured to handle click events
    public JButton storeButton() {
        storeButton = new JButton("STORE");
        storeButton.addActionListener(this);
        storeButton.setBounds(15, 315, 250, 60);
        return storeButton;
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: creates and returns a button labeled "BACKPACK", 
    //          configured to handle click events
    public JButton backpackButton() {
        backpackButton = new JButton("BACKPACK");
        backpackButton.addActionListener(this);
        backpackButton.setBounds(15, 395, 250, 60);
        return backpackButton;
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: creates and returns a button labeled "MENU", 
    //          configured to handle click events
    public JButton menuButton() {
        menuButton = new JButton("MENU");
        menuButton.addActionListener(this);
        menuButton.setBounds(15, 475, 250, 60);
        return menuButton;
    }

    // REQUIRES: e is not null
    // MODIFIES: this
    // EFFECTS: handles click events for storeButton, backpackButton, and menuButton,
    //          displaying a dialog message based on the button clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == storeButton) {
            new StoreUI(bankAccount, backpack, () -> updateBalanceDisplay(bankAccount));
        } else if (e.getSource() == backpackButton) {
            new BackpackUI(backpack, bankAccount, () -> updateBalanceDisplay(bankAccount));
        } else if (e.getSource() == menuButton) {
            showMenu(backpack, bankAccount);
        }
    }

    // REQUIRES: bet is a valid number or color string
    // MODIFIES: this
    // EFFECTS: sets the betField text to the selected bet value
    private void handleBet(String bet) {
        betField.setText(bet);
        betField.setFont(new Font("Arial", Font.PLAIN, 18));
        betField.setHorizontalAlignment(JTextField.CENTER);
    }

    // REQUIRES: betField and amountField are not empty, amountField contains a valid integer
    // MODIFIES: this, bankAccount
    // EFFECTS: processes the roulette spin, calculates the result, updates balance, and notifies the user of win/loss
    private void handleRoll() {
        
        String bet = validateBet();
        if (bet == null) {
            return;
        }

        int amount = validateAmount();
        if (amount == -1) {
            return;
        }

        processSpin(bet, amount);
    }    

    // REQUIRES: betField is initialized
    // EFFECTS: returns the bet selected by the user; shows an error dialog if no bet is selected
    private String validateBet() {
        String bet = betField.getText();
        if (bet.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a bet!");
            return null;
        }
        return bet;
    }

    // REQUIRES: amountField is initialized
    // EFFECTS: returns the amount entered by the user if valid; 
    //          shows an error dialog for empty or invalid input, or if amount exceeds balance
    private int validateAmount() {
        String amountText = amountField.getText();
        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an amount!");
            return -1;
        }

        try {
            int amount = Integer.parseInt(amountText);
            if (amount > bankAccount.getBalance()) {
                JOptionPane.showMessageDialog(this, "Insufficient balance!");
                return -1;
            }
            return amount;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount entered!");
            return -1;
        }
    }

    // REQUIRES: bet is a valid number or color; amount is a positive integer
    // MODIFIES: this
    // EFFECTS: spins the roulette wheel, updates the result fields, calculates the result, and updates the balance
    private void processSpin(String bet, int amount) {
        rouletteWheel.spinTheWheel();
        int winningNumber = rouletteWheel.getWinningNumber();
        String winningColor = rouletteWheel.getWinningColor();
    
        updateResultFields(winningNumber, winningColor);
        calculateResult(bet, amount, winningNumber, winningColor);
    }

    // REQUIRES: winningNumber is between 0-36, winningColor is "red", "black", or "green"
    // MODIFIES: numberResultField, colorResultField
    // EFFECTS: updates the result fields with the winning number and color, 
    //          and sets the background color of the color result field
    private void updateResultFields(int winningNumber, String winningColor) {
        numberResultField.setText(String.valueOf(winningNumber));
        colorResultField.setText(winningColor.toUpperCase());

        Color color = null;
        if (winningColor.equals("red")) {
            color = Color.RED;
        } else if (winningColor.equals("black")) {
            color = Color.BLACK;
        } else {
            color = Color.GREEN;
        }

        colorResultField.setBackground(color);
        colorResultField.setForeground(winningColor.equals("green") ? Color.BLACK : Color.WHITE);
    }

    // REQUIRES: bet is a valid number or color; amount > 0; 
    //           winningNumber is between 0-36; winningColor is "red", "black", or "green"
    // MODIFIES: this, bankAccount
    // EFFECTS: determines if the user wins or loses the bet, updates the balance, 
    //          and shows a dialog indicating the result
    private void calculateResult(String bet, int amount, int winningNumber, String winningColor) {
        boolean isWin = checkWin(bet, winningNumber, winningColor);
    
        if (isWin) {
            int payout = calculatePayout(bet, amount);
            bankAccount.deposit(payout - amount); 
            JOptionPane.showMessageDialog(this, "You win! Payout: $" + payout);
        } else {
            bankAccount.subtract(amount);
            JOptionPane.showMessageDialog(this, "You lose! Amount deducted: $" + amount);
        }
    
        updateBalanceDisplay(bankAccount);
    }

    // REQUIRES: bet is a valid number or color; 
    //           winningNumber is between 0-36; winningColor is "red", "black", or "green"
    // EFFECTS: returns true if the bet matches the winning number or color, false otherwise
    private boolean checkWin(String bet, int winningNumber, String winningColor) {
        if (bet.matches("\\d+")) { 
            return Integer.parseInt(bet) == winningNumber;
        } else { 
            return bet.equalsIgnoreCase(winningColor);
        }
    }
    
    // REQUIRES: bet is a valid number or color; amount > 0
    // EFFECTS: returns the payout amount based on the type of bet (number: 35:1, color: 1:1)
    private int calculatePayout(String bet, int amount) {
        if (bet.matches("\\d+")) {
            return amount * 35;
        } else {
            return amount * 2; 
        }
    }
    
    /**
     * EFFECTS: Initializes the menu UI with Load and Save buttons.
     */
    public void showMenu(Backpack backpack, BankAccount bankAccount) {
        this.backpack = backpack;
        this.bankAccount = bankAccount;
        setTitle("Menu");
        setSize(400, 200);
        setLayout(new BorderLayout());

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        // Create buttons
        JButton loadButton = new JButton("Load Progress");
        JButton saveButton = new JButton("Save Progress");

        // Add action listeners to buttons
        loadButton.addActionListener(e -> handleLoadProgress());
        saveButton.addActionListener(e -> handleSaveProgress());

        // Add buttons to the center of the window in a panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0)); // Horizontal layout
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Center the frame
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * EFFECTS: Handles the Load Progress button click.
     */
    private void handleLoadProgress() {
        try {
            // Attempt to read data from JSON
            objects = jsonReader.read();
            
            // Ensure that objects are properly loaded
            if (objects != null && objects.length >= 2) {
                bankAccount = (BankAccount) objects[0]; // First object is BankAccount
                backpack = (Backpack) objects[1];       // Second object is Backpack
                
                // Notify the user of success
                JOptionPane.showMessageDialog(this, "Progress loaded successfully!");
                
                // Close the current menu and open the main UI with loaded progress
                dispose();
                new SimpleRouletteWheelUI(backpack, bankAccount);
            } else {
                // Handle incomplete or missing data
                JOptionPane.showMessageDialog(this, "Failed to load progress: Incomplete data.");
            }
        } catch (IOException e) {
            // Handle IOExceptions during file reading
            JOptionPane.showMessageDialog(this, "Failed to load progress: " + e.getMessage());
        } catch (ClassCastException e) {
            // Handle cases where casting fails
            JOptionPane.showMessageDialog(this, "Failed to load progress: Data format is invalid.");
        }
    }


    /**
     * EFFECTS: Handles the Save Progress button click.
     */
    private void handleSaveProgress() {
        try {
            jsonWriter.open(); // Open the writer
            jsonWriter.write(backpack, bankAccount); // Save data
            jsonWriter.close(); // Close the writer
            JOptionPane.showMessageDialog(this, "Progress saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save progress: " + e.getMessage());
        }
    }

    /**
     * EFFECTS: Create and display the ScreenPrinter to show the event log
     */
    private void handleWindowClosing() {
        // Create and display the ScreenPrinter to show the event log
        try {
            LogPrinter screenPrinter;
            screenPrinter = new ScreenPrinter(SimpleRouletteWheelUI.this);
            add((ScreenPrinter) screenPrinter);
            screenPrinter.printLog(EventLog.getInstance());
        } catch (LogException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
						JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleRouletteWheelUI(new Backpack(), new BankAccount(10000)));
    }
}
