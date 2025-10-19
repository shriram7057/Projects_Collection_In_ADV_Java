import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class OnlineBankingEnhanced extends JFrame {
    private JTextField accountField, amountField;
    private JTextArea display;
    private Map<String, Double> accounts = new HashMap<>();

    public OnlineBankingEnhanced() {
        setTitle("🏦 Online Banking System");
        setSize(550, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(230, 245, 255));

        JLabel title = new JLabel("Online Banking", JLabel.CENTER);
        title.setBounds(50, 10, 450, 40);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(new Color(0, 70, 140));
        add(title);

        add(new JLabel("Account No:")).setBounds(30, 70, 100, 25);
        add(new JLabel("Amount:")).setBounds(30, 110, 100, 25);

        accountField = new JTextField();
        accountField.setBounds(140, 70, 380, 25);
        amountField = new JTextField();
        amountField.setBounds(140, 110, 380, 25);
        accountField.setBackground(new Color(255, 255, 200));
        amountField.setBackground(new Color(255, 255, 200));
        accountField.setToolTipText("Enter account number");
        amountField.setToolTipText("Enter amount");

        add(accountField);
        add(amountField);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.setBounds(30, 150, 150, 35);
        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(200, 150, 150, 35);
        JButton showBtn = new JButton("Show All");
        showBtn.setBounds(370, 150, 150, 35);

        styleButton(depositBtn, new Color(0, 120, 215), Color.WHITE);
        styleButton(withdrawBtn, new Color(220, 50, 50), Color.WHITE);
        styleButton(showBtn, new Color(0, 150, 0), Color.WHITE);

        add(depositBtn);
        add(withdrawBtn);
        add(showBtn);

        display = new JTextArea();
        display.setEditable(false);
        display.setBackground(new Color(245, 245, 250));
        display.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane sp = new JScrollPane(display);
        sp.setBounds(30, 200, 480, 250);
        add(sp);

        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());
        showBtn.addActionListener(e -> showAll());

        setVisible(true);
    }

    private void styleButton(JButton b, Color bg, Color fg) {
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                b.setBackground(bg.darker());
            }

            public void mouseExited(MouseEvent evt) {
                b.setBackground(bg);
            }
        });
    }

    private void deposit() {
        String acc = accountField.getText().trim();
        if (acc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter account number");
            return;
        }
        try {
            double amt = Double.parseDouble(amountField.getText().trim());
            accounts.put(acc, accounts.getOrDefault(acc, 0.0) + amt);
            JOptionPane.showMessageDialog(this, "✅ Deposited " + amt);
            clearFields();
            showAll();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount");
        }
    }

    private void withdraw() {
        String acc = accountField.getText().trim();
        if (acc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter account number");
            return;
        }
        try {
            double amt = Double.parseDouble(amountField.getText().trim());
            if (accounts.getOrDefault(acc, 0.0) >= amt) {
                accounts.put(acc, accounts.get(acc) - amt);
                JOptionPane.showMessageDialog(this, "✅ Withdrawn " + amt);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Insufficient Balance");
            }
            clearFields();
            showAll();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount");
        }
    }

    private void clearFields() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clearFields'");
    }

    private void showAll() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s %-10s\n", "Account", "Balance"));
        sb.append("------------------------------\n");
        for (Map.Entry<String, Double> e : accounts.entrySet()) {
            sb.append(String.format("%-15s %-10.2f\n", e.getKey(), e.getValue()));
        }
        display.setText(sb.toString());
    }

    private void clea   rFields() {
        accountField.setText("");
        amountField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OnlineBankingEnhanced::new);
    }
}
