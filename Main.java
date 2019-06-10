import java.util.*;

public class Main {

    public static void main(String[] args) {
        Graph graph = Parser.parse(args[0]);

        IProblem[] problems = {new Alternate(Parser.parse(args[0])), new Some(Parser.parse(args[0]))};
        Arrays.stream(problems).forEach(i -> {
            i.solve();
            i.printResult();
        });
        /*
        IProblem problem = new Many(graph);
        problem.solve();
        problem.printResult();
        */
    }

    public static void bfs(Graph graph) {
        LinkedList<Node> nodes = new LinkedList<>();
        Node root = graph.getStart();
        nodes.add(root);
        root.setRedDistance(0);

        while (!nodes.isEmpty()) {
            Node node = nodes.poll();
            node.setDiscovered(true);
            for (Edge edge : node.getEdges()) {
                Node to = edge.getEnd();
                if (!to.isDiscovered()) {
                    nodes.add(to);
                    to.setShortestTo(edge);
                }
            }
        }
    }
    
}
