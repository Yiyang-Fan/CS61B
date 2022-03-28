import java.util.Iterator;
import java.util.LinkedList;

public class Way {
    private long id;
    private long version;
    private LinkedList<Long> nodeInWay;
    private String name;
    private boolean goodWay;
    private String maxSpeed;
    public Way(long id, long version) {
        this.id = id;
        this.version = version;
        this.nodeInWay = new LinkedList<>();
    }
    public void updateName(String name) {
        this.name = name;
    }
    public void updateGoodWay(boolean goodWay) {
        this.goodWay = goodWay;
    }
    public void updateMaxspeed(String maxspeed) {
        this.maxSpeed = maxspeed;
    }
    public void addNode(Long nid) {
        nodeInWay.add(nid);
    }
    public boolean isGoodWay() {
        return goodWay;
    }
    public LinkedList<Long> getNodeInWay() {
        return nodeInWay;
    }
}