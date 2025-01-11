import javax.swing.*;
import java.awt.*;

public class JenisSampahElektronik {
    public static void main(String[] args) {
        // Membuat frame utama
        JFrame frame = new JFrame("Jenis-Jenis Sampah Elektronik Media Hiburan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(new BorderLayout());

        // Panel atas untuk judul
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Jenis-Jenis Sampah", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Jenis-Jenis Sampah Elektronik Media Hiburan", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(titleLabel);
        topPanel.add(subtitleLabel);

        // Panel tengah untuk ikon-ikon gambar
        JPanel gridPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Menambahkan gambar-gambar
        gridPanel.add(createImagePanel("Televisi", "img/tv.JPEG"));
        gridPanel.add(createImagePanel("Laptop", "img/laptop.JPEG"));
        gridPanel.add(createImagePanel("Smartphone", "img/smartphone.PNG"));
        gridPanel.add(createImagePanel("Kamera", "img/camera.JPG"));
        gridPanel.add(createImagePanel("Konsol", "img/konsol.JPEG"));
        gridPanel.add(createImagePanel("GamePortable", "img/portable.JPEG"));

        // Tombol Kembali di bawah
        JButton backButton = new JButton("Kembali");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setPreferredSize(new Dimension(100, 40));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        // Menambahkan panel ke frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Menampilkan frame
        frame.setVisible(true);
    }

    private static JPanel createImagePanel(String label, String imagePath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Menambahkan gambar dengan ukuran tetap
        JLabel imageLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon(imagePath); // Ganti dengan path gambar sebenarnya
        Image scaledImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        imageLabel.setIcon(scaledIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Menambahkan teks label
        JLabel textLabel = new JLabel(label, SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(imageLabel);
        panel.add(textLabel);

        return panel;
    }
}
