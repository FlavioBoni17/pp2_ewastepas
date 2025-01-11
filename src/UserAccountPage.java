import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserAccountPage extends JFrame {

    private String nama = "------";
    private String username;
    private String email;
    private String noHandphone = "------";
    private String alamat = "------";
    private String tanggalLahir = "------";

    public UserAccountPage(String username, String email) {
        this.username = username;
        this.email = email;
        retrieveUserData();

        setTitle("Profil Pengguna");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Label dan field untuk nama
        addField(mainPanel, gbc, "Nama:", nama, 0);
        addField(mainPanel, gbc, "Username:", username, 1);
        addField(mainPanel, gbc, "Email:", email, 2);
        addField(mainPanel, gbc, "No Handphone:", noHandphone, 3);
        addField(mainPanel, gbc, "Alamat:", alamat, 4);
        addField(mainPanel, gbc, "Tanggal Lahir:", tanggalLahir, 5);

        // Button Ubah Profil
        JButton editProfileButton = addButton(mainPanel, gbc, "Ubah Profil", 6);
        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserEditProfile(username, email).setVisible(true);
                dispose();
            }
        });

        // Button Kembali
        JButton backButton = addButton(mainPanel, gbc, "Kembali", 7);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainPage(username, email).setVisible(true);
                dispose();
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, String value, int y) {
        JLabel fieldLabel = new JLabel(label);
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(fieldLabel, gbc);

        JLabel fieldValue = new JLabel(value);
        gbc.gridx = 1;
        panel.add(fieldValue, gbc);
    }

    private JButton addButton(JPanel panel, GridBagConstraints gbc, String label, int y) {
        JButton button = new JButton(label);
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(button, gbc);
        return button;
    }

    private void retrieveUserData() {
        try {
            // Koneksi ke database
            String url = "jdbc:mysql://localhost:3306/ewastepas";
            String user = "root";
            String password = "";
            Connection conn = DriverManager.getConnection(url, user, password);

            // Query untuk mengambil data pengguna
            String sql = "SELECT * FROM users WHERE username = ? AND email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nama = rs.getString("nama");
                noHandphone = rs.getString("no_handphone");
                alamat = rs.getString("alamat");
                tanggalLahir = rs.getString("tanggal_lahir");
            }

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UserAccountPage("username_contoh", "email@contoh.com").setVisible(true);
        });
    }
}