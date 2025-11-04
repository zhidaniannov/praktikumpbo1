import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    public static ArrayList<Mahasiswa> mahasiswaList = new ArrayList<>();
    public JPanel mainPanel;

    public MainFrame() { 
        setTitle("Tabel Mahasiswa");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new CardLayout());
        add(new DuaPanelFrame(this));
    }

    public void changeMainPanel(JPanel panel) {
        getContentPane().removeAll();
        add(new DuaPanelFrame(this, panel));
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
