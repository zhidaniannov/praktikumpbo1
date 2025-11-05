package com.ppbo.project;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class EditDataPanel extends javax.swing.JPanel {

        private ButtonGroup genderGroup;
        private final String oldNim; // NIM lama yang sedang diedit

        public EditDataPanel(Mahasiswa data) {
                // jaga-jaga
                if (data == null)
                        throw new IllegalArgumentException("Data mahasiswa tidak boleh null");
                this.oldNim = data.getNim();

                initComponents();

                // group gender
                genderGroup = new ButtonGroup();
                genderGroup.add(lakiLakiRadBtn);
                genderGroup.add(perempuanRadBtn);

                // pilihan prodi
                prodiComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                                "— Pilih Prodi —", "Teknik Informatika", "Sistem Informasi", "Teknik Elektro"
                }));

                // PREFILL dari data lama
                namaTextField.setText(data.getNama());
                nimTextField.setText(data.getNim());
                prodiComboBox.setSelectedItem(data.getProdi() != null ? data.getProdi() : "— Pilih Prodi —");
                if ("Laki-laki".equalsIgnoreCase(data.getJenisKelamin())) {
                        lakiLakiRadBtn.setSelected(true);
                } else if ("Perempuan".equalsIgnoreCase(data.getJenisKelamin())) {
                        perempuanRadBtn.setSelected(true);
                }
                statusCheckBox.setSelected(data.isActive());
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

        @SuppressWarnings("unchecked")
        private void initComponents() {

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
                updateBtn = new javax.swing.JButton();
                cancelBtn = new javax.swing.JButton();

                setLayout(new java.awt.CardLayout());

                titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                titleLabel.setText("Update Data Mahasiswa");

                namaLabel.setText("Nama");
                nimLabel.setText("NIM");
                prodiLabel.setText("Prodi");
                jenisKelaminLabel.setText("Jenis Kelamin");
                statusLabel.setText("Status");

                prodiComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "— Pilih Prodi —" }));

                lakiLakiRadBtn.setText("Laki-laki");
                perempuanRadBtn.setText("Perempuan");
                statusCheckBox.setText("Aktif");

                updateBtn.setText("Update");
                updateBtn.addActionListener(evt -> updateBtnActionPerformed(evt));

                cancelBtn.setText("Batal");
                cancelBtn.addActionListener(evt -> goBackToList());

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(titleLabel)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(namaLabel)
                                                                                                                .addComponent(nimLabel)
                                                                                                                .addComponent(prodiLabel)
                                                                                                                .addComponent(jenisKelaminLabel)
                                                                                                                .addComponent(statusLabel))
                                                                                                .addGap(37, 37, 37)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(namaTextField)
                                                                                                                .addComponent(nimTextField)
                                                                                                                .addComponent(prodiComboBox,
                                                                                                                                0,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addComponent(statusCheckBox)
                                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                .addComponent(lakiLakiRadBtn)
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                .addComponent(perempuanRadBtn))
                                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                .addComponent(updateBtn)
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                .addComponent(cancelBtn)))
                                                                                                                                .addGap(0, 100, Short.MAX_VALUE)))))
                                                                .addContainerGap()));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(titleLabel)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(namaLabel)
                                                                                .addComponent(namaTextField,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(nimLabel)
                                                                                .addComponent(nimTextField,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(prodiLabel)
                                                                                .addComponent(prodiComboBox,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jenisKelaminLabel)
                                                                                .addComponent(lakiLakiRadBtn)
                                                                                .addComponent(perempuanRadBtn))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(statusLabel)
                                                                                .addComponent(statusCheckBox))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(updateBtn)
                                                                                .addComponent(cancelBtn))
                                                                .addContainerGap(80, Short.MAX_VALUE)));

                add(jPanel1, "card2");
        }

        private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {
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

                // Kalau NIM berubah, pastikan tidak bentrok
                if (!oldNim.equals(nim) && Mahasiswa.existsByNim(nim)) {
                        JOptionPane.showMessageDialog(this, "NIM sudah terdaftar.");
                        return;
                }

                boolean ok = Mahasiswa.updateByNim(oldNim,
                                new Mahasiswa(nama, nim, prodi, jenisKelamin, isActive));
                if (ok) {
                        JOptionPane.showMessageDialog(this, "Data berhasil diupdate.");
                        goBackToList();
                } else {
                        JOptionPane.showMessageDialog(this, "Update gagal. Data tidak ditemukan.");
                }
        }

        private void goBackToList() {
                java.awt.Window win = SwingUtilities.getWindowAncestor(this);
                if (win instanceof MainJFrame) {
                        MainJFrame frame = (MainJFrame) win;
                        frame.changeMainPanel(new LihatDataPanel());
                }
        }

        // UI vars
        private javax.swing.JButton cancelBtn;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JLabel jenisKelaminLabel;
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
        private javax.swing.JButton updateBtn;
}
