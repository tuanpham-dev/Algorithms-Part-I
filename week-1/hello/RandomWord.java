import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 0;
        String champion = "", word;

        while (!StdIn.isEmpty()) {
            i++;
            word = StdIn.readString();

            if (StdRandom.bernoulli(1 / (double) i)) {
                champion = word;
            }
        }

        StdOut.println(champion);
    }
}
