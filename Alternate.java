import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Alternate extends Base {
    public Alternate(Graph g) {
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
            node.setEdges(node.getEdges().stream().filter(e -> e.getEnd().isRed() != e.getStart().isRed()).collect(Collectors.toList()));
            for (Edge edge: node.getEdges()) {
                if (map.get(edge.getEnd()) == null) {
                    nodes.push(edge.getEnd());
                }
            }
        }
    }

    @Override
    public void printResult() {
        System.out.println("Alternate: path exists: " + pathExists());
    }

    private boolean pathExists() {
        Node n = graph.getEnd();
        while (n != null) {
            if (n.getShortestTo() == null) {
                break;
            }
            n = n.getShortestTo().getStart();
        }

        return n == graph.getStart();
    }
}


