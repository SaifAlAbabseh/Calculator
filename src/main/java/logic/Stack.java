package logic;

import java.util.ArrayList;

public class Stack {
    private final ArrayList<Object> stack;
    private int top;

    public Stack() {
        stack = new ArrayList<>();
        top = -1;
    }

    public Object top() {
        return stack.get(top);
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(Object value) {
        stack.add(++top, value);
    }

    public Object pop() {
        return isEmpty() ? null : stack.get(top--);
    }
}