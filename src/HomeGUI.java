package src;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class HomeGUI {
    private JFrame homeFrame;
    private JTextField areaField;
    private JComboBox<String> disasterType;

    public HomeGUI() {
        setupHomeGUI();
    }

    private void setupHomeGUI() {
        homeFrame = new JFrame("Disaster Information");
        homeFrame.setSize(400, 200);
        homeFrame.setLocationRelativeTo(null);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setLayout(new BorderLayout(10, 10));

        homeFrame.getContentPane().setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("Disaster Relief Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(25, 25, 112));
        homeFrame.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBackground(new Color(240, 248, 255));
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(240, 248, 255));

        disasterType = new JComboBox<>(new String[]{"Earthquake", "Flood", "Hurricane"});
        disasterType.setPreferredSize(new Dimension(150, 25));
        areaField = new JTextField();
        areaField.setPreferredSize(new Dimension(150, 25));

        JButton nextButton = new JButton("Next");
        nextButton.setBackground(new Color(0, 128, 128));
        nextButton.setForeground(Color.WHITE);
        JButton quitButton = new JButton("Quit");
        quitButton.setBackground(new Color(220, 20, 60));
        quitButton.setForeground(Color.WHITE);

        nextButton.addActionListener(e -> {
                    String selectedDisaster = (String) disasterType.getSelectedItem();
                    String selectedArea = areaField.getText().trim();
                    if (selectedArea.isEmpty()) {
                        JOptionPane.showMessageDialog(homeFrame, "Please enter an area name.");
                        return;
                    }
                    homeFrame.dispose();
                    new MainGUI(selectedDisaster, selectedArea);
            });

        quitButton.addActionListener(e -> System.exit(0));

        inputPanel.add(new JLabel("Disaster Type:", JLabel.RIGHT));
        inputPanel.add(disasterType);
        inputPanel.add(new JLabel("Area Name:", JLabel.RIGHT));
        inputPanel.add(areaField);

        buttonPanel.add(nextButton);
        buttonPanel.add(quitButton);

        homeFrame.add(inputPanel, BorderLayout.CENTER);
        homeFrame.add(buttonPanel, BorderLayout.SOUTH);
        homeFrame.setVisible(true);
    }
}
