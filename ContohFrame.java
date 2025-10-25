import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ContohFrame {
    //contoh sederhana JFrame
    public static void main(String[] args) {
        JFrame frame = new JFrame("Contoh Frame");
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout()); 


        //contoh sederhana JLabel
        JLabel Label1 = new JLabel("Nama: ");
        JLabel Label2 = new JLabel("Selamat Datang ",SwingConstants.CENTER);
        ImageIcon icon = new ImageIcon("src/Logo.png");
        JLabel Label3 = new JLabel(icon);

        JLabel Label4 = new JLabel("User", icon , SwingConstants.LEFT);

        Label1.setText("Username :");

        Label1.setFont(new Font("Arial", Font.BOLD,16));
        Label1.setForeground(Color.BLUE);

        //Contoh sederhanna JtextField
        JTextField textField1 = new JTextField(20);
        JTextField textField2 = new JTextField("Teks Default");
        JTextField textField3 = new JTextField("Hello", 15);
        String text = textField3.getText();
        textField1.setText("text baru");

        //Contoh sederhana Jbutton
        JButton button1 = new JButton("Tombol 1");
        ImageIcon icon2 = new ImageIcon("src/Logo.png");
        JButton button2 = new JButton("Tombol 2", icon2);
        JButton button3 = new JButton(icon2);

        button1.setBackground(Color.BLUE);
        button1.setForeground(Color.WHITE);

        // --- Tambahkan semua komponen ke frame ---
        frame.add(Label1);
        frame.add(textField1);
        frame.add(Label2);
        frame.add(Label3);
        frame.add(Label4);
        frame.add(textField2);
        frame.add(textField3);
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);

        // tampilkan frame setelah semua elemen ditambahkan
        frame.setVisible(true);
    }
}
