import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class LihatDataPanel extends JPanel implements ActionListener {
    private JTable table;
    private DefaultTableModel model;
    private MainFrame parent;
    private JButton btnHapus, btnEdit;

    public LihatDataPanel(MainFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel lbl = new JLabel("Lihat Data Mahasiswa", JLabel.LEFT);
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        add(lbl, BorderLayout.NORTH);

        String[] columns = {"Nama", "NIM", "Prodi", "Jenis Kelamin", "Status"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        loadData();

        add(new JScrollPane(table), BorderLayout.CENTER);


        JPanel panelButton = new JPanel();
        btnEdit = new JButton("Update");
        btnHapus = new JButton("Delete");
        btnEdit.addActionListener(this);
        btnHapus.addActionListener(this);
        panelButton.add(btnEdit);
        panelButton.add(btnHapus);
        add(panelButton, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0);
        for (Mahasiswa mhs : MainFrame.mahasiswaList) {
            model.addRow(new Object[]{
                mhs.getNama(),
                mhs.getNim(),
                mhs.getProdi(),
                mhs.getJenisKelamin(),
                mhs.isActive() ? "Aktif" : "Tidak Aktif"
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (e.getSource() == btnHapus) {
            MainFrame.mahasiswaList.remove(selectedRow);
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            loadData();
        }

        if (e.getSource() == btnEdit) {
            Mahasiswa m = MainFrame.mahasiswaList.get(selectedRow);
            String namaBaru = JOptionPane.showInputDialog(this, "Edit Nama:", m.getNama());
            String prodiBaru = JOptionPane.showInputDialog(this, "Edit Prodi:", m.getProdi());

            if (namaBaru != null && !namaBaru.trim().isEmpty()) {
                m = new Mahasiswa(namaBaru, m.getNim(), prodiBaru, m.getJenisKelamin(), m.isActive());
                MainFrame.mahasiswaList.set(selectedRow, m);
                JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
                loadData();
            }
        }
    }
}
