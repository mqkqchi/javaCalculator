
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

    private JTextField display;

    private double firstNumber = 0;
    private String operator = "";
    private boolean newNumber = true;

    public Calculator() {

        setTitle("Java Calculator");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Display
        display = new JTextField("0");
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setHorizontalAlignment(JTextField.RIGHT);

        add(display, BorderLayout.NORTH);

        // Buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttons = {
            "C", "DEL", "/", "*",
            "7", "8", "9", "-",
            "4", "5", "6", "+",
            "1", "2", "3", "=",
            "0", ".", "", ""
        };

        for (String text : buttons) {

            if (text.isEmpty()) {
                panel.add(new JLabel());
                continue;
            }

            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);

            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if (command.matches("[0-9]")) {

            if (newNumber || display.getText().equals("0")) {
                display.setText(command);
                newNumber = false;
            } else {
                display.setText(display.getText() + command);
            }

        } else if (command.equals(".")) {

            if (newNumber) {
                display.setText("0.");
                newNumber = false;
            } else if (!display.getText().contains(".")) {
                display.setText(display.getText() + ".");
            }

        } else if (command.equals("C")) {

            display.setText("0");
            firstNumber = 0;
            operator = "";
            newNumber = true;

        } else if (command.equals("DEL")) {

            if (!newNumber) {
                String current = display.getText();

                if (current.length() > 1) {
                    display.setText(
                        current.substring(0, current.length() - 1)
                    );
                } else {
                    display.setText("0");
                    newNumber = true;
                }
            }

        } else if (command.equals("=")) {

            if (!operator.isEmpty() && !newNumber) {

                double secondNumber = Double.parseDouble(display.getText());
                double result = 0;

                switch (operator) {
                    case "+":
                        result = firstNumber + secondNumber;
                        break;

                    case "-":
                        result = firstNumber - secondNumber;
                        break;

                    case "*":
                        result = firstNumber * secondNumber;
                        break;

                    case "/":
                        if (secondNumber == 0) {
                            display.setText("Cannot divide by zero");
                            operator = "";
                            newNumber = true;
                            return;
                        }

                        result = firstNumber / secondNumber;
                        break;
                }

                display.setText(formatResult(result));
                operator = "";
                newNumber = true;
            }

        } else {

            if (!operator.isEmpty() && !newNumber) {
                return;
            }

            firstNumber = Double.parseDouble(display.getText());
            operator = command;
            newNumber = true;
        }
    }

    private String formatResult(double result) {

        if (result == (long) result) {
            return String.valueOf((long) result);
        }

        return String.valueOf(result);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }
}
