import java.util.*;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.Graph;

public class Some extends Base {

    public Some(Graph g) {
        super(g);
    }

    private Graph parserGraph;
    private edu.princeton.cs.algs4.Graph flowGraph;
    private String result;
    private int newSource;
    private int newSink;
    // may not work with cycles?,
    //Graph start end isDirected nodenumber nodes

    @Override
    public void solve() {
        //construct graph
        FlowNetwork flowNetwork = new FlowNetwork(graph.getNodeNum() + 2);
        //construct flow edges and add weight
        flowNetwork = setFlowEdges(flowNetwork, parserGraph.getNodes());
        //create new source, connect to old source and to old sink
        newSource = parserGraph.getNodeNum();
        newSink = parserGraph.getNodeNum() + 1;
        FlowEdge e1 = new FlowEdge(newSource, parserGraph.getNodes().get(parserGraph.getStart()), 1);
        FlowEdge e2 = new FlowEdge(newSource, parserGraph.getNodes().get(parserGraph.getEnd()), 1);
        flowNetwork.addEdge(e1);
        flowNetwork.addEdge(e2);
        List<Integer> redNodes = null;
        for (Map.Entry< Node,Integer> entry : parserGraph.getNodes().entrySet()){
            if (entry.getKey().isRed()){redNodes.add(entry.getValue());}
        }
        //solve some
        if (!parserGraph.isDirected()) {

            try {
                if (!redNodes.equals(null)){
                for ( Integer red : redNodes) {
                    FlowNetwork g = flowNetwork;
                    Iterator<FlowEdge> iterator = g.edges().iterator();
                    FlowNetwork gCopy = new FlowNetwork(g.V());
                    while (iterator.hasNext()) {
                        gCopy.addEdge(iterator.next());
                    }
                    FlowEdge e = new FlowEdge(red, newSink, 2);
                    gCopy.addEdge(e);

                    FordFulkerson ff = new FordFulkerson(gCopy,
                            newSource, newSink);

                    if (Math.round(ff.value()) == 2) {
                        result = "true";
                    }
                }
                }
                else {result = "false";}
            } catch (RuntimeException e) {
            }
        } else {
            result = "Graph is Not Directed";
        }
        result = "false";

    }
//modify!
    private FlowNetwork setFlowEdges(FlowNetwork fN, Map<Node,Integer> allNodes ) {
        LinkedList<Node> currentNodes = new LinkedList<>();
        Node source = graph.getStart();
        HashMap<Node, Boolean> map = new HashMap<>();
        currentNodes.add(source);
        while (!currentNodes.isEmpty()) {
            Node node = currentNodes.poll();
            map.put(node, true);
            for (Edge edge : node.getEdges()) {
                if (map.get(edge.getEnd()) == null) {
                    //create FlowEdge and add to graph
                    FlowEdge graphEdge = new FlowEdge( allNodes.get(edge.getStart()) ,allNodes.get(edge.getEnd()), (node.isRed() ? 1:0 ));
                    fN.addEdge(graphEdge);
                    currentNodes.push(edge.getEnd());
                }
            }
        }
        return fN;
    }

    @Override
    public void printResult() {
        System.out.println("Some: path exists: " + (graph.getEnd().getRedDistance() == 0));
    }
}
