import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        for(String path : args){
        Graph graph = Parser.parse(path);
        Path p = Paths.get(path);
        String file = p.getFileName().toString();
        System.out.print(file + "\t" + graph.getNodeNum() + "\t");
        IProblem[] problems = {new Alternate(Parser.parse(path)), new Few(Parser.parse(path)), new Many(Parser.parse(path)), new None(Parser.parse(path)), new Some(Parser.parse(path))};
        Arrays.stream(problems).forEach(i -> {
            i.solve();
            i.printResult();

        });}

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
                if (!to.isDiscovered() && !nodes.contains(to)) {
                    nodes.add(to);
                    to.setShortestTo(edge);
                }
            }
        }
    }

}
