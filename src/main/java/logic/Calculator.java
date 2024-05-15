package logic;

import javax.swing.*;


public class Calculator {

    private final String expr;

    public Calculator(String expr) {
        this.expr = expr;
    }

    public String evaluate(String brackets) {
        if (areBracketsBalanced(brackets)) {
            try {
                Stack s = new Stack();
                StringBuilder postfix = new StringBuilder();
                for (int i = 0; i < expr.length(); i++) {
                    if (expr.charAt(i) == '(') s.push('(');
                    else if (expr.charAt(i) == ')') {
                        while (!s.isEmpty()) {
                            Object temp = s.pop();
                            if ((char) temp == '(') break;
                            else postfix.append(temp).append(" ");
                        }
                    } else if (expr.charAt(i) == '+' || ((expr.charAt(i) == '-' && !((expr.charAt(i) == '-' && (i - 1 < 0)) || (expr.charAt(i) == '-' && (expr.charAt(i - 1) == '-' || expr.charAt(i - 1) == '+' || expr.charAt(i - 1) == '*' || expr.charAt(i - 1) == '/' || expr.charAt(i - 1) == '%')))))) {
                        if (!s.isEmpty() && ((char) s.top() == '%' || (char) s.top() == '*' || (char) s.top() == '/' || (char) s.top() == '+' || (char) s.top() == '-')) {
                            postfix.append(s.pop()).append(" ");
                            s.push(expr.charAt(i));
                        } else s.push(expr.charAt(i));
                    } else if (expr.charAt(i) == '*' || expr.charAt(i) == '/' || expr.charAt(i) == '%') {
                        if (!s.isEmpty() && ((char) s.top() == '*' || (char) s.top() == '/' || (char) s.top() == '%')) {
                            postfix.append(s.pop()).append(" ");
                            s.push(expr.charAt(i));
                        } else s.push(expr.charAt(i));
                    } else {
                        StringBuilder num = new StringBuilder("" + expr.charAt(i));
                        for (int j = i + 1; j < expr.length(); j++) {
                            if (expr.charAt(i + 1) != '+' && expr.charAt(i + 1) != '-' && expr.charAt(i + 1) != '/' && expr.charAt(i + 1) != '*' && expr.charAt(i + 1) != '%' && expr.charAt(i + 1) != '(' && expr.charAt(i + 1) != ')') {
                                num.append(expr.charAt(j));
                                i++;
                            } else if ((expr.charAt(i) == '-' && (i - 1 < 0)) || (expr.charAt(i) == '-' && (expr.charAt(i - 1) == '-' || expr.charAt(i - 1) == '+' || expr.charAt(i - 1) == '*' || expr.charAt(i - 1) == '/' || expr.charAt(i - 1) == '%'))) {
                                num.append('-');
                                i++;
                            }
                        }
                        postfix.append(num).append(" ");
                    }
                }
                while (!s.isEmpty()) postfix.append(s.pop()).append(" ");
                Stack s2 = new Stack();
                String[] temp = postfix.toString().split(" ");
                for (String string : temp) {
                    if (string.equals("%") || string.equals("*") || string.equals("/") || string.equals("+") || string.equals("-")) {
                        double num1 = Double.parseDouble("" + s2.pop()), num2 = Double.parseDouble("" + s2.pop());
                        if (string.equals("%")) s2.push(num2 % num1);
                        if (string.equals("*")) s2.push(num2 * num1);
                        if (string.equals("/")) s2.push(num2 / num1);
                        if (string.equals("+")) s2.push(num2 + num1);
                        if (string.equals("-")) s2.push(num2 - num1);
                    } else s2.push(string);
                }
                return "" + s2.pop();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private boolean areBracketsBalanced(String expr) {
        Stack stack = new Stack();
        for (int i = 0; i < expr.length(); i++) {
            char x = expr.charAt(i);
            if (x == '(' || x == '[' || x == '{') {
                stack.push(x);
                continue;
            }
            if (stack.isEmpty()) {
                return false;
            }
            Object check = stack.pop();
            switch (x) {
                case ')':
                    if ((char) check == '{' || (char) check == '[') {
                        return false;
                    }
                    break;
                case '}':
                    if ((char) check == '(' || (char) check == '[') {
                        return false;
                    }
                    break;
                case ']':
                    if ((char) check == '(' || (char) check == '{') {
                        return false;
                    }
                    break;
            }
        }
        return stack.isEmpty();
    }
}