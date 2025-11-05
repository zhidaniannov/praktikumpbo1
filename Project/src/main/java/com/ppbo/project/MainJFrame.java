package com.ppbo.project;

public class MainJFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(MainJFrame.class.getName());

    public void changeMainPanel(javax.swing.JPanel panel){
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(panel);
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    public MainJFrame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        sidebarPanel = new javax.swing.JPanel();
        tambahDataBtn = new javax.swing.JButton();
        lihatDataBtn = new javax.swing.JButton();
        basePanel = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sidebarPanel.setBackground(new java.awt.Color(0, 128, 255));

        tambahDataBtn.setText("Tambah Data");
        tambahDataBtn.addActionListener(evt -> tambahDataBtnActionPerformed(evt));

        lihatDataBtn.setText("Lihat Data");
        lihatDataBtn.addActionListener(evt -> lihatDataBtnActionPerformed(evt));

        javax.swing.GroupLayout sidebarPanelLayout = new javax.swing.GroupLayout(sidebarPanel);
        sidebarPanel.setLayout(sidebarPanelLayout);
        sidebarPanelLayout.setHorizontalGroup(
            sidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tambahDataBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lihatDataBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sidebarPanelLayout.setVerticalGroup(
            sidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(tambahDataBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lihatDataBtn)
                .addContainerGap(212, Short.MAX_VALUE))
        );

        getContentPane().add(sidebarPanel, java.awt.BorderLayout.LINE_START);

        basePanel.setForeground(new java.awt.Color(255, 255, 255));
        mainPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout basePanelLayout = new javax.swing.GroupLayout(basePanel);
        basePanel.setLayout(basePanelLayout);
        basePanelLayout.setHorizontalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basePanelLayout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 293, Short.MAX_VALUE))
        );
        basePanelLayout.setVerticalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basePanelLayout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 300, Short.MAX_VALUE))
        );

        getContentPane().add(basePanel, java.awt.BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void tambahDataBtnActionPerformed(java.awt.event.ActionEvent evt) {
        changeMainPanel(new TambahDataPanel());
    }

    private void lihatDataBtnActionPerformed(java.awt.event.ActionEvent evt) {
        changeMainPanel(new LihatDataPanel());
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new MainJFrame().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JPanel basePanel;
    private javax.swing.JButton lihatDataBtn;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel sidebarPanel;
    private javax.swing.JButton tambahDataBtn;
    // End of variables declaration
}
