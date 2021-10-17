import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size = 0;
    private Item[] list = (Item[]) new Object[1];

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] randomIndexList = StdRandom.permutation(size);
        private int currentIndex = 0;

        public boolean hasNext() {
            return currentIndex < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = list[randomIndexList[currentIndex]];
            currentIndex++;

            return item;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int newSize) {
        Item[] newList = (Item[]) new Object[newSize];

        for (int i = 0; i < size; i++) {
            newList[i] = list[i];
        }

        list = newList;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (size == list.length) {
            resize(list.length * 2);
        }

        list[size++] = item;
    }

    private Item getRandomItem(boolean remove) {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        int randomIndex = StdRandom.uniform(size);
        Item item = list[randomIndex];

        if (remove) {
            if (randomIndex != size - 1) {
                list[randomIndex] = list[size - 1];
            }

            list[--size] = null;

            if (size > 0 && size == list.length / 4) {
                resize(list.length / 2);
            }
        }

        return item;
    }

    public Item dequeue() {
        return getRandomItem(true);
    }

    public Item sample() {
        return getRandomItem(false);
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);

        for (int i : queue) {
            StdOut.print(i + " ");
        }

        StdOut.println();
        StdOut.println("Size: " + queue.size());

        StdOut.println("----------");
        StdOut.println("Dequeue: " + queue.dequeue());
        StdOut.println("Dequeue: " + queue.dequeue());
        StdOut.println("Sample: " + queue.sample());
        StdOut.println("----------");

        for (int i : queue) {
            StdOut.print(i + " ");
        }

        StdOut.println();
        StdOut.println("Size: " + queue.size());
    }
}
