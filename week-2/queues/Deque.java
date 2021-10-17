import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null, last = null;
    private int size = 0;

    private class Node {
        private final Item item;
        private Node next, prev;

        public Node(Item item) {
            this.item = item;
            next = null;
            prev = null;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;

            return item;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node node = new Node(item);

        if (size == 0) {
            first = node;
            last = node;
        }
        else {
            node.next = first;
            first.prev = node;
            first = node;
        }

        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node node = new Node(item);

        if (size == 0) {
            first = node;
            last = node;
        }
        else {
            last.next = node;
            node.prev = last;
            last = node;
        }

        size++;
    }

    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        Item item = first.item;
        first = first.next;

        if (first == null) {
            last = null;
        }
        else {
            first.prev = null;
        }

        size--;

        return item;
    }

    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        Item item = last.item;
        last = last.prev;

        if (last == null) {
            first = null;
        }
        else {
            last.next = null;
        }

        size--;

        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        deque.addLast(1);
        deque.addLast(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addLast(6);

        for (int i : deque) {
            StdOut.print(i + " ");
        }

        StdOut.println();
        StdOut.println("Size: " + deque.size());

        StdOut.println("----------");
        StdOut.println("Remove First: " + deque.removeFirst());
        StdOut.println("Remove First: " + deque.removeFirst());
        StdOut.println("Remove First: " + deque.removeFirst());
        StdOut.println("Remove Last: " + deque.removeLast());
        StdOut.println("----------");

        for (int i : deque) {
            StdOut.print(i + " ");
        }

        StdOut.println();
        StdOut.println("Size: " + deque.size());
    }
}
