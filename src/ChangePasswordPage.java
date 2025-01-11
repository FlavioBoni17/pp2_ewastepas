import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ChangePasswordPage extends JFrame {
    private String username;

    public ChangePasswordPage(String username) {
        this.username = username;
        setTitle("Ubah Password");
        // Increased height to make sure everything is visible
        setSize(400, 400); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label dan field untuk password lama, password baru, konfirmasi password baru
        JLabel oldPasswordLabel = new JLabel("Password Lama:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(oldPasswordLabel, gbc);

        JPasswordField oldPasswordField = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(oldPasswordField, gbc);

        JLabel newPasswordLabel = new JLabel("Password Baru:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(newPasswordLabel, gbc);

        JPasswordField newPasswordField = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(newPasswordField, gbc);

        JLabel confirmPasswordLabel = new JLabel("Konfirmasi Password Baru:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(confirmPasswordLabel, gbc);

        JPasswordField confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(confirmPasswordField, gbc);

        // Button Simpan
        JButton saveButton = new JButton("Simpan");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldPassword = new String(oldPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Password baru tidak cocok.");
                    return;
                }

                try {
                    // Koneksi ke database
                    String url = "jdbc:mysql://localhost:3306/ewastepas";
                    String user = "root";
                    String password = "";
                    Connection conn = DriverManager.getConnection(url, user, password);

                    // Memeriksa password lama
                    String queryCheckOldPassword = "SELECT password FROM users WHERE username = ?";
                    PreparedStatement psCheckOldPassword = conn.prepareStatement(queryCheckOldPassword);
                    psCheckOldPassword.setString(1, username); // Menggunakan username dari login page
                    ResultSet rs = psCheckOldPassword.executeQuery();

                    if (rs.next()) {
                        String currentPassword = rs.getString("password");
                        if (!currentPassword.equals(oldPassword)) {
                            JOptionPane.showMessageDialog(null, "Password lama salah. Silakan coba lagi.");
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Pengguna tidak ditemukan.");
                        return;
                    }

                    // Query untuk mengupdate password
                    String sql = "UPDATE users SET password = ? WHERE username = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, newPassword);
                    ps.setString(2, username);

                    int rowsUpdated = ps.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(null, "Password berhasil diubah.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Kesalahan saat mengubah password.");
                    }

                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        mainPanel.add(saveButton, gbc);

        // Button Kembali
        gbc.gridy = 7;
        JButton backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainPage(username, "user_email@example.com").setVisible(true); // Replace with actual user's email
                dispose();
            }
        });
        mainPanel.add(backButton, gbc);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChangePasswordPage("username").setVisible(true);
        });
    }
}