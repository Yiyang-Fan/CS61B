package hw4.puzzle;

public class SearchNode implements Comparable<SearchNode> {
    public WorldState state;
    public SearchNode prev;
    public int step;
    public int prior;
    public SearchNode(WorldState state, int step, SearchNode prev) {
        this.state = state;
        this.prev = prev;
        this.step = step;
        prior = state.estimatedDistanceToGoal() + step;
    }

    @Override
    public int compareTo(SearchNode o) {
        return this.prior - o.prior;
    }
}
