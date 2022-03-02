package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {
    private MinPQ<SearchNode> fringe;
    private LinkedList<WorldState> sol;
    public Solver(WorldState initial) {
        fringe = new MinPQ();
        SearchNode sn = new SearchNode(initial, 0, null);
        fringe.insert(sn);
        sol = new LinkedList<>();

        while (!fringe.isEmpty()) {
            sn = fringe.delMin();
            if (!sn.state.isGoal()) {
                for (WorldState ws: sn.state.neighbors()) {
                    if (sn.prev == null || (sn.prev != null && !ws.equals(sn.prev.state))) {
                        fringe.insert(new SearchNode(ws, sn.step + 1, sn));
                    }
                }
            } else {
                while (sn != null) {
                    sol.add(0, sn.state);
                    sn = sn.prev;
                }
                break;
            }
        }
        // recSolve();
    }

    private void recSolve() {
        SearchNode sn = fringe.delMin();

        if (!sn.state.isGoal()) {
            for (WorldState ws: sn.state.neighbors()) {
                if (sn.prev == null || (sn.prev != null && !ws.equals(sn.prev.state))) {
                    fringe.insert(new SearchNode(ws, sn.step + 1, sn));
                }
            }
            recSolve();
        } else {
            while (sn != null) {
                sol.add(0, sn.state);
                sn = sn.prev;
            }
        }
    }

    public int moves() {
        return sol.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return sol;
    }


}
