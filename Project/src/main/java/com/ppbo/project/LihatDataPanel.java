package com.ppbo.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LihatDataPanel extends javax.swing.JPanel {

    public LihatDataPanel() {
        initComponents();
        loadData(); // tetap otomatis load saat dibuka
    }

    private void loadData() {
        String[] columns = {"Nama", "NIM", "Prodi", "Jenis Kelamin", "Status"};
        DefaultTableModel model = new DefaultTableModel(null, columns) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Mahasiswa m : Mahasiswa.mahasiswas) {
            model.addRow(new Object[]{
                m.getNama(),
                m.getNim(),
                m.getProdi(),
                m.getJenisKelamin(),
                m.isActive() ? "Aktif" : "Nonaktif"
            });
        }
        dataMahasiswaTable.setModel(model);
        dataMahasiswaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void openEditPanel() {
        int row = dataMahasiswaTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih baris yang akan diupdate."); return; }
        String nim = dataMahasiswaTable.getValueAt(row, 1).toString();
        Mahasiswa m = Mahasiswa.findByNim(nim);
        if (m == null) { JOptionPane.showMessageDialog(this, "Data tidak ditemukan."); return; }

        java.awt.Window win = SwingUtilities.getWindowAncestor(this);
        if (win instanceof MainJFrame frame) {
            frame.changeMainPanel(new EditDataPanel(m));
        }
    }

    private void deleteSelected() {
        int row = dataMahasiswaTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih baris yang akan dihapus."); return; }
        String nim = dataMahasiswaTable.getValueAt(row, 1).toString();

        int c = JOptionPane.showConfirmDialog(this, "Hapus data NIM " + nim + " ?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (c != JOptionPane.YES_OPTION) return;

        if (Mahasiswa.deleteByNim(nim)) {
            loadData(); // tetap refresh tabel setelah delete
            JOptionPane.showMessageDialog(this, "Data terhapus.");
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data.");
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataMahasiswaTable = new javax.swing.JTable();
        buttonPanel = new javax.swing.JPanel();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        titleLabel.setText("Lihat Data Mahasiswa");

        dataMahasiswaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {}, new String [] {}
        ));
        jScrollPane1.setViewportView(dataMahasiswaTable);

        updateBtn.setText("Update");
        updateBtn.addActionListener(e -> openEditPanel());

        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(e -> deleteSelected());

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(updateBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateBtn)
                    .addComponent(deleteBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel)
                    .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    // Variables (tanpa refreshBtn)
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JTable dataMahasiswaTable;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton updateBtn;
}
