import java.util.*;

public class Many extends Base {
    public Many(Graph g) {
        super(g);
    }
    
    int result = 0;

    @Override
    public void solve() {

        // Initialise all distances to INT_MAX (overestimating)
        changeWeights();

        // as relax() does not count start vertex when computing distance of reds to all nodes, we add it here if necessary
        result = this.graph.getStart().isRed() ? relax()+1 : relax();

        // CONSIDER DOING THE FINAL SCAN EXPLAINED HERE TO TEST FOR CYCLES?
        // https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm
        // make sure it actually works, and doesn't just detect cycles including a red vertex (as it has -1)

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

    private int relax(){
        LinkedList<Node> nodes = new LinkedList<>();
        Node root = graph.getStart();
        HashMap<Node, Boolean> map = new HashMap<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {
            Node node = nodes.poll();
            map.put(node, true);

            for (Edge edge : node.getEdges()) {
                // if is red, then we set distance to -1, else 0
                Node edgeEnd = edge.getEnd();
                int tempValueIsRed = edgeEnd.isRed() ? -1 : 0;

                if(node.getDistance() + tempValueIsRed < edgeEnd.getDistance()){
                    edgeEnd.setDistance(node.getDistance() + tempValueIsRed);
                }
            }
            for (Edge edge : node.getEdges()) {
                nodes.push(edge.getEnd());
            }
        }
        return Math.abs(this.graph.getEnd().getDistance());
    }

    @Override
    public void printResult() {
        // If we never visited end-vertex it will still have distance INT_MAX
        if(this.graph.getEnd().getDistance() == Integer.MAX_VALUE){
            System.out.println("Many: no path found");
        }
        else{
            System.out.println("Many: path found going through "+result+" red vertices");
        }
    }
}