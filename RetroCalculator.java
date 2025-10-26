import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.text.NumberFormat;
import java.util.Locale;
import java.math.BigDecimal;

public class RetroCalculator extends JFrame implements ActionListener {
    // --- Variabel Logika ---
    private double num1 = 0;
    private char operator = '\0';
    private boolean isTypingNumber = false;
    // --- Komponen GUI ---
    private JTextField display;
    private JLabel historyDisplay;
    private JPanel buttonPanel;
    private JButton acButton; 
    // --- Warna Kustom (Tema Pink/Retro) ---
    private final Color FRAME_BG = new Color(245, 239, 230);
    private final Color DISPLAY_BG = new Color(220, 200, 200);
    private final Color DISPLAY_TEXT_COLOR = new Color(100, 80, 80);
    private final Color HISTORY_TEXT_COLOR = new Color(150, 130, 130);
    private final Color BTN_SPECIAL_COLOR = new Color(220, 180, 180); 
    private final Color BTN_NUM_COLOR = new Color(240, 200, 200);     
    private final Color BTN_OP_COLOR = new Color(200, 120, 120);      
    private NumberFormat numberParser;

    public RetroCalculator() {
        // --- Setup Frame Utama ---
        setTitle("Kalkulator Retro");
        setSize(320, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(FRAME_BG);
        // --- Inisialisasi Parser Angka ---
        numberParser = NumberFormat.getInstance(Locale.US);
        // --- Panel Layar ---
        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.setBackground(DISPLAY_BG);
        displayPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 15, 5, 15),
                new LineBorder(new Color(180, 160, 160), 2)
        ));
        historyDisplay = new JLabel(" ");
        historyDisplay.setFont(new Font("Monospaced", Font.PLAIN, 16));
        historyDisplay.setHorizontalAlignment(JLabel.RIGHT);
        historyDisplay.setForeground(HISTORY_TEXT_COLOR);
        historyDisplay.setBorder(new EmptyBorder(5, 5, 0, 5));
        displayPanel.add(historyDisplay, BorderLayout.NORTH);

        display = new JTextField("0");
        display.setEditable(false);
        display.setFont(new Font("Monospaced", Font.BOLD, 36));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(DISPLAY_BG);
        display.setForeground(DISPLAY_TEXT_COLOR);
        display.setBorder(new EmptyBorder(0, 5, 5, 5));
        displayPanel.add(display, BorderLayout.CENTER);

        add(displayPanel, BorderLayout.NORTH);
        // --- Setup Panel Tombol (GridBagLayout) ---
        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(FRAME_BG);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        // Tata letak 4 kolom baru (tanpa %)
        String[][] buttonLabels = {
                {"AC", "+/-", "÷"}, // Baris atas 3 tombol
                {"7", "8", "9", "×"},
                {"4", "5", "6", "−"},
                {"1", "2", "3", "+"},
                {"0", ",", "="} 
        };
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        for (int y = 0; y < buttonLabels.length; y++) {
            for (int x = 0; x < buttonLabels[y].length; x++) {
                String text = buttonLabels[y][x];
                gbc.gridx = x;
                gbc.gridy = y;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;

                // --- Penyesuaian Layout ---
                if (y == 0) { // Baris atas
                    if (text.equals("AC")) {
                        gbc.gridx = 0;
                        gbc.gridwidth = 2; // Tombol AC mencakup 2 kolom
                    } else if (text.equals("+/-")) {
                        gbc.gridx = 2;
                    } else if (text.equals("÷")) {
                        gbc.gridx = 3;
                    }
                }
                else if (y == 4) { // Baris bawah
                    if (text.equals("0")) {
                        gbc.gridx = 0;
                        gbc.gridwidth = 2; // Tombol 0 mencakup 2 kolom
                    } else if (text.equals(",")) {
                        gbc.gridx = 2;
                    } else if (text.equals("=")) {
                        gbc.gridx = 3;
                    }
                }
                // --- Akhir Penyesuaian Layout ---
                
                JButton button = createButton(text);
                
                if (text.equals("AC")) {
                    acButton = button; 
                }
                
                buttonPanel.add(button, gbc);
            }
        }
        add(buttonPanel, BorderLayout.CENTER);
    }
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        
        Color bgColor;
        if ("AC".contains(text)) { 
            bgColor = BTN_SPECIAL_COLOR; // Warna 
        } else if ("+/-÷×−+=".contains(text)) { // Tombol +, −, ÷, ×, = ada di sini
            bgColor = BTN_OP_COLOR; // Diatur ke pink tua
        } else {
            bgColor = BTN_NUM_COLOR; 
        }
        button.setFont(new Font("Monospaced", Font.BOLD, 20));
        button.setForeground(DISPLAY_TEXT_COLOR);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker().darker(), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        button.putClientProperty("JButton.buttonType", "roundRect");
        button.addActionListener(this);
        return button;
    }
    private double getDisplayValue() {
        try {
            return numberParser.parse(display.getText()).doubleValue();
        } catch (java.text.ParseException e) {
            return 0;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        // Menggunakan switch-case agar lebih ringkas
        switch (command) {
            case "0": case "1": case "2": case "3": case "4":
            case "5": case "6": case "7": case "8": case "9":
                handleNumberInput(command);
                break;
            case ",":
                handleDotInput();
                break;
            case "+/-":
                handleNegate();
                break;
            case "AC":
                resetCalculator();
                break;
            case "C":
                handleClearEntry();
                break;
            case "÷":
                handleOperator('/');
                break;
            case "×":
                handleOperator('*');
                break;
            case "−":
                handleOperator('-');
                break;
            case "+":
                handleOperator('+');
                break;
            case "=":
                handleEquals();
                break;
        }
    }
    private void handleNumberInput(String number) {
        if (!isTypingNumber && (operator == '\0' || display.getText().equals("Error"))) {
            historyDisplay.setText(" ");
        }
        
        if (!isTypingNumber || display.getText().equals("0") || display.getText().equals("Error")) {
            display.setText(number);
        } else {
            display.setText(display.getText() + number);
        }
        isTypingNumber = true;
        acButton.setText("C"); 
    }
    private void handleDotInput() {
        if (!isTypingNumber && (operator == '\0' || display.getText().equals("Error"))) {
            historyDisplay.setText(" ");
        }
        
        if (!isTypingNumber || display.getText().equals("Error")) {
            display.setText("0.");
        } 
        else if (!display.getText().contains(".")) {
            display.setText(display.getText() + ".");
        }
        isTypingNumber = true;
        acButton.setText("C"); 
    }
    private void handleClearEntry() {
        display.setText("0");
        isTypingNumber = true; 
        acButton.setText("AC");
    }
    private void resetCalculator() { 
        display.setText("0");
        historyDisplay.setText(" ");
        num1 = 0;
        operator = '\0';
        isTypingNumber = false;
        acButton.setText("AC");
    }
    private void handleNegate() {
        if (display.getText().equals("0") || display.getText().equals("Error")) return;
        double val = getDisplayValue() * -1;
        display.setText(formatResult(val));
        if (!isTypingNumber) {
            num1 = val;
        }
    }
    private double calculate(double n1, double n2, char op) throws ArithmeticException {
        switch (op) {
            case '+': return n1 + n2;
            case '-': return n1 - n2;
            case '*': return n1 * n2;
            case '/':
                if (n2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return n1 / n2;
            default:
                return n2; // Seharusnya tidak terjadi
        }
    }
    private void handleOperator(char op) {
        // Jika pengguna menekan 5 + 5 + (perhitungan berantai),
        // selesaikan dulu 5 + 5 sebelum memproses + berikutnya.
        if (isTypingNumber && operator != '\0') {
            handleEquals();
        }
        
        // Simpan angka di layar (baik itu num1 asli atau hasil perhitungan)
        if (!display.getText().equals("Error")) {
            num1 = getDisplayValue();
        }
        
        operator = op;
        isTypingNumber = false;
        
        if (!display.getText().equals("Error")) {
            char displayOp = op;
            if (op == '/') displayOp = '÷';
            if (op == '*') displayOp = '×';
            if (op == '-') displayOp = '−';
            historyDisplay.setText(formatResult(num1) + " " + displayOp + " ");
        }
        acButton.setText("AC"); 
    }

    private void handleEquals() {
        // Hanya hitung jika ada operator DAN angka kedua sudah diketik
        if (operator != '\0' && isTypingNumber) {
            double num2 = getDisplayValue();
            
            char displayOp = operator;
            if (operator == '/') displayOp = '÷';
            if (operator == '*') displayOp = '×';
            if (operator == '-') displayOp = '−';
            historyDisplay.setText(formatResult(num1) + " " + displayOp + " " + formatResult(num2) + " =");

            try {
                double result = calculate(num1, num2, operator);
                display.setText(formatResult(result));
                num1 = result; // Simpan hasil untuk perhitungan selanjutnya
            } catch (ArithmeticException ex) {
                display.setText("Error");
                num1 = 0; // Reset num1 saat error
            }
            
            operator = '\0';
            isTypingNumber = false;
            acButton.setText("AC"); 
        }
    }
    
    private String formatResult(double result) {
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return new BigDecimal(result).stripTrailingZeros().toPlainString();
        }
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
            System.err.println("Nimbus L&F not found, using default.");
        }

        SwingUtilities.invokeLater(() -> {
            new RetroCalculator().setVisible(true);
        });
    }
}