import edu.princeton.cs.algorithms.FlowEdge;

import java.util.*;

public class Graph {
    private final List<List<Integer>> adj;
    private Node start;
    private Node end;
    private boolean isDirected;
    private int nodeNum;
    private Map<Node, Integer> nodes;
    private List<FlowEdge> edgesForFlow = new ArrayList<>();
    private HashSet<Integer> redNodes = new HashSet<>();


    public Graph(Node start, Node end, int nodeNum, boolean isDirected, Map<Node, Integer> nodes, List<FlowEdge> edgesForFlow, HashSet<Integer> redNodes) {
        this.start = start;
        this.end = end;
        this.nodeNum = nodeNum;
        this.isDirected = isDirected;
        this.nodes = nodes;
        this.edgesForFlow = edgesForFlow;
        this.redNodes = redNodes;
        adj = new ArrayList<>(nodeNum);
        for (int i = 0; i < nodeNum; i++) {
            adj.add(new LinkedList<>());
        }
    }

    public HashSet<Integer> getRedNodes() {
        return this.redNodes;
    }

    public List<FlowEdge> getEdgesForFlow() {
        return this.edgesForFlow;
    }

    public Map<Node, Integer> getNodes() {
        return this.nodes;
    }

    public int getNodeNum() {
        return this.nodeNum;
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public boolean isDirected() {
        return isDirected;
    }

    // returns true if contains cycle
    // inspiration: https://www.geeksforgeeks.org/detect-cycle-in-a-graph/
    public boolean isCyclic() {
        // Mark all the vertices as not visited and 
        // not part of recursion stack 
        boolean[] visited = new boolean[nodeNum];
        boolean[] recStack = new boolean[nodeNum];


        // Call the recursive helper function to 
        // detect cycle in different DFS trees 
        for (int i = 0; i < nodeNum; i++)
            if (cyclicRecursive(i, visited, recStack))
                return true;

        return false;
    }

    public boolean cyclicRecursive(int i, boolean[] visited, boolean[] recStack) {
        // Mark the current node as visited and 
        // part of recursion stack 
        if (recStack[i])
            return true;

        if (visited[i])
            return false;

        visited[i] = true;

        recStack[i] = true;
        List<Integer> children = adj.get(i);

        for (Integer c : children)
            if (cyclicRecursive(c, visited, recStack))
                return true;

        recStack[i] = false;

        return false;
    }
}
