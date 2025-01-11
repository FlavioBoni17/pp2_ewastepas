import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame {

    private String loggedInUsername;
    private String loggedInEmail;

    public MainPage(String username, String email) {
        this.loggedInUsername = username;
        this.loggedInEmail = email;

        setTitle("Halaman Utama");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Logo and description
        JLabel logoLabel = new JLabel("E-Wastepas", JLabel.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(logoLabel, gbc);

        // Profile Button
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        JButton profileButton = new JButton("Profile");
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserAccountPage(loggedInUsername, loggedInEmail).setVisible(true);
                dispose();
            }
        });
        mainPanel.add(profileButton, gbc);

        // Ubah Password Button
        gbc.gridy = 2;
        JButton ubahPasswordButton = new JButton("Ubah Password");
        ubahPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangePasswordPage(loggedInUsername).setVisible(true);
                dispose();
            }
        });
        mainPanel.add(ubahPasswordButton, gbc);

        // Jenis Sampah Button
        gbc.gridy = 3;
        JButton trashTypeButton = new JButton("Jenis Sampah");
        // Tambahkan aksi untuk trashTypeButton di sini
        mainPanel.add(trashTypeButton, gbc);

        // Keluar Button
        gbc.gridy = 4;
        JButton logoutButton = new JButton("Keluar");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mainPanel.add(logoutButton, gbc);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainPage("username_pengguna", "email@domain.com").setVisible(true);
        });
    }
}