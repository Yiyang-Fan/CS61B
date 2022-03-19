package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Queue<Integer> exploring;
    private int source;
    private int target;
    private boolean targetFound = false;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        source = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
        exploring = new LinkedList<>();
        exploring.add(source);
        distTo[source] = 0;
        edgeTo[source] = source;
        System.out.println(targetX);
        System.out.println(targetY);
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        while (!exploring.isEmpty()) {
            int r = exploring.remove();
            if (r == target) {
                targetFound = true;
            }
            if (targetFound) {
                marked[r] = true;
                announce();
                return;
            }
            if (!marked[r]) {
                marked[r] = true;
                announce();
                for (int t : maze.adj(r)) {
                    if (!marked[t]) {
                        distTo[t] = distTo[r] + 1;
                        edgeTo[t] = r;
                        exploring.add(t);
                    }
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

