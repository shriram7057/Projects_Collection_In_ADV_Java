import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class HotelBookingEnhanced extends JFrame {
    private JTextField nameField, roomField;
    private JTextArea display;
    private Map<String, String> bookings = new LinkedHashMap<>();

    public HotelBookingEnhanced() {
        setTitle("🏨 Hotel Booking System");
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 245, 240));

        JLabel title = new JLabel("Hotel Booking", JLabel.CENTER);
        title.setBounds(50, 10, 400, 40);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(new Color(180, 50, 50));
        add(title);

        add(new JLabel("Customer Name:")).setBounds(30, 70, 120, 25);
        add(new JLabel("Room No:")).setBounds(30, 110, 120, 25);

        nameField = new JTextField();
        roomField = new JTextField();
        nameField.setBounds(160, 70, 300, 25);
        roomField.setBounds(160, 110, 300, 25);
        nameField.setBackground(new Color(255, 255, 200));
        roomField.setBackground(new Color(255, 255, 200));
        nameField.setToolTipText("Enter customer name");
        roomField.setToolTipText("Enter room number");

        add(nameField);
        add(roomField);

        JButton addBtn = new JButton("Book");
        addBtn.setBounds(30, 150, 120, 35);
        JButton deleteBtn = new JButton("Cancel");
        deleteBtn.setBounds(170, 150, 120, 35);
        JButton showBtn = new JButton("Show All");
        showBtn.setBounds(310, 150, 120, 35);

        styleButton(addBtn, new Color(0, 120, 215), Color.WHITE);
        styleButton(deleteBtn, new Color(220, 50, 50), Color.WHITE);
        styleButton(showBtn, new Color(0, 150, 0), Color.WHITE);

        add(addBtn);
        add(deleteBtn);
        add(showBtn);

        display = new JTextArea();
        display.setEditable(false);
        display.setBackground(new Color(245, 245, 250));
        display.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane sp = new JScrollPane(display);
        sp.setBounds(30, 200, 430, 250);
        add(sp);

        addBtn.addActionListener(e -> bookRoom());
        deleteBtn.addActionListener(e -> cancelBooking());
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

    private void bookRoom() {
        String name = nameField.getText().trim();
        String room = roomField.getText().trim();
        if (name.isEmpty() || room.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter all details");
            return;
        }
        bookings.put(room, name);
        JOptionPane.showMessageDialog(this, "✅ Room booked");
        clearFields();
        showAll();
    }

    private void cancelBooking() {
        String room = roomField.getText().trim();
        if (room.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter room number");
            return;
        }
        if (bookings.remove(room) != null) {
            JOptionPane.showMessageDialog(this, "Booking cancelled ❌");
            showAll();
        } else
            JOptionPane.showMessageDialog(this, "Room not found ❌");
        clearFields();
    }

    private void showAll() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-20s\n", "Room", "Customer"));
        sb.append("----------------------------\n");
        for (Map.Entry<String, String> e : bookings.entrySet()) {
            sb.append(String.format("%-10s %-20s\n", e.getKey(), e.getValue()));
        }
        display.setText(sb.toString());
    }

    private void clearFields() {
        nameField.setText("");
        roomField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelBookingEnhanced::new);
    }
}
