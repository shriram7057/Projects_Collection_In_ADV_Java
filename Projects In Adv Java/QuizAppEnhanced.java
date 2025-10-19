import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizAppEnhanced extends JFrame {
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup group;
    private JButton nextBtn;
    private JTextArea scoreArea;
    private int current = 0, score = 0;

    private String[][] quiz = {
            { "Java is a ___ language?", "Compiled", "Interpreted", "Both", "Both" },
            { "JVM stands for?", "Java Virtual Machine", "Java Very Much", "Just Virtual Machine",
                    "Java Virtual Machine" },
            { "Swing is used for?", "Web", "Desktop GUI", "Database", "Desktop GUI" }
    };

    public QuizAppEnhanced() {
        setTitle("❓ Quiz App");
        setSize(600, 400);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 255));

        questionLabel = new JLabel();
        questionLabel.setBounds(30, 20, 540, 40);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setForeground(new Color(0, 50, 150));
        add(questionLabel);

        options = new JRadioButton[3];
        group = new ButtonGroup();
        for (int i = 0; i < 3; i++) {
            options[i] = new JRadioButton();
            options[i].setBounds(50, 70 + i * 40, 500, 30);
            options[i].setBackground(new Color(245, 245, 255));
            options[i].setFont(new Font("Arial", Font.PLAIN, 16));
            group.add(options[i]);
            add(options[i]);
        }

        nextBtn = new JButton("Next");
        nextBtn.setBounds(230, 200, 100, 35);
        styleButton(nextBtn, new Color(0, 120, 215), Color.WHITE);
        add(nextBtn);

        scoreArea = new JTextArea();
        scoreArea.setEditable(false);
        scoreArea.setBounds(50, 250, 500, 100);
        scoreArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(scoreArea);

        nextBtn.addActionListener(e -> nextQuestion());

        loadQuestion();
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

    private void loadQuestion() {
        if (current < quiz.length) {
            questionLabel.setText((current + 1) + ". " + quiz[current][0]);
            for (int i = 0; i < 3; i++)
                options[i].setText(quiz[current][i + 1]);
            group.clearSelection();
        } else {
            questionLabel.setText("Quiz Completed!");
            nextBtn.setEnabled(false);
            scoreArea.setText("Your Score: " + score + "/" + quiz.length);
        }
    }

    private void nextQuestion() {
        for (JRadioButton rb : options) {
            if (rb.isSelected() && rb.getText().equals(quiz[current][4]))
                score++;
        }
        current++;
        loadQuestion();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizAppEnhanced::new);
    }
}
