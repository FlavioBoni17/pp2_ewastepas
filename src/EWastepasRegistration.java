import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EWastepasRegistration extends JFrame {

    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public EWastepasRegistration() {
        setTitle("E-Wastepas Registration");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Logo and illustration
        JLabel logoLabel = new JLabel("E-Wastepas", JLabel.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(logoLabel, gbc);

        // Username Label and Input
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        panel.add(usernameField, gbc);

        // Email Label and Input
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel emailLabel = new JLabel("Email:");
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Password Label and Input
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        // Register Button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton registerButton = new JButton("Daftar");
        registerButton.setPreferredSize(new Dimension(70, 25)); 
        panel.add(registerButton, gbc);
        registerButton.addActionListener(new RegisterButtonListener());

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JLabel alreadyHaveAccountLabel = new JLabel("Sudah punya akun?", JLabel.CENTER);
        panel.add(alreadyHaveAccountLabel, gbc);

        // Login Button
        gbc.gridy = 6;
        JButton loginButton = new JButton("Masuk");
        panel.add(loginButton, gbc);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new EWastepasLogin().setVisible(true);
            }
        });

        add(panel);
        setVisible(true);
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Save to database
            saveToDatabase(username, email, password);
        }
    }

    private void saveToDatabase(String username, String email, String password) {
        String url = "jdbc:mysql://localhost:3306/ewastepas"; 
        String user = "root"; 
        String pass = ""; 

        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registrasi berhasil!");
            dispose(); // Menutup jendela registrasi
            new EWastepasLogin().setVisible(true); // Buka halaman login

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Registrasi gagal. Coba lagi.");
        }
    }

    public static void main(String[] args) {
        new EWastepasRegistration();
    }
}
