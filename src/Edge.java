public class Edge {
    private final Node start;
    private final Node end;
    private int distance = 1;

    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }
}
