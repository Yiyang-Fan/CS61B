package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    double[] result;
    StdRandom rand;
    StdStats stat;
    int T;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        this.T = T;
        result = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            int k = 0;
            while (!p.percolates()) {
                int randRow = rand.uniform(N);
                int randCol = rand.uniform(N);
                while (p.isOpen(randRow, randCol)) {
                    randRow = rand.uniform(N);
                    randCol = rand.uniform(N);
                }
                p.open(randRow, randCol);
                k += 1;
            }
            result[i] = k * 1.0 / (N * N * 1.0);
        }
    }
    public double mean() {
        return stat.mean(result);
    }
    public double stddev() {
        return stat.stddev(result);
    }
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
