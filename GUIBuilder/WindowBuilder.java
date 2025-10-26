import javax.swing.*;
import java.awt.Color;
import helper_classes.*;

public class WindowBuilder {
  public static void main(String[] args) {

    JFrame frame = new JFrame("My Awesome Window");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1475, 797);
    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setBackground(Color.decode("#1e1e1e"));

    JTextField element1 = new JTextField("");
    element1.setBounds(450, 150, 213, 21);
    element1.setFont(CustomFontLoader.loadFont("./resources/fonts/Lato.ttf", 14));
    element1.setBackground(Color.decode("#B2B2B2"));
    element1.setForeground(Color.decode("#656565"));
    element1.setBorder(new RoundedBorder(2, Color.decode("#979797"), 0));
    OnFocusEventHelper.setOnFocusText(element1, "Your Input!", Color.decode("#353535"), Color.decode("#656565"));
    panel.add(element1);

    JPasswordField element2 = new JPasswordField("");
    element2.setBounds(450, 180, 213, 21);
    element2.setFont(CustomFontLoader.loadFont("./resources/fonts/Lato.ttf", 14));
    element2.setBackground(Color.decode("#B2B2B2"));
    element2.setForeground(Color.decode("#656565"));
    element2.setBorder(new RoundedBorder(2, Color.decode("#979797"), 0));
    OnFocusEventHelper.setOnFocusText(element2, "Your Password!", Color.decode("#353535"), Color.decode("#656565"));
    panel.add(element2);

    JLabel element3 = new JLabel("Username");
    element3.setBounds(300, 150, 75, 21);
    element3.setFont(CustomFontLoader.loadFont("./resources/fonts/Lato.ttf", 14));
    element3.setForeground(Color.decode("#D9D9D9"));
    panel.add(element3);

    JLabel element4 = new JLabel("Password");
    element4.setBounds(300, 180, 75, 21);
    element4.setFont(CustomFontLoader.loadFont("./resources/fonts/Lato.ttf", 14));
    element4.setForeground(Color.decode("#D9D9D9"));
    panel.add(element4);

    JButton element5 = new JButton("Login");
    element5.setBounds(565, 220, 100, 30);
    element5.setBackground(Color.decode("#2e2e2e"));
    element5.setForeground(Color.decode("#D9D9D9"));
    element5.setFont(CustomFontLoader.loadFont("./resources/fonts/Lato.ttf", 14));
    element5.setBorder(new RoundedBorder(4, Color.decode("#979797"), 1));
    element5.setFocusPainted(false);
    OnClickEventHelper.setOnClickColor(element5, Color.decode("#232323"), Color.decode("#2e2e2e"));
    panel.add(element5);

    frame.add(panel);
    frame.setVisible(true);

  }
}