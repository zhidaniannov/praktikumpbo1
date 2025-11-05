package com.ppbo.project;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 * Panel tambah data mahasiswa (dengan validasi & cek duplikat NIM).
 */
public class TambahDataPanel extends javax.swing.JPanel {

    private ButtonGroup genderGroup;

    public TambahDataPanel() {
        initComponents();
        genderGroup = new ButtonGroup();
        genderGroup.add(lakiLakiRadBtn);
        genderGroup.add(perempuanRadBtn);

        prodiComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                "— Pilih Prodi —", "Teknik Informatika", "Sistem Informasi", "Teknik Elektro"
        }));
        prodiComboBox.setSelectedIndex(0);
    }

    private void clearForm() {
        namaTextField.setText("");
        nimTextField.setText("");
        genderGroup.clearSelection();
        statusCheckBox.setSelected(false);
        prodiComboBox.setSelectedIndex(0);
    }

    private String validateForm() {
        if (namaTextField.getText().trim().isEmpty())
            return "Nama tidak boleh kosong.";
        String nim = nimTextField.getText().trim();
        if (nim.isEmpty())
            return "NIM tidak boleh kosong.";
        if (!nim.matches("\\d+"))
            return "NIM harus berupa angka.";
        if (prodiComboBox.getSelectedIndex() == 0)
            return "Silakan pilih Prodi.";
        if (!lakiLakiRadBtn.isSelected() && !perempuanRadBtn.isSelected())
            return "Pilih jenis kelamin.";
        return null;
    }

    // --------- Generated UI ----------
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        namaLabel = new javax.swing.JLabel();
        nimLabel = new javax.swing.JLabel();
        prodiLabel = new javax.swing.JLabel();
        jenisKelaminLabel = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        namaTextField = new javax.swing.JTextField();
        nimTextField = new javax.swing.JTextField();
        prodiComboBox = new javax.swing.JComboBox<>();
        lakiLakiRadBtn = new javax.swing.JRadioButton();
        perempuanRadBtn = new javax.swing.JRadioButton();
        statusCheckBox = new javax.swing.JCheckBox();
        kirimBtn = new javax.swing.JButton();

        jTextField3.setText("jTextField3");

        setLayout(new java.awt.CardLayout());

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        titleLabel.setText("Tambah Data Mahasiswa");

        namaLabel.setText("Nama");
        nimLabel.setText("NIM");
        prodiLabel.setText("Prodi");
        jenisKelaminLabel.setText("Jenis Kelamin");
        statusLabel.setText("Status");

        prodiComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "— Pilih Prodi —" }));

        lakiLakiRadBtn.setText("Laki-laki");
        perempuanRadBtn.setText("Perempuan");
        statusCheckBox.setText("Aktif");

        kirimBtn.setText("Kirim");
        kirimBtn.addActionListener(evt -> kirimBtnActionPerformed(evt));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(titleLabel)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(namaLabel)
                                                        .addComponent(nimLabel)
                                                        .addComponent(prodiLabel)
                                                        .addComponent(jenisKelaminLabel)
                                                        .addComponent(statusLabel))
                                                .addGap(37, 37, 37)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(namaTextField)
                                                        .addComponent(nimTextField)
                                                        .addComponent(prodiComboBox, 0,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(statusCheckBox)
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(lakiLakiRadBtn)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(perempuanRadBtn))
                                                                        .addComponent(kirimBtn))
                                                                .addGap(0, 119, Short.MAX_VALUE)))))
                                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titleLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(namaLabel)
                                        .addComponent(namaTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(nimLabel)
                                        .addComponent(nimTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(prodiLabel)
                                        .addComponent(prodiComboBox, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jenisKelaminLabel)
                                        .addComponent(lakiLakiRadBtn)
                                        .addComponent(perempuanRadBtn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(statusLabel)
                                        .addComponent(statusCheckBox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kirimBtn)
                                .addContainerGap(80, Short.MAX_VALUE)));

        add(jPanel1, "card2");
    }

    // --------- Actions ----------
    private void kirimBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String err = validateForm();
        if (err != null) {
            JOptionPane.showMessageDialog(this, err);
            return;
        }

        String nama = namaTextField.getText().trim();
        String nim = nimTextField.getText().trim();
        String prodi = (String) prodiComboBox.getSelectedItem();
        String jenisKelamin = lakiLakiRadBtn.isSelected() ? "Laki-laki" : "Perempuan";
        boolean isActive = statusCheckBox.isSelected();

        if (Mahasiswa.existsByNim(nim)) {
            JOptionPane.showMessageDialog(this, "NIM sudah terdaftar.");
            return;
        }

        boolean ok = Mahasiswa.addIfNotExists(new Mahasiswa(nama, nim, prodi, jenisKelamin, isActive));
        if (ok) {
            JOptionPane.showMessageDialog(this, "Sukses menambahkan data mahasiswa: " + nama);
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data.");
        }
    }

    // ---------- Variables (auto) ----------
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel jenisKelaminLabel;
    private javax.swing.JButton kirimBtn;
    private javax.swing.JRadioButton lakiLakiRadBtn;
    private javax.swing.JLabel namaLabel;
    private javax.swing.JTextField namaTextField;
    private javax.swing.JLabel nimLabel;
    private javax.swing.JTextField nimTextField;
    private javax.swing.JRadioButton perempuanRadBtn;
    private javax.swing.JComboBox<String> prodiComboBox;
    private javax.swing.JLabel prodiLabel;
    private javax.swing.JCheckBox statusCheckBox;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JLabel titleLabel;
}
