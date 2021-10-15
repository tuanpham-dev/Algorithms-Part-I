import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean, stddev, confidenceLo, confidenceHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trails is not valid");
        }

        double[] fractions = new double[trials];
        Percolation percolation;

        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);

            do {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;

                percolation.open(row, col);
            } while (!percolation.percolates());

            fractions[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }

        mean = StdStats.mean(fractions);
        stddev = StdStats.stddev(fractions);
        double tmp = 1.96 * stddev / Math.sqrt(trials);
        confidenceLo = mean - tmp;
        confidenceHi = mean + tmp;
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confidenceLo;
    }

    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("You need to provide 2 arguments");
        }

        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),
                                                      Integer.parseInt(args[1]));

        StdOut.printf("%-23s = %f\n", "mean", stats.mean());
        StdOut.printf("%-23s = %.18f\n", "stddev", stats.stddev());
        StdOut.printf("%-23s = [%.18f, %.18f]\n", "95% confidence interval", stats.confidenceLo(),
                      stats.confidenceHi());
    }
}
