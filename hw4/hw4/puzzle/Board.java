package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int[][] tiles;
    private final int BLANK = 0;

    public Board(int[][] tiles) {
        this.tiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    @Override
    public int estimatedDistanceToGoal() {
        /*
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                System.out.printf("%d  ", tileAt(i, j));
            }
            System.out.println();
        }
        System.out.printf("Manhattan %d \n", manhattan());
        */
        return manhattan();
    }

    public int tileAt(int i, int j) {
        return tiles[i][j];
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    public int size() {
        return tiles.length;
    }

    public boolean equals(Object y) {
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tileAt(i, j) != ((Board) y).tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hamming() {
        int dist = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tileAt(i, j) == BLANK) {
                    if (i != size() - 1 && j != size() - 1) {
                        dist += 1;
                    }
                } else if (tileAt(i, j) != i * size() + j + 1) {
                    dist += 1;
                }
            }
        }
        return dist;
    }

    public int manhattan() {
        int dist = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                int t = tileAt(i, j);
                int col, row;
                if (t != BLANK) {
                    col = (t - 1) % size();
                    row = (t - 1) / size();
                    dist += (Math.abs(i - row) + Math.abs(j- col));
                }
            }
        }
        return dist;
    }
}
