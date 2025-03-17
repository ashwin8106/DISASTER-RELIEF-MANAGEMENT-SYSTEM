package src;

import javax.swing.*;
import java.awt.*;

public class MainGUI {
    private JFrame mainFrame;
    private JTextField personsCampField, affectedField;
    private JComboBox<String> areaType, foodDropdown;
    private JTextArea displayArea;
    private JLabel disasterLabel, areaLabel;

    private InventoryManager inventoryManager;

    public MainGUI(String selectedDisaster, String selectedArea)
    {
        inventoryManager = new InventoryManager(selectedArea);
        setupMainGUI(selectedDisaster, selectedArea);
    }

    private void setupMainGUI(String selectedDisaster, String selectedArea) 
    {
        mainFrame = new JFrame("Disaster Relief Management");
        mainFrame.setSize(600, 400);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout(10, 10));
        mainFrame.getContentPane().setBackground(new Color(240, 248, 255)); 

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBackground(new Color(240, 248, 255)); 
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255)); 
        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.setBackground(new Color(240, 248, 255)); 

        disasterLabel = new JLabel("Disaster: " + selectedDisaster, JLabel.CENTER);
        disasterLabel.setFont(new Font("Arial", Font.BOLD, 16));
        disasterLabel.setForeground(new Color(25, 25, 112)); 
        areaLabel = new JLabel("Area: " + selectedArea, JLabel.CENTER);
        areaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        areaLabel.setForeground(new Color(25, 25, 112)); 

        foodDropdown = new JComboBox<>(new String[]{"Meals", "Water", "Medicine", "Canned Food", "Blankets"});
        foodDropdown.setPreferredSize(new Dimension(120, 25));

        JButton addItemButton = new JButton("Add Item");
        addItemButton.setBackground(new Color(0, 128, 128)); 
        addItemButton.setForeground(Color.WHITE);
        JButton viewInventoryButton = new JButton("View Inventory");
        viewInventoryButton.setBackground(new Color(0, 128, 128)); 
        viewInventoryButton.setForeground(Color.WHITE);

        personsCampField = new JTextField();
        personsCampField.setPreferredSize(new Dimension(120, 25));
        affectedField = new JTextField();
        affectedField.setPreferredSize(new Dimension(120, 25));
        areaType = new JComboBox<>(new String[]{"Low", "Mid", "High"});
        areaType.setPreferredSize(new Dimension(120, 25));

        JButton allocateButton = new JButton("Allocate Supplies");
        allocateButton.setBackground(new Color(0, 128, 128)); 
        allocateButton.setForeground(Color.WHITE);
        JButton quitButton = new JButton("Quit");
        quitButton.setBackground(new Color(220, 20, 60)); 
        quitButton.setForeground(Color.WHITE);

        displayArea = new JTextArea(10, 30);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 14));
        displayArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        displayArea.setEditable(false);
        displayArea.setBackground(new Color(255, 250, 240)); 

        addItemButton.addActionListener(e -> openAddItemWindow());
        viewInventoryButton.addActionListener(e -> displayArea.setText(inventoryManager.viewInventory()));
        allocateButton.addActionListener(e -> allocateSupplies());
        quitButton.addActionListener(e -> System.exit(0));

        inputPanel.add(new JLabel("Food Item:"));
        inputPanel.add(foodDropdown);
        inputPanel.add(new JLabel("Persons in Camp:"));
        inputPanel.add(personsCampField);
        inputPanel.add(new JLabel("Heavily Affected Persons:"));
        inputPanel.add(affectedField);
        inputPanel.add(new JLabel("Area Type:"));
        inputPanel.add(areaType);

        buttonPanel.setLayout(new GridLayout(1, 4, 5, 5));
        buttonPanel.add(addItemButton);
        buttonPanel.add(viewInventoryButton);
        buttonPanel.add(allocateButton);
        buttonPanel.add(quitButton);

        displayPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(new Color(240, 248, 255)); 
        headerPanel.add(disasterLabel);
        headerPanel.add(areaLabel);

        mainFrame.add(headerPanel, BorderLayout.NORTH);
        mainFrame.add(inputPanel, BorderLayout.CENTER);
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);
        mainFrame.add(displayPanel, BorderLayout.EAST);

        mainFrame.setVisible(true);
    }

    private void openAddItemWindow() {
        JFrame addItemFrame = new JFrame("Add Item");
        addItemFrame.setSize(300, 150);
        addItemFrame.setLocationRelativeTo(mainFrame);
        addItemFrame.setLayout(new GridLayout(3, 2, 5, 5));
        addItemFrame.getContentPane().setBackground(new Color(240, 248, 255)); 

        JComboBox<String> itemDropdown = new JComboBox<>(new String[]{"Meals", "Water", "Medicine", "Canned Food", "Blankets"});
        JTextField quantityField = new JTextField();
        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(0, 128, 128)); 
        addButton.setForeground(Color.WHITE);

        addButton.addActionListener(e -> {
                    String item = (String) itemDropdown.getSelectedItem();
                    String quantityStr = quantityField.getText().trim();

                    try {
                        int quantity = Integer.parseInt(quantityStr);
                        if (quantity <= 0) {
                            JOptionPane.showMessageDialog(addItemFrame, "Quantity must be greater than zero.");
                            return;
                        }

                        inventoryManager.addItem(item, quantity);
                        JOptionPane.showMessageDialog(addItemFrame, item + " added: " + quantity + " units.");
                        addItemFrame.dispose();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(addItemFrame, "Invalid quantity! Enter a number.");
                    }
            });

        addItemFrame.add(new JLabel("Item:"));
        addItemFrame.add(itemDropdown);
        addItemFrame.add(new JLabel("Quantity:"));
        addItemFrame.add(quantityField);
        addItemFrame.add(new JLabel());
        addItemFrame.add(addButton);

        addItemFrame.setVisible(true);
    }

    private void allocateSupplies() {
        try {
            int personsCamp = Integer.parseInt(personsCampField.getText().trim());
            int affected = Integer.parseInt(affectedField.getText().trim());
            String areaTypeSelected = (String) areaType.getSelectedItem();
            String selectedItem = (String) foodDropdown.getSelectedItem();

            String allocationMessage = inventoryManager.allocateSupplies(selectedItem, personsCamp, affected, areaTypeSelected);
            displayArea.setText(inventoryManager.viewInventory());
            displayArea.append(allocationMessage);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame, "Invalid input! Please enter valid numbers.");
        }
    }
}
