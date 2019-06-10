import java.util.*;

public class Graph {
    private Node start;
    private Node end;
    private boolean isDirected;
    private int nodeNum;
    private final List<List<Integer>> adj;
    private Map<Node,Integer> nodes;


    public Graph(Node start, Node end, int nodeNum, boolean isDirected, Map<Node,Integer> nodes) {
        this.start = start;
        this.end = end;
        this.nodeNum = nodeNum;
        this.isDirected = isDirected;
        this.nodes = nodes;
        adj = new ArrayList<>(nodeNum); 
        for (int i = 0; i < nodeNum; i++){
            adj.add(new LinkedList<>()); 
        }
    }

    public Map<Node,Integer> getNodes(){return this.nodes;}

    public int getNodeNum(){return this.nodeNum;}

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

    public boolean isDirected(){
        return isDirected;
    }

    // returns true if contains cycle
    // inspiration: https://www.geeksforgeeks.org/detect-cycle-in-a-graph/
    public boolean isCyclic(){
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

    public boolean cyclicRecursive(int i, boolean[] visited, boolean[] recStack){ 
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
