import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ChatAppEnhanced extends JFrame {
    private JTextArea chatArea;
    private JTextField msgField;
    private JButton sendBtn;
    private ArrayList<String> messages = new ArrayList<>();

    public ChatAppEnhanced() {
        setTitle("💬 Chat Application (Local)");
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 245));

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        chatArea.setBackground(new Color(255, 250, 240));
        JScrollPane sp = new JScrollPane(chatArea);
        sp.setBounds(30, 30, 420, 330);
        add(sp);

        msgField = new JTextField();
        msgField.setBounds(30, 380, 320, 30);
        msgField.setBackground(new Color(255, 255, 200));
        msgField.setToolTipText("Type message");
        add(msgField);

        sendBtn = new JButton("Send");
        sendBtn.setBounds(360, 380, 90, 30);
        styleButton(sendBtn, new Color(0, 120, 215), Color.WHITE);
        add(sendBtn);

        sendBtn.addActionListener(e -> sendMessage());
        msgField.addActionListener(e -> sendMessage());

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

    private void sendMessage() {
        String msg = msgField.getText().trim();
        if (!msg.isEmpty()) {
            messages.add("You: " + msg);
            updateChat();
            msgField.setText("");
        }
    }

    private void updateChat() {
        StringBuilder sb = new StringBuilder();
        for (String m : messages)
            sb.append(m).append("\n");
        chatArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatAppEnhanced::new);
    }
}
