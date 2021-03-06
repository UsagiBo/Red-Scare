import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class None extends Base {
    public None(Graph g) {
        super(g);
    }

    @Override
    public void solve() {
        removeRed();
        Main.bfs(this.graph);
    }

    private void removeRed() {
        LinkedList<Node> nodes = new LinkedList<>();
        Node root = graph.getStart();
        HashMap<Node, Boolean> map = new HashMap<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {
            Node node = nodes.poll();
            map.put(node, true);
            node.setEdges(node.getEdges().stream().filter(e -> !e.getEnd().isRed() || e.getEnd() == graph.getEnd()).collect(Collectors.toList()));
            for (Edge edge : node.getEdges()) {
                if (map.get(edge.getEnd()) == null) {
                    nodes.push(edge.getEnd());
                }
            }
        }
    }

    @Override
    public void printResult() {
        System.out.print(pathExists() + "\t");
    }

    private int pathExists() {
        Node n = graph.getEnd();
        int i = 0;
        while (true) {
            if (n.getShortestTo() == null) {
                break;
            }
            n = n.getShortestTo().getStart();
            i+= 1;
        }
        return n == graph.getStart() ? i : -1;
    }
}
