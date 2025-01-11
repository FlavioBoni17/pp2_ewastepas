import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserEditProfile extends JFrame {
    private JTextField namaField;
    private JTextField noHandphoneField;
    private JTextField alamatField;
    private JTextField tanggalLahirField;
    private JButton saveButton;

    private String username;
    private String email;

    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ewastepas";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public UserEditProfile(String username, String email) {
        this.username = username;
        this.email = email;

        // Konfigurasi JFrame
        setTitle("Edit Profil Pengguna");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label dan field "Nama"
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Nama:"), gbc);

        gbc.gridx = 1;
        namaField = new JTextField(20);
        mainPanel.add(namaField, gbc);

        // Label dan field "No Handphone"
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("No Handphone:"), gbc);

        gbc.gridx = 1;
        noHandphoneField = new JTextField(20);
        mainPanel.add(noHandphoneField, gbc);

        // Label dan field "Alamat"
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Alamat:"), gbc);

        gbc.gridx = 1;
        alamatField = new JTextField(20);
        mainPanel.add(alamatField, gbc);

        // Label dan field "Tanggal Lahir"
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Tanggal Lahir:"), gbc);

        gbc.gridx = 1;
        tanggalLahirField = new JTextField(20);
        mainPanel.add(tanggalLahirField, gbc);

        // Tombol "Simpan"
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        saveButton = new JButton("Simpan");
        mainPanel.add(saveButton, gbc);

        add(mainPanel);

        // Action Listener untuk tombol "Simpan"
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProfile();
            }
        });

        // Retrieve user data
        retrieveUserData();
    }

    private void retrieveUserData() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM users WHERE username = ? AND email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, email);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        namaField.setText(resultSet.getString("nama"));
                        noHandphoneField.setText(resultSet.getString("no_handphone"));
                        alamatField.setText(resultSet.getString("alamat"));
                        tanggalLahirField.setText(resultSet.getString("tanggal_lahir"));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void saveProfile() {
        String nama = namaField.getText();
        String noHandphone = noHandphoneField.getText();
        String alamat = alamatField.getText();
        String tanggalLahir = tanggalLahirField.getText();

        if (nama.isEmpty() || noHandphone.isEmpty() || alamat.isEmpty() || tanggalLahir.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE users SET nama = ?, no_handphone = ?, alamat = ?, tanggal_lahir = ? WHERE username = ? AND email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nama);
                statement.setString(2, noHandphone);
                statement.setString(3, alamat);
                statement.setString(4, tanggalLahir);
                statement.setString(5, username);
                statement.setString(6, email);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Profil berhasil diperbarui!");

                    // Beralih ke halaman UserAccountPage
                    this.dispose(); // Menutup halaman Edit Profil
                    UserAccountPage userAccountPage = new UserAccountPage(username, email);
                    userAccountPage.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal memperbarui profil.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserEditProfile editProfile = new UserEditProfile("testUser", "testUser@example.com");
            editProfile.setVisible(true);
        });
    }
}