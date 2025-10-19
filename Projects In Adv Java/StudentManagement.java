// StudentManagement.java
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class StudentManagement extends JFrame implements ActionListener {
    // Database credentials - It's better to load these from a config file in a real app
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    JTextField t1, t2, t3;
    JButton add, view;
    JTextArea area;

    StudentManagement() {
        setTitle("Student Management System");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Added default close operation
        setLayout(null);

        // --- GUI Setup (No changes needed here) ---
        JLabel l1 = new JLabel("ID:");
        JLabel l2 = new JLabel("Name:");
        JLabel l3 = new JLabel("Course:");
        l1.setBounds(30, 30, 100, 30);
        l2.setBounds(30, 70, 100, 30);
        l3.setBounds(30, 110, 100, 30);
        add(l1);
        add(l2);
        add(l3);

        t1 = new JTextField();
        t2 = new JTextField();
        t3 = new JTextField();
        t1.setBounds(120, 30, 150, 25);
        t2.setBounds(120, 70, 150, 25);
        t3.setBounds(120, 110, 150, 25);
        add(t1);
        add(t2);
        add(t3);

        add = new JButton("Add");
        view = new JButton("View");
        add.setBounds(50, 160, 100, 30);
        view.setBounds(180, 160, 100, 30);
        add(add);
        add(view);
        
        // Use JScrollPane for the JTextArea
        area = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBounds(30, 210, 320, 120);
        add(scrollPane);

        add.addActionListener(this);
        view.addActionListener(this);

        // --- Database Initialization ---
        initializeDatabase();

        setVisible(true);
    }

    // Method to create the table if it doesn't exist
    private void initializeDatabase() {
        // We use try-with-resources here to ensure the connection and statement are closed
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement st = con.createStatement()) {
            
            // The table creation logic remains the same
            st.execute("CREATE TABLE IF NOT EXISTS student(id INT PRIMARY KEY, name VARCHAR(50), course VARCHAR(50))");
            System.out.println("Database table 'student' checked/created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection error! Check your MySQL server and credentials.", "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            addStudent();
        } else if (e.getSource() == view) {
            viewStudents();
        }
    }

    // --- Safe Add Method using PreparedStatement ---
    private void addStudent() {
        String idText = t1.getText();
        String name = t2.getText();
        String course = t3.getText();
        
        // Basic input validation
        if (idText.isEmpty() || name.isEmpty() || course.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             // 💡 IMPORTANT: Use PreparedStatement to prevent SQL injection
             PreparedStatement ps = con.prepareStatement("INSERT INTO student (id, name, course) VALUES (?, ?, ?)")) {

            int id = Integer.parseInt(idText);
            
            ps.setInt(1, id); // Set the first '?' to the ID
            ps.setString(2, name); // Set the second '?' to the Name
            ps.setString(3, course); // Set the third '?' to the Course
            
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "Student Added Successfully! ✅");
            
            // Clear fields after successful addition
            t1.setText("");
            t2.setText("");
            t3.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Safe View Method using try-with-resources ---
    private void viewStudents() {
        area.setText("ID\tName\tCourse\n");
        
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement st = con.createStatement();
             // ResultSet is included in try-with-resources
             ResultSet rs = st.executeQuery("SELECT * FROM student")) { 
            
            while (rs.next()) {
                area.append(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getString("course") + "\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            area.setText("Error viewing students: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error retrieving data from database.", "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Best practice to run Swing apps on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new StudentManagement());
    }
}