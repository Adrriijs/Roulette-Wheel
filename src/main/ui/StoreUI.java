package ui;

import javax.swing.*;
import java.awt.*;

import model.*;

/**
 * Represents a store GUI.
 */
public class StoreUI extends JFrame {
    private RewardShop rewardShop;
    private BankAccount bankAccount;
    private Backpack backpack;
    private JLabel balanceLabel;
    private Runnable onBalanceUpdate;

    /**
     * EFFECTS: Initializes a window for store GUI.
     */
    public StoreUI(BankAccount bankAccount, Backpack backpack, Runnable onBalanceUpdate) {
        this.rewardShop = new RewardShop();
        this.bankAccount = bankAccount;
        this.backpack = backpack;
        this.onBalanceUpdate = onBalanceUpdate;

        setTitle("REWARD STORE");
        setSize(400, 250);
        setLayout(new BorderLayout());
        setResizable(false);

        setUpStore();        

        add(createItemsPanel(), BorderLayout.CENTER);
        add(createBalancePanel(), BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    // EFFECTS: initializes items in the store
    private void setUpStore() {
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

    // EFFECTS: creates and returns a panel showing items in the store
    private JPanel createItemsPanel() {
        JPanel itemsPanel = new JPanel();
        itemsPanel.setOpaque(false);
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < rewardShop.getItems().size(); i++) {
            Item item = rewardShop.getItems().get(i);
            JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel itemLabel = new JLabel(item.toString());
            JButton buyButton = new JButton("Buy");
            final int index = i;

            buyButton.addActionListener(e -> handlePurchase(index));
            itemPanel.add(itemLabel);
            itemPanel.add(buyButton);
            itemsPanel.add(itemPanel);
        }

        return itemsPanel;
    }

    // EFFECTS: creates and returns a panel showing the user's current balance
    private JPanel createBalancePanel() {
        JPanel balancePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        balancePanel.setBackground(Color.DARK_GRAY);
        balanceLabel = new JLabel("Balance : $" + bankAccount.getBalance());
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balancePanel.add(balanceLabel);

        return balancePanel;
    }

    // REQUIRES: index is valid for the items list
    // MODIFIES: bankAccount, backpack
    // EFFECTS: handles the purchase of an item and updates balance and backpack,
    //          shows a message if the purchase is successful and update balance 
    //          in main page or if there are insufficient funds
    private void handlePurchase(int index) {
        boolean success = rewardShop.purchaseItem(index, bankAccount, backpack);

        if (success) {
            JOptionPane.showMessageDialog(this, "Item purchased successfully!");
            updateBalanceDisplay(bankAccount);
            notifyBalanceUpdate();
        } else {
            JOptionPane.showMessageDialog(this, "Insufficient balance to purchase this item.");
        }
    }

    // REQUIRES: bankAccount is not null
    // MODIFIES: this
    // EFFECTS: updates the balance display with the current balance from bankAccount
    private void updateBalanceDisplay(BankAccount bankAccount) {
        balanceLabel.setText("$" + bankAccount.getBalance());
    }

    // EFFECTS: runs the balance update callback to notify the main UI
    private void notifyBalanceUpdate() {
        if (onBalanceUpdate != null) {
            onBalanceUpdate.run();
        }
    }
}
