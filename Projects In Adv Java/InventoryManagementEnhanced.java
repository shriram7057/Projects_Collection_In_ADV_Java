import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class InventoryManagementEnhanced extends JFrame {
    private JTextField itemField, qtyField;
    private JTextArea display;
    private Map<String, Integer> inventory = new LinkedHashMap<>();

    public InventoryManagementEnhanced() {
        setTitle("📦 Inventory Management");
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 255, 245));

        JLabel title = new JLabel("Inventory Management", JLabel.CENTER);
        title.setBounds(50, 10, 400, 40);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(new Color(0, 100, 50));
        add(title);

        add(new JLabel("Item:")).setBounds(30, 70, 80, 25);
        add(new JLabel("Quantity:")).setBounds(30, 110, 80, 25);

        itemField = new JTextField();
        qtyField = new JTextField();
        itemField.setBounds(120, 70, 350, 25);
        qtyField.setBounds(120, 110, 350, 25);
        itemField.setBackground(new Color(255, 255, 200));
        qtyField.setBackground(new Color(255, 255, 200));
        itemField.setToolTipText("Enter item name");
        qtyField.setToolTipText("Enter quantity");

        add(itemField);
        add(qtyField);

        JButton addBtn = new JButton("Add/Update");
        addBtn.setBounds(30, 150, 150, 35);
        JButton removeBtn = new JButton("Remove");
        removeBtn.setBounds(200, 150, 120, 35);
        JButton showBtn = new JButton("Show Inventory");
        showBtn.setBounds(340, 150, 150, 35);

        styleButton(addBtn, new Color(0, 120, 215), Color.WHITE);
        styleButton(removeBtn, new Color(220, 50, 50), Color.WHITE);
        styleButton(showBtn, new Color(0, 150, 0), Color.WHITE);

        add(addBtn);
        add(removeBtn);
        add(showBtn);

        display = new JTextArea();
        display.setEditable(false);
        display.setBackground(new Color(250, 250, 240));
        display.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane sp = new JScrollPane(display);
        sp.setBounds(30, 200, 460, 250);
        add(sp);

        addBtn.addActionListener(e -> addOrUpdate());
        removeBtn.addActionListener(e -> removeItem());
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

    private void addOrUpdate() {
        String item = itemField.getText().trim();
        try {
            int qty = Integer.parseInt(qtyField.getText().trim());
            if (item.isEmpty() || qty <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid input");
                return;
            }
            inventory.put(item, inventory.getOrDefault(item, 0) + qty);
            JOptionPane.showMessageDialog(this, "✅ Added/Updated");
            clearFields();
            showAll();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid quantity");
        }
    }

    private void removeItem() {
        String item = itemField.getText().trim();
        if (inventory.remove(item) != null) {
            JOptionPane.showMessageDialog(this, "Removed ❌");
            showAll();
        } else
            JOptionPane.showMessageDialog(this, "Item not found ❌");
        clearFields();
    }

    private void showAll() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-10s\n", "Item", "Qty"));
        sb.append("---------------------------\n");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            sb.append(String.format("%-20s %-10d\n", e.getKey(), e.getValue()));
        }
        display.setText(sb.toString());
    }

    private void clearFields() {
        itemField.setText("");
        qtyField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryManagementEnhanced::new);
    }
}
