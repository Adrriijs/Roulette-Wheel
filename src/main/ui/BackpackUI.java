package ui;

import javax.swing.*;
import model.Backpack;
import model.BankAccount;
import model.Item;

import java.awt.*;

/**
 * Represents a simplified backpack GUI with Sell, Rename, and View options.
 */
public class BackpackUI extends JFrame {
    private Backpack backpack;
    private BankAccount bankAccount;
    private Runnable onBalanceUpdate;

    /**
     * EFFECTS: Initializes a window for the backpack GUI.
     */
    public BackpackUI(Backpack backpack, BankAccount bankAccount, Runnable onBalanceUpdate) {
        this.backpack = backpack;
        this.bankAccount = bankAccount;
        this.onBalanceUpdate = onBalanceUpdate;

        setTitle("BACKPACK");
        setSize(600, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        refreshUI();

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: refreshes the UI to show current items in the backpack
    private void refreshUI() {
        getContentPane().removeAll(); // Clear the frame

        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));

        if (backpack.getItemsOwned().isEmpty()) {
            JLabel emptyLabel = new JLabel("Your backpack is empty.");
            emptyLabel.setFont(new Font("Arial", Font.BOLD, 16));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemsPanel.add(emptyLabel);
        } else {
            for (int i = 0; i < backpack.getItemsOwned().size(); i++) {
                Item item = backpack.getItemsOwned().get(i);
                itemsPanel.add(createItemPanel(item, i));
            }
        }

        add(new JScrollPane(itemsPanel), BorderLayout.CENTER);
        revalidate(); // Refresh UI layout
        repaint();
    }

    // EFFECTS: creates a panel for a single item with sell, rename, and view buttons
    private JPanel createItemPanel(Item item, int index) {
        JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel itemLabel = new JLabel(item.toString());
        JButton sellButton = new JButton("Sell");
        JButton renameButton = new JButton("Rename");
        JButton viewButton = new JButton("View");

        // Sell Button Action
        sellButton.addActionListener(e -> handleSellItem(index));

        // Rename Button Action
        renameButton.addActionListener(e -> handleRenameItem(index));

        // View Button Action
        viewButton.addActionListener(e -> handleViewItem(item));

        itemPanel.add(itemLabel);
        itemPanel.add(sellButton);
        itemPanel.add(renameButton);
        itemPanel.add(viewButton);

        return itemPanel;
    }

    // MODIFIES: backpack, bankAccount
    // EFFECTS: sells an item, updates balance, and refreshes the UI
    private void handleSellItem(int index) {
        // Use Backpack's sellItem method
        Item soldItem = backpack.sellItem(index, bankAccount);
    
        if (soldItem != null) {
            int sellPrice = (int) (soldItem.getItemPrice() * 0.8); // 80% of original price
            JOptionPane.showMessageDialog(this, "You sold " + soldItem.getItemName() + " for $" + sellPrice + ".");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to sell the item. Please try again.");
        }
    
        notifyBalanceUpdate();
        refreshUI(); // Refresh the UI to show updated backpack
    }

    // MODIFIES: backpack
    // EFFECTS: renames an item in the backpack with a new name entered by the user
    private void handleRenameItem(int index) {
        String newName = JOptionPane.showInputDialog(this, "Enter a new name for the item:");
        if (newName != null && !newName.trim().isEmpty()) {
            Item item = backpack.getItemsOwned().get(index);
            item.renameItem(newName.trim()); // Rename the item
            JOptionPane.showMessageDialog(this, "Item renamed successfully to " + newName + "!");
            refreshUI(); // Refresh the UI
        } else {
            JOptionPane.showMessageDialog(this, "Invalid name! Item name was not changed.");
        }
    }

    // EFFECTS: displays an image and details of the selected item in a new window
    private void handleViewItem(Item item) {
        JFrame viewFrame = new JFrame("View Item");
        viewFrame.setSize(400, 450);
        viewFrame.setLayout(new BorderLayout());
    
        JLabel itemImage = new JLabel();
        itemImage.setHorizontalAlignment(SwingConstants.CENTER);

        itemImage.setIcon(chooseImage(item));
    
        JLabel itemDetails = new JLabel(item.toString());
        itemDetails.setHorizontalAlignment(SwingConstants.CENTER);
        itemDetails.setFont(new Font("Arial", Font.BOLD, 14));
    
        viewFrame.add(itemImage, BorderLayout.CENTER);
        viewFrame.add(itemDetails, BorderLayout.SOUTH);
    
        viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewFrame.setLocationRelativeTo(null); // Center the view frame
        viewFrame.setVisible(true);
    }

    // EFFECTS: choose an image to display
    public ImageIcon chooseImage(Item item) {
        int holder = item.getItemPrice();

        ImageIcon image = null;

        if (holder == 5000) {
            image = new ImageIcon(getClass().getClassLoader().getResource("ui/Images/coke.jpg"));
        } else if (holder == 35000) {
            image = new ImageIcon(getClass().getClassLoader().getResource("ui/Images/laptop.jpg"));
        } else if (holder == 60000) {
            image = new ImageIcon(getClass().getClassLoader().getResource("ui/Images/HTicket.jpg"));
        } else if (holder == 180000) {
            image = new ImageIcon(getClass().getClassLoader().getResource("ui/Images/car.jpg"));
        } else {
            image = new ImageIcon(getClass().getClassLoader().getResource("ui/Images/NSwitch.jpg"));
        }

        return image;
    }

    // EFFECTS: runs the balance update callback
    private void notifyBalanceUpdate() {
        if (onBalanceUpdate != null) {
            onBalanceUpdate.run();
        }
    }
}