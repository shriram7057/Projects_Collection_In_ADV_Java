import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class EmployeePayrollEnhanced extends JFrame {
    private JTextField nameField, salaryField;
    private JTextArea display;
    private Map<String, Double> employees = new LinkedHashMap<>();

    public EmployeePayrollEnhanced() {
        setTitle("💼 Employee Payroll System");
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 245, 255));

        JLabel title = new JLabel("Employee Payroll", JLabel.CENTER);
        title.setBounds(50, 10, 400, 40);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(new Color(0, 60, 120));
        add(title);

        add(new JLabel("Name:")).setBounds(30, 70, 80, 25);
        add(new JLabel("Salary:")).setBounds(30, 110, 80, 25);

        nameField = new JTextField();
        salaryField = new JTextField();
        nameField.setBounds(120, 70, 350, 25);
        salaryField.setBounds(120, 110, 350, 25);
        nameField.setBackground(new Color(255, 255, 200));
        salaryField.setBackground(new Color(255, 255, 200));
        nameField.setToolTipText("Employee name");
        salaryField.setToolTipText("Salary amount");

        add(nameField);
        add(salaryField);

        JButton addBtn = new JButton("Add/Update");
        addBtn.setBounds(30, 150, 150, 35);
        JButton removeBtn = new JButton("Remove");
        removeBtn.setBounds(200, 150, 120, 35);
        JButton showBtn = new JButton("Show All");
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
        removeBtn.addActionListener(e -> removeEmployee());
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
        String name = nameField.getText().trim();
        try {
            double salary = Double.parseDouble(salaryField.getText().trim());
            if (name.isEmpty() || salary < 0) {
                JOptionPane.showMessageDialog(this, "Invalid input");
                return;
            }
            employees.put(name, salary);
            JOptionPane.showMessageDialog(this, "✅ Added/Updated");
            clearFields();
            showAll();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid salary");
        }
    }

    private void removeEmployee() {
        String name = nameField.getText().trim();
        if (employees.remove(name) != null) {
            JOptionPane.showMessageDialog(this, "Removed ❌");
            showAll();
        } else
            JOptionPane.showMessageDialog(this, "Employee not found ❌");
        clearFields();
    }

    private void showAll() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-10s\n", "Name", "Salary"));
        sb.append("-----------------------------\n");
        for (Map.Entry<String, Double> e : employees.entrySet()) {
            sb.append(String.format("%-20s %-10.2f\n", e.getKey(), e.getValue()));
        }
        display.setText(sb.toString());
    }

    private void clearFields() {
        nameField.setText("");
        salaryField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeePayrollEnhanced::new);
    }
}
