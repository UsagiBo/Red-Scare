import java.util.ArrayList;
import java.util.List;

public class Node {
    private final String name;
    private List<Edge> edges = new ArrayList<>();
    private boolean red;
    private int redDistance = Integer.MAX_VALUE;
    private Edge shortestTo;
    private int distance = Integer.MAX_VALUE;
    private boolean discovered = false;

    public Node(String name) {
        this.name = name;
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public Edge getShortestTo() {
        return shortestTo;
    }

    public void setShortestTo(Edge shortestTo) {
        this.shortestTo = shortestTo;
    }

    public String getName() {
        return name;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", edges=" + edges +
                '}';
    }

    public int getRedDistance() {
        return redDistance;
    }

    public void setRedDistance(int redDistance) {
        this.redDistance = redDistance;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
