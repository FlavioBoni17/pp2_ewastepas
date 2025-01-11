import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EWastepasLogin extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public EWastepasLogin() {
        setTitle("E-Wastepas Login");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel logoLabel = new JLabel("E-Wastepas", JLabel.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(logoLabel, gbc);

        // Email Label and Input
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email:");
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Password Label and Input
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Masuk");
        loginButton.setPreferredSize(new Dimension(70, 25));
        panel.add(loginButton, gbc);

        // Forgot Password Button
        gbc.gridy = 4;
        JButton forgotPasswordButton = new JButton("Lupa Password?");
        panel.add(forgotPasswordButton, gbc);
        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ForgotPasswordScreen();
            }
        });

        // Text "Belum punya akun?"
        gbc.gridy = 5;
        JLabel noAccountLabel = new JLabel("Belum punya akun?", JLabel.CENTER);
        panel.add(noAccountLabel, gbc);

        // Register Button
        gbc.gridy = 6;
        JButton registerButton = new JButton("Daftar");
        panel.add(registerButton, gbc);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new EWastepasRegistration().setVisible(true);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String email = emailField.getText();
                    String password = new String(passwordField.getPassword());

                    System.out.println("Login attempt with Email: " + email + ", Password: " + password);

                    String[] userData = authenticateUser(email, password);
                    if (userData != null) {
                        System.out.println("Authentication successful: Username: " + userData[0] + ", Email: " + userData[1]);
                        JOptionPane.showMessageDialog(EWastepasLogin.this, "Login berhasil!");
                        dispose();
                        new ChangePasswordPage(userData[0]).setVisible(true);
                    } else {
                        System.out.println("Authentication failed: Email or Password is incorrect.");
                        JOptionPane.showMessageDialog(EWastepasLogin.this, "Login gagal! Email atau password salah.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(panel);
        setVisible(true);
    }

    private String[] authenticateUser(String email, String password) throws Exception {
        String dbUrl = "jdbc:mysql://localhost:3306/ewastepas";
        String dbUser = "root";
        String dbPassword = "";

        System.out.println("Connecting to database...");

        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        String sql = "SELECT username, email FROM users WHERE email = ? AND password = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, email);
        statement.setString(2, password);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String[] userData = {resultSet.getString("username"), resultSet.getString("email")};
            System.out.println("Database query successful: Username: " + userData[0] + ", Email: " + userData[1]);
            resultSet.close();
            statement.close();
            conn.close();
            return userData;
        } else {
            System.out.println("Database query failed: No user found with given email and password.");
            resultSet.close();
            statement.close();
            conn.close();
            return null;
        }
    }

    public static void main(String[] args) {
        new EWastepasLogin();
    }
}
