import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();

            queue.enqueue(word);
        }

        if (k > 0) {
            int i = 0;
            for (String item : queue) {
                i++;
                StdOut.println(item);

                if (i == k) {
                    break;
                }
            }
        }
    }
}
