import java.util.HashSet;
import java.util.Set;

public class Node {
    private long id, version;
    private double lat, lon;
    // The ways that included this Node
    private Set<Long> ways;
    private String name;
    // The directly adjacent nodes to this Node
    private Set<Long> adjNodes;
    public Node(long id, double lat, double lon, long version) {
        this.id = id;
        this.version = version;
        this.lat = lat;
        this.lon = lon;
        ways = new HashSet<>();
        adjNodes = new HashSet<>();
    }
    public double lon() {
        return lon;
    }
    public double lat() {
        return lat;
    }
    public Set<Long> adj() {
        return this.adjNodes;
    }

    public void addWay(long wid) {
        ways.add(wid);
    }

    public void addName(String name) {
        this.name = name;
    }
    public void addAdj(long id) {
        adjNodes.add(id);
    }
    public boolean connectedToOther() {
        return adjNodes.size() > 0;
    }
}