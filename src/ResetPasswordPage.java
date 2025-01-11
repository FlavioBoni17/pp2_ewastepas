import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ResetPasswordPage extends JFrame {

    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private String userEmail;

    public ResetPasswordPage(String email) {
        this.userEmail = email;

        setTitle("Reset Password");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Reset Password", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Password Label
        gbc.gridy++;
        gbc.gridwidth = 2;
        JLabel newPasswordLabel = new JLabel("Password Baru:", JLabel.LEFT);
        mainPanel.add(newPasswordLabel, gbc);

        // Password Field
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        newPasswordField = new JPasswordField(20);
        mainPanel.add(newPasswordField, gbc);

        // Confirm Password Label
        gbc.gridy++;
        gbc.gridwidth = 2;
        JLabel confirmPasswordLabel = new JLabel("Konfirmasi Password:", JLabel.LEFT);
        mainPanel.add(confirmPasswordLabel, gbc);

        // Confirm Password Field
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        confirmPasswordField = new JPasswordField(20);
        mainPanel.add(confirmPasswordField, gbc);

        // Save Button
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        JButton saveButton = new JButton("Simpan");
        saveButton.setPreferredSize(new Dimension(160, 30)); // Consistent button width
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (newPassword.equals(confirmPassword)) {
                    try {
                        updatePasswordInDatabase(userEmail, newPassword);
                        JOptionPane.showMessageDialog(ResetPasswordPage.this, "Password berhasil diubah!");
                        new EWastepasLogin().setVisible(true); // Back to login page
                        dispose();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(ResetPasswordPage.this, "Terjadi kesalahan. Coba lagi.");
                    }
                } else {
                    JOptionPane.showMessageDialog(ResetPasswordPage.this, "Password baru tidak cocok. Coba lagi.");
                }
            }
        });
        mainPanel.add(saveButton, gbc);

        add(mainPanel);
        setVisible(true);
    }

    private void updatePasswordInDatabase(String email, String newPassword) throws Exception {
        String dbUrl = "jdbc:mysql://localhost:3306/ewastepas"; // Ganti dengan URL database kamu
        String dbUser = "root"; // Ganti dengan pengguna database kamu
        String dbPassword = ""; // Ganti dengan password database kamu

        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        String sql = "UPDATE users SET password = ? WHERE email = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, newPassword);
        statement.setString(2, email);
        statement.executeUpdate();

        statement.close();
        conn.close();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String userEmail = "gunakan email dari halaman lupa password"; // Dapatkan email dari halaman lupa password
            new ResetPasswordPage(userEmail).setVisible(true);
        });
    }
}