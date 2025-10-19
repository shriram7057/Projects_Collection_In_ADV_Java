import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class ECommerceCartEnhanced extends JFrame {
    private JTextField productField, qtyField;
    private JTextArea display;
    private Map<String, Integer> cart = new HashMap<>();

    public ECommerceCartEnhanced() {
        setTitle("🛒 E-commerce Shopping Cart");
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 255, 240));

        JLabel title = new JLabel("Shopping Cart", JLabel.CENTER);
        title.setBounds(50, 10, 400, 40);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(new Color(0, 120, 70));
        add(title);

        add(new JLabel("Product:")).setBounds(30, 70, 80, 25);
        add(new JLabel("Quantity:")).setBounds(30, 110, 80, 25);

        productField = new JTextField();
        qtyField = new JTextField();
        productField.setBounds(120, 70, 350, 25);
        qtyField.setBounds(120, 110, 350, 25);
        productField.setBackground(new Color(255, 255, 200));
        qtyField.setBackground(new Color(255, 255, 200));
        productField.setToolTipText("Enter product name");
        qtyField.setToolTipText("Enter quantity");

        add(productField);
        add(qtyField);

        JButton addBtn = new JButton("Add/Update");
        addBtn.setBounds(30, 150, 150, 35);
        JButton removeBtn = new JButton("Remove");
        removeBtn.setBounds(200, 150, 120, 35);
        JButton showBtn = new JButton("Show Cart");
        showBtn.setBounds(340, 150, 130, 35);

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
        sp.setBounds(30, 200, 420, 250);
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
        String product = productField.getText().trim();
        try {
            int qty = Integer.parseInt(qtyField.getText().trim());
            if (product.isEmpty() || qty <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid input");
                return;
            }
            cart.put(product, cart.getOrDefault(product, 0) + qty);
            JOptionPane.showMessageDialog(this, "✅ Added/Updated");
            clearFields();
            showAll();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid quantity");
        }
    }

    private void removeItem() {
        String product = productField.getText().trim();
        if (cart.remove(product) != null) {
            JOptionPane.showMessageDialog(this, "Removed ❌");
            showAll();
        } else
            JOptionPane.showMessageDialog(this, "Product not found ❌");
        clearFields();
    }

    private void showAll() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-10s\n", "Product", "Qty"));
        sb.append("---------------------------\n");
        for (Map.Entry<String, Integer> e : cart.entrySet()) {
            sb.append(String.format("%-20s %-10d\n", e.getKey(), e.getValue()));
        }
        display.setText(sb.toString());
    }

    private void clearFields() {
        productField.setText("");
        qtyField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ECommerceCartEnhanced::new);
    }
}
