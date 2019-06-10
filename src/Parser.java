import edu.princeton.cs.algorithms.FlowEdge;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Parser {

    static Graph parse(String fileLocation) {
        try (
                Scanner in = new Scanner(new FileReader(fileLocation));
        ) {
            // read s and t (s and t are both in NODES)
            // then read nodenum nodes, if red, number is followed by * (66 *)
            // then read edgenum edges, (a -- b is undirected), (a -> b is directed)

            boolean isDirected = false;

            String[] counts = in.nextLine().split(" ");
            int nodenum = Integer.parseInt(counts[0]);
            int edgenum = Integer.parseInt(counts[1]);
            int cardinality = Integer.parseInt(counts[2]);

            HashMap<String, Node> nodes = new HashMap<>();
            Map<Node, Integer> nodeToIndex = new HashMap<>();
            List<FlowEdge> edges4Flow = new ArrayList<>();
            HashSet<Integer> redNodes = new HashSet<>();
            String[] specialNodes = in.nextLine().split(" ");

            for (int i = 0; i < nodenum; i++) {
                String[] line = (in.nextLine()).split(" ");
                Node n = new Node(line[0]);
                n.setRed(line.length > 1);
                if (n.isRed()) {
                    redNodes.add(i);
                }
                nodes.put(line[0], n);
                nodeToIndex.put(n, i);
            }

            // To check if the graph is directed, we first peek and check if it is
            // Then, skipping the first, we read the rest of the edge-data
            if (edgenum == 0) {
            } else {
                String n = in.nextLine();

                if (!(n.split(" ")[1].equals("--"))) {
                    isDirected = true;
                }
                for (int i = 0; i < edgenum; i++) {
                    String[] parts = n.split(" ");
                    Node from = nodes.get(parts[0]);
                    Node to = nodes.get(parts[2]);
                    if (parts[1].equals("--")) {
                        // create both ways as is undirected
                        to.addEdge(new Edge(to, from));
                        from.addEdge(new Edge(from, to));
                        FlowEdge fEfrom = new FlowEdge(nodeToIndex.get(from), nodeToIndex.get(to), 1);
                        FlowEdge fEto = new FlowEdge(nodeToIndex.get(to), nodeToIndex.get(from), 1);
                        edges4Flow.add(fEfrom);
                        edges4Flow.add(fEto);
                    } else {
                        from.addEdge(new Edge(from, to));
                    }
                    if (i == edgenum - 1) {
                        break;
                    }
                    n = in.nextLine();
                }
            }
            return new Graph(nodes.get(specialNodes[0]), nodes.get(specialNodes[1]), nodenum, isDirected, nodeToIndex, edges4Flow, redNodes);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
