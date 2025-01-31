package ui;

import model.Backpack;
import model.BankAccount;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MenuUI extends JFrame {
    private static final String JSON_STORE = "./data/progress.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Backpack backpack;      
    private BankAccount bankAccount; 
    protected Object[] objects;

    /**
     * EFFECTS: Initializes the menu UI with Load and Save buttons.
     */
    public MenuUI(Backpack backpack, BankAccount bankAccount) {
        this.backpack = backpack;
        this.bankAccount = bankAccount;
        setTitle("Menu");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize JsonReader and JsonWriter
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        // Initialize the backpack and bank account (defaults or from code)
        backpack = new Backpack();
        bankAccount = new BankAccount(1000); // Example: starting balance

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
            objects = jsonReader.read(); // Read data
            JOptionPane.showMessageDialog(this, "Progress loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to load progress: " + e.getMessage());
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
}