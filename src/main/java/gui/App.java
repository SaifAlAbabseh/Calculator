package gui;

import logic.Calculator;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class App extends JFrame {

    private JButton[] numbers;
    private Listener listener;
    private JButton clear, leftPara, rightPara, mod, division, multi, minus, plus, equal, dot, backspace;
    private JTextField inputField, outputField;
    private String expr = "";

    public App() {
        initButtons();
        initButtonsPanel();
        initFieldsPanel();
        initFrame();
    }

    private void initFieldsPanel() {
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 1));
        JPanel inputPanel = new JPanel();

        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(400, 100));
        outputField = new JTextField();

        styleField(inputField);
        styleField(outputField);

        inputPanel.add(inputField);
        inputPanel.add(backspace);

        fieldsPanel.add(inputPanel);
        fieldsPanel.add(outputField);

        add(fieldsPanel, BorderLayout.NORTH);
    }

    private void styleField(JTextField field) {
        field.setEditable(false);
        field.setBackground(Color.WHITE);
        field.setForeground(Color.RED);
        field.setFont(new Font("Arial", Font.BOLD, 25));
        field.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
        field.setFocusable(false);
    }

    private void initButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(5, 4));

        buttonsPanel.add(clear);
        buttonsPanel.add(leftPara);
        buttonsPanel.add(rightPara);
        buttonsPanel.add(mod);
        buttonsPanel.add(division);

        initNumbers(buttonsPanel);

        add(buttonsPanel);
    }

    private void initButtons() {
        clear = new JButton("C");
        numbers = new JButton[10];
        listener = new Listener();
        leftPara = new JButton("(");
        rightPara = new JButton(")");
        mod = new JButton("%");
        division = new JButton("/");
        multi = new JButton("*");
        minus = new JButton("-");
        plus = new JButton("+");
        equal = new JButton("=");
        dot = new JButton(".");
        backspace = new JButton("<<");

        styleOperationButton(leftPara);
        styleOperationButton(rightPara);
        styleOperationButton(mod);
        styleOperationButton(division);
        styleOperationButton(multi);
        styleOperationButton(minus);
        styleOperationButton(plus);
        styleNumber(dot);
        styleClearButton();
        styleEqualButton();
        styleBackspaceButton();
    }

    private void initFrame() {
        setTitle("Calculator");
        setSize(500, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initNumbers(JPanel buttonsPanel) {
        for(int i = 0; i < numbers.length; i++) {
            numbers[i] = new JButton("" + i);
            styleNumber(numbers[i]);
            buttonsPanel.add(numbers[i]);
            if(i == 2) buttonsPanel.add(multi);
            if(i == 5) buttonsPanel.add(minus);
            if(i == 8) buttonsPanel.add(plus);
            if(i == 9) {
                buttonsPanel.add(dot);
                buttonsPanel.add(equal);
            }
        }
    }

    private void buttonCommonStyles(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 35));
        button.setFocusable(false);
        button.setUI(new BasicButtonUI());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(listener);
        button.addMouseListener(listener);
    }

    private void styleNumber(JButton number) {
        number.setForeground(Color.GREEN);
        number.setBackground(Color.BLACK);
        number.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
        buttonCommonStyles(number);
    }

    private void styleOperationButton(JButton operationButton) {
        operationButton.setForeground(Color.BLACK);
        operationButton.setBackground(Color.GREEN);
        operationButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        buttonCommonStyles(operationButton);
    }

    private void styleClearButton() {
        clear.setForeground(Color.RED);
        clear.setBackground(Color.BLACK);
        clear.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
        buttonCommonStyles(clear);
    }

    private void styleEqualButton() {
        equal.setForeground(Color.WHITE);
        equal.setBackground(Color.BLACK);
        equal.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        buttonCommonStyles(equal);
    }

    private void styleBackspaceButton() {
        backspace.setPreferredSize(new Dimension(60, 30));
        backspace.setForeground(Color.RED);
        backspace.setBackground(Color.BLACK);
        backspace.setFont(new Font("Arial", Font.BOLD, 20));
        backspace.setFocusable(false);
        backspace.setUI(new BasicButtonUI());
        backspace.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backspace.addActionListener(listener);
        backspace.addMouseListener(listener);
    }

    private class Listener implements ActionListener, MouseListener {

        private Color prevBackColor = null;
        private Color prevForeColor = null;

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String text = button.getActionCommand();
            if(text.equals("C")) {
                expr = "";
                inputField.setText("");
                outputField.setText("");
            }
            else {
                if(text.equals("<<")) {
                    if(!expr.isEmpty()) {
                        expr = expr.substring(0, expr.length() - 1);
                        inputField.setText(expr);
                    }
                }
                else if(text.equals("=")) {
                    StringBuilder temp = new StringBuilder();
                    for (int i = 0; i < expr.length(); i++) {
                        if (expr.charAt(i) == '(' || expr.charAt(i) == ')')
                            temp.append(expr.charAt(i));
                    }
                    Calculator calculator = new Calculator(expr);
                    String answer = calculator.evaluate(temp.toString());
                    outputField.setText(Objects.requireNonNullElse(answer, "Error"));
                }
                else {
                    expr += text;
                    inputField.setText(expr);
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton number = (JButton) e.getSource();
            prevBackColor = number.getBackground();
            prevForeColor = number.getForeground();
            number.setBackground(prevForeColor);
            number.setForeground(prevBackColor);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton number = (JButton) e.getSource();
            number.setBackground(prevBackColor);
            number.setForeground(prevForeColor);
        }
    }
}
