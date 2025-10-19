import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CourseRegistrationEnhanced extends JFrame {
    private JTextField nameField;
    private JCheckBox javaCB, pythonCB, webCB;
    private JTextArea display;
    private ArrayList<String> registrations = new ArrayList<>();

    public CourseRegistrationEnhanced() {
        setTitle("📘 Course Registration System");
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 250, 255));

        JLabel title = new JLabel("Course Registration", JLabel.CENTER);
        title.setBounds(50, 10, 400, 40);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(new Color(0, 60, 120));
        add(title);

        add(new JLabel("Student Name:")).setBounds(30, 70, 120, 25);
        nameField = new JTextField();
        nameField.setBounds(150, 70, 300, 25);
        nameField.setBackground(new Color(255, 255, 200));
        add(nameField);

        javaCB = new JCheckBox("Java");
        pythonCB = new JCheckBox("Python");
        webCB = new JCheckBox("Web Dev");
        javaCB.setBounds(50, 110, 100, 25);
        pythonCB.setBounds(160, 110, 100, 25);
        webCB.setBounds(270, 110, 120, 25);
        javaCB.setBackground(new Color(245, 250, 255));
        pythonCB.setBackground(new Color(245, 250, 255));
        webCB.setBackground(new Color(245, 250, 255));
        add(javaCB);
        add(pythonCB);
        add(webCB);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(180, 150, 120, 35);
        styleButton(registerBtn, new Color(0, 120, 215), Color.WHITE);
        add(registerBtn);

        display = new JTextArea();
        display.setEditable(false);
        display.setBounds(30, 200, 420, 250);
        display.setFont(new Font("Monospaced", Font.PLAIN, 14));
        display.setBackground(new Color(250, 250, 240));
        add(display);

        registerBtn.addActionListener(e -> registerCourses());

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

    private void registerCourses() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter student name");
            return;
        }
        StringBuilder courses = new StringBuilder();
        if (javaCB.isSelected())
            courses.append("Java ");
        if (pythonCB.isSelected())
            courses.append("Python ");
        if (webCB.isSelected())
            courses.append("Web Dev ");
        if (courses.length() == 0) {
            JOptionPane.showMessageDialog(this, "Select at least one course");
            return;
        }
        registrations.add(name + " -> " + courses.toString());
        JOptionPane.showMessageDialog(this, "✅ Registered");
        clearFields();
        showAll();
    }

    private void showAll() {
        StringBuilder sb = new StringBuilder();
        for (String r : registrations)
            sb.append(r).append("\n");
        display.setText(sb.toString());
    }

    private void clearFields() {
        nameField.setText("");
        javaCB.setSelected(false);
        pythonCB.setSelected(false);
        webCB.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CourseRegistrationEnhanced::new);
    }
}
