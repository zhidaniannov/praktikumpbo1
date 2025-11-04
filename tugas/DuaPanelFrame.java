import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DuaPanelFrame extends JPanel implements ActionListener {

    private JButton btnTambah, btnLihat;
    private JPanel kananPanel;
    private MainFrame parent;

    public DuaPanelFrame(MainFrame parent) {
        this(parent, new TambahDataPanel(parent)); 
    }

    public DuaPanelFrame(MainFrame parent, JPanel panelKanan) {
        this.parent = parent;
        setLayout(new BorderLayout());

        
        JPanel kiri = new JPanel();
        kiri.setBackground(Color.CYAN);
        kiri.setPreferredSize(new Dimension(200, 0));
        kiri.setLayout(null);

        btnTambah = new JButton("Tambah Data");
        btnTambah.setBounds(30, 100, 140, 30);
        btnTambah.addActionListener(this);
        kiri.add(btnTambah);

        btnLihat = new JButton("Lihat Data");
        btnLihat.setBounds(30, 150, 140, 30);
        btnLihat.addActionListener(this);
        kiri.add(btnLihat);

        add(kiri, BorderLayout.WEST);

        
        kananPanel = panelKanan;
        add(kananPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnTambah) {
            parent.changeMainPanel(new TambahDataPanel(parent));
        } else if (e.getSource() == btnLihat) {
            parent.changeMainPanel(new LihatDataPanel(parent));
        }
    }
}

