package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    WeightedQuickUnionUF FULL;
    WeightedQuickUnionUF PERC;
    int[][] OPEN;
    int numOpen;
    int indUp;
    int indDown;
    int N;
    public Percolation(int N) {
        FULL = new WeightedQuickUnionUF(N * N + 1);
        PERC = new WeightedQuickUnionUF(N * N + 2);
        OPEN = new int[N][N];
        numOpen = 0;
        indUp = N * N;
        indDown = N * N + 1;
        this.N = N;
    }

    public void open(int row, int col) {
        if (OPEN[row][col] == 0) {
            numOpen += 1;
            OPEN[row][col] = 1;
            connectOpenNeighbor(row, col);
            if (isUpEdge(row, col)) {
                PERC.union(getInd(row, col), indUp);
                FULL.union(getInd(row, col), indUp);
            }
            if (isDownEdge(row, col)) {
                PERC.union(getInd(row, col), indDown);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return OPEN[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        return FULL.find(getInd(row, col)) == FULL.find(indUp);
    }

    public int numberOfOpenSites() {
        return numOpen;
    }

    public boolean percolates() {
        return PERC.find(indDown) == PERC.find(indUp);
    }

    private void connectOpenNeighbor(int row, int col) {
        if (row + 1 < N) {
            if (isOpen(row + 1, col)) {
                PERC.union(getInd(row, col), getInd(row + 1, col));
                FULL.union(getInd(row, col), getInd(row + 1, col));
            }
        }
        if (row - 1 >= 0) {
            if (isOpen(row - 1, col)) {
                PERC.union(getInd(row, col), getInd(row - 1, col));
                FULL.union(getInd(row, col), getInd(row - 1, col));
            }
        }
        if (col + 1 < N) {
            if (isOpen(row, col + 1)) {
                PERC.union(getInd(row, col), getInd(row, col + 1));
                FULL.union(getInd(row, col), getInd(row, col + 1));
            }
        }
        if (col - 1 >= 0) {
            if (isOpen(row, col - 1)) {
                PERC.union(getInd(row, col), getInd(row, col - 1));
                FULL.union(getInd(row, col), getInd(row, col - 1));
            }
        }
    }

    private boolean isDownEdge(int row, int col) {
        return row == N - 1;
    }

    private int getInd(int row, int col) {
        return row * N + col;
    }

    private boolean isUpEdge(int row, int col) {
        return row == 0;
    }
}
