import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Kalkulator extends JFrame implements ActionListener {

    private JTextField display;
    private double num1 = 0, num2 = 0, result = 0;
    private String operator = "";

    public Kalkulator() {
        setTitle("Kalkulator");
        setSize(330, 440);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(15, 20, 25));

        display = new JTextField();
        display.setBounds(15, 15, 290, 50);
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setFont(new Font("Consolas", Font.BOLD, 24));
        display.setBackground(new Color(20, 25, 30));
        display.setForeground(new Color(180, 255, 255));
        display.setCaretColor(Color.CYAN);
        add(display);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));

        String[][] buttons = {
                { "AC", "del", "+/-", "÷" },
                { "7", "8", "9", "×" },
                { "4", "5", "6", "-" },
                { "1", "2", "3", "+" },
                { "%", "0", ",", "=" }
        };

        int startY = 80;
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                String text = buttons[row][col];
                JButton btn = new JButton(text);
                btn.setBounds(15 + col * 72, startY + row * 65, 65, 55);
                btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
                btn.setForeground(Color.WHITE);

                if (text.equals("AC") || text.equals("del") || text.equals("+/-")) {
                    btn.setBackground(new Color(0, 70, 80));
                } else if (text.equals("=")) {
                    btn.setBackground(new Color(0, 180, 190));
                } else if (text.equals("÷") || text.equals("x") || text.equals("-") || text.equals("+")) {
                    btn.setBackground(new Color(0, 100, 110));
                } else if (text.equals("%")) {
                    btn.setBackground(new Color(0, 110, 100));
                } else {
                    btn.setBackground(new Color(35, 40, 45));
                }

                btn.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        btn.setBackground(btn.getBackground().brighter());
                    }

                    public void mouseExited(MouseEvent e) {
                        if (text.equals("AC") || text.equals("del") || text.equals("+/-")) {
                            btn.setBackground(new Color(0, 70, 80));
                        } else if (text.equals("=")) {
                            btn.setBackground(new Color(0, 180, 190));
                        } else if (text.equals("÷") || text.equals("×") || text.equals("-") || text.equals("+")) {
                            btn.setBackground(new Color(0, 100, 110));
                        } else if (text.equals("%")) {
                            btn.setBackground(new Color(0, 110, 100));
                        } else {
                            btn.setBackground(new Color(35, 40, 45));
                        }
                    }
                });

                btn.setFocusPainted(false);
                btn.setBorderPainted(false);
                btn.addActionListener(this);
                add(btn);
            }
        }
        setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd) {
            case "AC":
                display.setText("");
                num1 = num2 = result = 0;
                operator = "";
                break;

            case "del":
                String text = display.getText();
                if (text.length() > 0) {
                    display.setText(text.substring(0, text.length() - 1));
                }
                break;

            case "+/-":
                if (!display.getText().isEmpty()) {
                    double val = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(val * -1));
                }
                break;

            case "+":
            case "-":
            case "x":
            case "÷":
                if (!display.getText().isEmpty()) {
                    num1 = Double.parseDouble(display.getText());
                    operator = cmd;
                    display.setText("");
                }
                break;

            case "=":
                if (!display.getText().isEmpty() && !operator.isEmpty()) {
                    num2 = Double.parseDouble(display.getText());
                    switch (operator) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "×":
                            result = num1 * num2;
                            break;
                        case "÷":
                            if (num2 == 0) {
                                display.setText("Error");
                                return;
                            }
                            result = num1 / num2;
                            break;
                    }
                    display.setText(String.valueOf(result));
                    operator = "";
                }
                break;

            case ",":
                if (!display.getText().contains(".")) {
                    display.setText(display.getText() + ".");
                }
                break;

            default:
                display.setText(display.getText() + cmd);
                break;
        }
    }

    public static void main(String[] args) {
        new Kalkulator();
    }

}
