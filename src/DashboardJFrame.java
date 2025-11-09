import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DashboardJFrame extends JFrame {

    public DashboardJFrame() {
        setTitle("Hospital Management Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Background panel
        JLabel background = new JLabel(new ImageIcon("src/images/BG.jpg"));
        background.setLayout(new GridBagLayout());
        add(background);

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(3, 2, 20, 20));
        statsPanel.setBackground(new Color(255, 255, 255, 200));
        statsPanel.setPreferredSize(new Dimension(800, 600));

        // Cards
        statsPanel.add(createCard("👨\u200d⚕️ Total Doctors", getCount("doctor")));
        statsPanel.add(createCard("🧑\u200d🤝\u200d🧑 Total Patients", getCount("patients")));
        statsPanel.add(createCard("📅 Appointments", getCount("appointment")));
        statsPanel.add(createCard("💊 Prescriptions", getCount("treatment")));
        statsPanel.add(createCard("🏥 Patients Treated", getCount("treatment"))); // Assuming treatment == treated
        statsPanel.add(createCard("👩\u200d⚕️ Nurses", getCount("nurse")));

        background.add(statsPanel);
    }

    private JPanel createCard(String title, int count) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(250, 150));
        card.setBackground(new Color(0, 123, 255));
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JLabel countLabel = new JLabel(String.valueOf(count), SwingConstants.CENTER);
        countLabel.setFont(new Font("Arial", Font.BOLD, 36));
        countLabel.setForeground(Color.YELLOW);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(countLabel, BorderLayout.CENTER);

        return card;
    }

    private int getCount(String tableName) {
        int count = 0;
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitaldb", "root", "");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardJFrame().setVisible(true));
    }
}
