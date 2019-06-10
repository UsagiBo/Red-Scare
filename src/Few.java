import java.util.HashMap;
import java.util.LinkedList;

public class Few extends Base {
    public Few(Graph g) {
        super(g);
    }

    @Override
    public void solve() {
        changeWeights();
        Djikstra.djikstra(this.graph);
    }

    private void changeWeights() {
        LinkedList<Node> nodes = new LinkedList<>();
        Node root = graph.getStart();
        HashMap<Node, Boolean> map = new HashMap<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {
            Node node = nodes.poll();
            map.put(node, true);
            node.getEdges().forEach(e -> e.setDistance(e.getEnd().isRed() ? 1 : 0));
            for (Edge edge : node.getEdges()) {
                if (map.get(edge.getEnd()) == null) {
                    nodes.push(edge.getEnd());
                }
            }
        }
    }

    @Override
    public void printResult() {
        int dist = graph.getEnd().getDistance();
        if (dist == Integer.MAX_VALUE) {
            dist = -1;
        }
        if (graph.getStart().isRed()) {
            dist += 1;
        }
        System.out.print(dist + "\t");
    }
}
