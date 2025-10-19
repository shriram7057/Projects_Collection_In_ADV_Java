import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*; // Import for JScrollPane and layout improvements

public class ExpenseTracker extends JFrame implements ActionListener {
    JTextField nameField, amountField;
    JTextArea displayArea;
    JButton addButton, showButton;
    JLabel totalLabel; // NEW: Label to show total expense

    // File name constant
    private static final String EXPENSE_FILE = "expenses.txt";

    ExpenseTracker() {
        setTitle("Expense Tracker 💰");
        setSize(400, 450); // Increased height to accommodate the total label
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Added to ensure the app closes properly
        setLayout(null);

        // --- Input Labels and Fields ---
        JLabel n = new JLabel("Item:");
        JLabel a = new JLabel("Amount (INR):");
        n.setBounds(30, 30, 100, 30);
        a.setBounds(30, 70, 100, 30);
        add(n);
        add(a);

        nameField = new JTextField();
        amountField = new JTextField();
        nameField.setBounds(120, 30, 150, 25);
        amountField.setBounds(120, 70, 150, 25);
        add(nameField);
        add(amountField);

        // --- Buttons ---
        addButton = new JButton("Add Expense");
        showButton = new JButton("Show All & Total");
        addButton.setBounds(30, 110, 130, 30);
        showButton.setBounds(170, 110, 150, 30);
        add(addButton);
        add(showButton);

        // --- Display Area (Using JScrollPane for better view) ---
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBounds(30, 160, 330, 180); // Adjusted size and position
        add(scrollPane);

        // --- Total Expense Label (NEW INTERACTIVE ELEMENT) ---
        totalLabel = new JLabel("Total Expense: 0.00");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalLabel.setForeground(Color.RED);
        totalLabel.setBounds(30, 360, 330, 30);
        add(totalLabel);

        // --- Event Listeners ---
        addButton.addActionListener(this);
        showButton.addActionListener(this);

        // Initial call to show total if file exists
        calculateAndDisplayTotal();

        setVisible(true);
    }

    // --- Action Listener Method ---
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addExpense();
        } else if (e.getSource() == showButton) {
            showExpenses();
        }
        // Always update the total after any action
        calculateAndDisplayTotal();
    }

    // --- New Method: Calculates and displays the total expense ---
    private void calculateAndDisplayTotal() {
        double total = 0.0;

        // Use try-with-resources for safe file handling
        try (BufferedReader br = new BufferedReader(new FileReader(EXPENSE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Example line: "Coffee - 35.50"
                String[] parts = line.split(" - ");
                if (parts.length == 2) {
                    try {
                        total += Double.parseDouble(parts[1].trim());
                    } catch (NumberFormatException e) {
                        // Skip lines with invalid amount format
                        System.err.println("Skipping invalid amount in line: " + line);
                    }
                }
            }
            // Format the total to two decimal places
            totalLabel.setText(String.format("Total Expense: ₹ %.2f", total));
        } catch (FileNotFoundException e) {
            // This is normal if the file doesn't exist yet
            totalLabel.setText("Total Expense: ₹ 0.00");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Enhanced addExpense method with validation and safe file handling ---
    private void addExpense() {
        String item = nameField.getText().trim();
        String amountText = amountField.getText().trim();

        if (item.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Item and Amount cannot be empty.", "Input Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // NEW: Input Validation
            double amountValue = Double.parseDouble(amountText);
            if (amountValue <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be a positive number.", "Input Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Use try-with-resources for safe file handling
            try (FileWriter fw = new FileWriter(EXPENSE_FILE, true)) {
                fw.write(item + " - " + String.format("%.2f", amountValue) + "\n");
                JOptionPane.showMessageDialog(this, "Expense Added! ✔️", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear fields
                nameField.setText("");
                amountField.setText("");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error writing to file: " + ex.getMessage(), "File Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Enhanced showExpenses method with safe file handling ---
    private void showExpenses() {
        displayArea.setText("");
        // Use try-with-resources for safe file handling
        try (BufferedReader br = new BufferedReader(new FileReader(EXPENSE_FILE))) {
            String line;
            displayArea.append("Item\t\tAmount\n" + "--------------------------------------\n");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" - ");
                if (parts.length == 2) {
                    // Format output nicely
                    displayArea.append(String.format("%-20s ₹ %s\n", parts[0], parts[1]));
                } else {
                    displayArea.append(line + " (Malformed Line)\n");
                }
            }
        } catch (FileNotFoundException e) {
            displayArea.setText("No expenses recorded yet.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage(), "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Best practice to run Swing apps on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new ExpenseTracker());
    }
}