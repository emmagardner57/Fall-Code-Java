package hw2;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private int trials;
    private double[] ratioList;
    private Percolation board;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        trials = T;
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        ratioList = new double[trials];
        for (int i = 0; i < T; i++) {
            board = pf.make(N);
            while (!board.percolates()) {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                board.open(row, col);
            }
            double val = board.numberOfOpenSites();
            val = val / (N * N);
            ratioList[i] = val;
        }
    }
    public double mean() {
        double mean = StdStats.mean(ratioList);
        return mean;
    }
    public double stddev() {
        double val = StdStats.stddev(ratioList);
        return val;
    }
    public double confidenceLow() {
        double mean = this.mean();
        double stdDev = this.stddev();
        stdDev *= 1.96;
        stdDev /= Math.sqrt(trials);
        mean -= stdDev;
        return mean;
    }
    public double confidenceHigh() {
        double mean = this.mean();
        double stdDev = this.stddev();
        stdDev *= 1.96;
        stdDev /= Math.sqrt(trials);
        mean += stdDev;
        return mean;
    }

}
