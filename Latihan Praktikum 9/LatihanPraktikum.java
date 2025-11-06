import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class LatihanPraktikum extends JFrame {
    private List<Mahasiswa> mahasiswaList = new ArrayList<>();

    class Mahasiswa {
        private String nim, nama, prodi, kelamin;
        private boolean isActive;
        public Mahasiswa(String nim, String nama, String prodi, String kelamin, boolean isActive) {
            this.nim = nim; this.nama = nama; this.prodi = prodi; this.kelamin = kelamin; this.isActive = isActive;
        }
        public String getNim(){ return nim; }   
        public String getNama(){ return nama; }
        public String getProdi(){ return prodi; }
        public String getKelamin(){ return kelamin; }
        public boolean isActive(){ return isActive; }
        public void setNim(String nim){ this.nim = nim; } 
        public void setNama(String nama){ this.nama = nama; }
        public void setProdi(String prodi){ this.prodi = prodi; }
        public void setKelamin(String kelamin){ this.kelamin = kelamin; }
        public void setActive(boolean active){ this.isActive = active; }
    }
    private JPanel sidebar, contentPanel;
    private JButton btnTambah, btnLihat;
    private JTextField tfNIM, tfNama;
    private JComboBox<String> cbProdi;
    private JRadioButton rbLaki, rbPerempuan;
    private ButtonGroup bgKelamin;
    private JCheckBox chkAktif;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnKirim, btnReset, btnUpdate, btnHapus;
    private int selectedRow = -1;

    public LatihanPraktikum() {
        setTitle("Aplikasi Data Mahasiswa");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        sidebar = new JPanel(new GridLayout(0, 1, 10, 10));
        sidebar.setPreferredSize(new Dimension(160, 0));
        sidebar.setBackground(new Color(240, 240, 240));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        btnTambah = new JButton("Tambah Data");
        btnLihat = new JButton("Lihat Data");

        sidebar.add(btnTambah);
        sidebar.add(btnLihat);
        add(sidebar, BorderLayout.WEST);

        contentPanel = new JPanel(new CardLayout());
        contentPanel.add(panelTambah(), "TAMBAH");
        contentPanel.add(panelLihat(), "LIHAT");
        add(contentPanel, BorderLayout.CENTER);

        btnTambah.addActionListener(e -> showCard("TAMBAH"));
        btnLihat.addActionListener(e -> {
            refreshTable();
            showCard("LIHAT");
        });
        showCard("TAMBAH");
    }
    private JPanel panelTambah() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Data Mahasiswa", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.DARK_GRAY);
        panel.add(lblTitle, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblNama = new JLabel("Nama:");
        JLabel lblNIM = new JLabel("NIM:");
        JLabel lblProdi = new JLabel("Prodi:");
        JLabel lblKelamin = new JLabel("Jenis Kelamin:");
        JLabel lblStatus = new JLabel("Status:");

        tfNIM = new JTextField(20);
        tfNama = new JTextField(20);
        cbProdi = new JComboBox<>(new String[]{
            "-- Pilih Prodi --",
            "S1 Teknik Informatika",
            "S1 Teknik Elektro",
            "D3 Teknik Elektro"
        });
        tfNIM.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                    JOptionPane.showMessageDialog(null, "NIM hanya boleh angka!", "Input Tidak Valid", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        rbLaki = new JRadioButton("Laki-Laki");
        rbPerempuan = new JRadioButton("Perempuan");
        bgKelamin = new ButtonGroup();
        bgKelamin.add(rbLaki);
        bgKelamin.add(rbPerempuan);
        JPanel kelaminPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        kelaminPanel.setBackground(Color.WHITE);
        kelaminPanel.add(rbLaki);
        kelaminPanel.add(rbPerempuan);

        chkAktif = new JCheckBox("Aktif");
        chkAktif.setBackground(Color.WHITE);

        btnKirim = new JButton("Simpan");
        btnUpdate = new JButton("Update");
        btnReset = new JButton("Reset");

        btnKirim.setBackground(new Color(60, 179, 113));
        btnKirim.setForeground(Color.WHITE);
        btnUpdate.setBackground(new Color(70, 130, 180));
        btnUpdate.setForeground(Color.WHITE);
        btnReset.setBackground(new Color(220, 20, 60));
        btnReset.setForeground(Color.WHITE);

        btnUpdate.setEnabled(false);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(btnKirim);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnReset);

        gbc.gridx = 0; gbc.gridy = 0; form.add(lblNama, gbc);
        gbc.gridx = 1; form.add(tfNama, gbc);
        gbc.gridx = 0; gbc.gridy = 1; form.add(lblNIM, gbc);
        gbc.gridx = 1; form.add(tfNIM, gbc);
        gbc.gridx = 0; gbc.gridy = 2; form.add(lblProdi, gbc);
        gbc.gridx = 1; form.add(cbProdi, gbc);
        gbc.gridx = 0; gbc.gridy = 3; form.add(lblKelamin, gbc);
        gbc.gridx = 1; form.add(kelaminPanel, gbc);
        gbc.gridx = 0; gbc.gridy = 4; form.add(lblStatus, gbc);
        gbc.gridx = 1; form.add(chkAktif, gbc);
        gbc.gridx = 1; gbc.gridy = 5; form.add(btnPanel, gbc);

        panel.add(form, BorderLayout.CENTER);

        btnKirim.addActionListener(e -> simpanData());
        btnReset.addActionListener(e -> clearForm());
        btnUpdate.addActionListener(e -> updateData());

        return panel;
    }
    private JPanel panelLihat() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        tableModel = new DefaultTableModel(new Object[]{"Nama", "NIM", "Prodi", "Jenis Kelamin", "Status"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottom.setBackground(Color.WHITE);

        btnHapus = new JButton("Hapus Data");
        JButton btnEdit = new JButton("Edit Data");
        bottom.add(btnEdit);
        bottom.add(btnHapus);
        panel.add(bottom, BorderLayout.SOUTH);

        btnHapus.addActionListener(e -> hapusBaris());
        btnEdit.addActionListener(e -> editData());

        return panel;
    }
    private void showCard(String name) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, name);
    }
    private void simpanData() {
        String nim = tfNIM.getText().trim();
        String nama = tfNama.getText().trim();
        String prodi = (String) cbProdi.getSelectedItem();
        String kelamin = rbLaki.isSelected() ? "Laki-laki" : rbPerempuan.isSelected() ? "Perempuan" : "";
        boolean status = chkAktif.isSelected();

        if (nama.isEmpty() || nim.isEmpty() || prodi.equals("-- Pilih Prodi --") || kelamin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!nim.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "NIM harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Mahasiswa m : mahasiswaList) {
            if (m.getNim().equals(nim)) {
                JOptionPane.showMessageDialog(this, "Mahasiswa dengan NIM ini sudah ada!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        mahasiswaList.add(new Mahasiswa(nim, nama, prodi, kelamin, status));
        JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
        clearForm();
    }
    private void clearForm() {
        tfNIM.setText("");
        tfNama.setText("");
        cbProdi.setSelectedIndex(0);
        bgKelamin.clearSelection();
        chkAktif.setSelected(false);
        btnUpdate.setEnabled(false);
        btnKirim.setEnabled(true);
        selectedRow = -1;
        tfNIM.setEditable(true);
    }
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Mahasiswa m : mahasiswaList) {
            tableModel.addRow(new Object[]{
                m.getNama(),
                m.getNim(),
                m.getProdi(),
                m.getKelamin(),
                m.isActive() ? "Aktif" : "Tidak Aktif"
            });
        }
    }
    private void hapusBaris() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang akan dihapus!");
            return;
        }
        int opt = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (opt != JOptionPane.YES_OPTION) return;

        String nim = (String) tableModel.getValueAt(row, 1);
        mahasiswaList.removeIf(m -> m.getNim().equals(nim));
        refreshTable();
        JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
    }
    private void editData() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit!");
            return;
        }
        selectedRow = row;
        String nim = (String) tableModel.getValueAt(row, 1);
        for (Mahasiswa m : mahasiswaList) {
            if (m.getNim().equals(nim)) {
                tfNama.setText(m.getNama());
                tfNIM.setText(m.getNim());
                cbProdi.setSelectedItem(m.getProdi());
                if (m.getKelamin().equals("Laki-laki")) rbLaki.setSelected(true);
                else rbPerempuan.setSelected(true);
                chkAktif.setSelected(m.isActive());
                break;
            }
        }
        btnKirim.setEnabled(false);
        btnUpdate.setEnabled(true);
        tfNIM.setEditable(true); 
        showCard("TAMBAH");
    }
    private void updateData() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Tidak ada data yang dipilih untuk diperbarui!");
            return;
        }
        String nimBaru = tfNIM.getText().trim();
        String nama = tfNama.getText().trim();
        String prodi = (String) cbProdi.getSelectedItem();
        String kelamin = rbLaki.isSelected() ? "Laki-laki" : rbPerempuan.isSelected() ? "Perempuan" : "";
        boolean status = chkAktif.isSelected();

        if (nama.isEmpty() || nimBaru.isEmpty() || prodi.equals("-- Pilih Prodi --") || kelamin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
            return;
        }
        if (!nimBaru.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "NIM harus berupa angka!");
            return;
        }
        for (int i = 0; i < mahasiswaList.size(); i++) {
            if (i != selectedRow && mahasiswaList.get(i).getNim().equals(nimBaru)) {
                JOptionPane.showMessageDialog(this, "NIM baru sudah digunakan oleh mahasiswa lain!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        Mahasiswa m = mahasiswaList.get(selectedRow);
        m.setNim(nimBaru);
        m.setNama(nama);
        m.setProdi(prodi);
        m.setKelamin(kelamin);
        m.setActive(status);

        JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
        clearForm();
        refreshTable();
        showCard("LIHAT");
    }
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        SwingUtilities.invokeLater(() -> new LatihanPraktikum().setVisible(true));
    }   
}
