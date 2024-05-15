package logic;

public class Stack {
    Node tail;

    public Stack() {
        tail = null;
    }

    public void push(Object data) {
        Node temp = new Node();
        temp.data = data;
        temp.below = tail;
        tail = temp;
    }

    public Object top() {
        return tail.data;
    }

    public boolean isEmpty() {
        return tail == null;
    }

    public Object pop() {
        Node temp = tail;
        tail = temp.below;
        temp.below = null;
        return temp.data;
    }
}