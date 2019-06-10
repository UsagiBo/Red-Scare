import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algorithms.Digraph;
import edu.princeton.cs.algorithms.DirectedCycle;
import edu.princeton.cs.algorithms.FlowEdge;

public class Many extends Base {
    int result = 0;

    public Many(Graph g) {
        super(g);
    }

    @Override
    public void solve() {
        if(this.graph.isDirected() && !isCyclic()){
            // Initialise all distances to INT_MAX (overestimating)
            changeWeights();

            // as relax() does not count start vertex when computing distance of reds to all nodes, we add it here if necessary
            result = this.graph.getStart().isRed() ? relax() + 1 : relax();
        }
    }

    private void changeWeights() {
        LinkedList<Node> nodes = new LinkedList<>();
        Node root = graph.getStart();
        HashMap<Node, Boolean> map = new HashMap<>();
        nodes.add(root);

        // special case, set end-vertex to have distance as while-loop below may not visit end-node
        // used to check for path in printResult
        this.graph.getEnd().setDistance(Integer.MAX_VALUE);

        while (!nodes.isEmpty()) {
            Node node = nodes.poll();

            map.put(node, true);
            node.setDistance(Integer.MAX_VALUE);

            for (Edge edge : node.getEdges()) {
                if (map.get(edge.getEnd()) == null) {
                    nodes.push(edge.getEnd());
                }
            }
        }
        this.graph.getStart().setDistance(0);
    }

    private boolean isCyclic()
    {
       Digraph d = new Digraph(graph.getNodeNum());
       for(FlowEdge e: graph.getEdgesForFlow()) {
           d.addEdge(e.from(), e.to());
       }
       return (new DirectedCycle(d)).hasCycle();
    }

    private int relax() {
        LinkedList<Node> nodes = new LinkedList<>();
        Node root = graph.getStart();
        HashMap<Node, Boolean> map = new HashMap<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {
            Node node = nodes.poll();
            map.put(node, true);

            node.setDiscovered(true);

            for (Edge edge : node.getEdges()) {
                // if is red, then we set distance to -1, else 0
                Node edgeEnd = edge.getEnd();
                int tempValueIsRed = edgeEnd.isRed() ? -1 : 0;

                if (node.getDistance() + tempValueIsRed < edgeEnd.getDistance()) {
                    edgeEnd.setDistance(node.getDistance() + tempValueIsRed);
                }
            }
            for (Edge edge : node.getEdges()) {
                Node to = edge.getEnd();
                if(!to.isDiscovered() && !nodes.contains(to)){
                    nodes.add(to);
                    to.setShortestTo(edge);
                }
            }
        }
        return Math.abs(this.graph.getEnd().getDistance());
    }

    @Override
    public void printResult() {
        if(!this.graph.isDirected()){
            System.out.print("?!\t");
        }
        // If we never visited end-vertex it will still have distance INT_MAX
        else if (this.graph.getEnd().getDistance() == Integer.MAX_VALUE) {
            System.out.print("-1\t");
        } 
        else {
            System.out.print(result + "\t");
        }
    }
}
