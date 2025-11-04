import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TambahDataPanel extends JPanel implements ActionListener {
    private JTextField Nama, NIM;
    private JComboBox<String> Prodi;
    private JRadioButton Laki, Perempuan;
    private JCheckBox Aktif;
    private JButton btnKirim;
    private ButtonGroup Kelamin;
    private MainFrame parent;

    public TambahDataPanel(MainFrame parent) {
        this.parent = parent;
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Tambah Data Mahasiswa");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBounds(50, 20, 400, 30);
        add(lblTitle);

        JLabel lblNama = new JLabel("Nama");
        lblNama.setBounds(50, 70, 100, 25);
        add(lblNama);

        Nama = new JTextField();
        Nama.setBounds(180, 70, 350, 25);
        add(Nama);

        JLabel lblNIM = new JLabel("NIM ");
        lblNIM.setBounds(50, 110, 100, 25);
        add(lblNIM);

        NIM = new JTextField();
        NIM.setBounds(180, 110, 350, 25);
        add(NIM);

        JLabel lblProdi = new JLabel("Prodi");
        lblProdi.setBounds(50, 150, 100, 25);
        add(lblProdi);

        String[] prodiList = {"S1 Teknik Informatika", "S1 Sistem Informasi", "D3 Teknik Elektro"};
        Prodi = new JComboBox<>(prodiList);
        Prodi.setBounds(180, 150, 350, 25);
        add(Prodi);

        JLabel lblJK = new JLabel("Jenis Kelamin");
        lblJK.setBounds(50, 190, 100, 25);
        add(lblJK);

        Laki = new JRadioButton("Laki-laki");
        Laki.setBounds(180, 190, 100, 25);
        Laki.setBackground(Color.WHITE);

        Perempuan = new JRadioButton("Perempuan");
        Perempuan.setBounds(280, 190, 120, 25);
        Perempuan.setBackground(Color.WHITE);

        Kelamin = new ButtonGroup();
        Kelamin.add(Laki);
        Kelamin.add(Perempuan);

        add(Laki);
        add(Perempuan);

        JLabel lblStatus = new JLabel("Status");
        lblStatus.setBounds(50, 230, 100, 25);
        add(lblStatus);

        Aktif = new JCheckBox("Aktif");
        Aktif.setBackground(Color.WHITE);
        Aktif.setBounds(180, 230, 100, 25);
        add(Aktif);

        btnKirim = new JButton("Kirim");
        btnKirim.setBackground(new Color(102, 255, 255));
        btnKirim.setBounds(180, 280, 100, 30);
        btnKirim.addActionListener(this);
        add(btnKirim);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnKirim) {
            String nama = Nama.getText().trim();
            String nim = NIM.getText().trim();
            String prodi = (String) Prodi.getSelectedItem();
            boolean aktif = Aktif.isSelected();

            
            if (nama.isEmpty() || nim.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama dan NIM tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

           
            if (!nim.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "NIM harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            if (!Laki.isSelected() && !Perempuan.isSelected()) {
                JOptionPane.showMessageDialog(this, "Pilih jenis kelamin terlebih dahulu!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String jk = Laki.isSelected() ? "Laki-laki" : "Perempuan";

            
            for (Mahasiswa m : MainFrame.mahasiswaList) {
                if (m.getNim().equals(nim)) {
                    JOptionPane.showMessageDialog(this, "NIM sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

        
            MainFrame.mahasiswaList.add(new Mahasiswa(nama, nim, prodi, jk, aktif));
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");

            parent.changeMainPanel(new LihatDataPanel(parent));
        }
    }
}
