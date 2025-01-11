import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ForgotPasswordScreen extends JFrame {
    private JTextField emailField;
    private JButton sendButton;

    public ForgotPasswordScreen() {
        setTitle("Lupa Password?");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Menutup jendela saat ini
                new EWastepasLogin(); // Membuka jendela Login
            }
        });
        add(backButton, BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel titleLabel = new JLabel("Lupa Password?");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Email label
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(emailLabel, gbc);

        // Email prompt
        JLabel emailPrompt = new JLabel("Masukkan email anda di bawah untuk reset password");
        emailPrompt.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(emailPrompt, gbc);

        // Email field
        emailField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(emailField, gbc);

        // Send button
        sendButton = new JButton("Kirim");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (checkEmailExists(emailField.getText())) {
                        dispose();
                        new ResetPasswordPage(emailField.getText()).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(ForgotPasswordScreen.this, "Email tidak ditemukan.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(sendButton, gbc);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private boolean checkEmailExists(String email) throws Exception {
        // Detail koneksi database
        String dbUrl = "jdbc:mysql://localhost:3306/ewastepas"; // Ganti dengan URL database kamu
        String dbUser = "root"; // Ganti dengan pengguna database kamu
        String dbPassword = ""; // Ganti dengan password database kamu

        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        String sql = "SELECT * FROM users WHERE email = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, email);

        ResultSet resultSet = statement.executeQuery();
        boolean found = resultSet.next();

        resultSet.close();
        statement.close();
        conn.close();

        return found;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ForgotPasswordScreen().setVisible(true);
            }
        });
    }
}