import edu.princeton.cs.algorithms.FlowEdge;
import edu.princeton.cs.algorithms.FlowNetwork;
import edu.princeton.cs.algorithms.FordFulkerson;

import java.util.Iterator;

public class Some extends Base {

    private boolean result;

    public Some(Graph g) {
        super(g);
    }

    @Override
    public void solve() {
        FlowNetwork flowNetwork = new FlowNetwork(this.graph.getNodeNum() + 2);
        for (FlowEdge FE : this.graph.getEdgesForFlow()) {
            flowNetwork.addEdge(FE);
        }
        //create new source, connect to old source and to old sink
        final int newSource = this.graph.getNodeNum();
        final int newSink = this.graph.getNodeNum() + 1;
        FlowEdge e1 = new FlowEdge(newSource, this.graph.getNodes().get(this.graph.getStart()), 1);
        FlowEdge e2 = new FlowEdge(this.graph.getNodes().get(this.graph.getEnd()), newSource, 1);
        flowNetwork.addEdge(e1);
        flowNetwork.addEdge(e2);
        //solve some

        result = false;
        for (Integer red : this.graph.getRedNodes()) {
            FlowNetwork fNet = flowNetwork;
            Iterator<FlowEdge> iterator = fNet.edges().iterator();
            FlowNetwork fNetCopy = new FlowNetwork(fNet.V());
            while (iterator.hasNext()) {
                fNetCopy.addEdge(iterator.next());
            }

            FlowEdge o = new FlowEdge(newSource, red, 1);
            FlowEdge e = new FlowEdge(red, newSink, 2);
            fNetCopy.addEdge(e);
            fNetCopy.addEdge(o);
            FordFulkerson fordFulk;
            try {
                fordFulk = new FordFulkerson(fNetCopy,
                        newSource, newSink);
                if (Math.round(fordFulk.value()) == 2) {
                    result = true;
                    break;
                }
            } catch (RuntimeException a) {
            }

        }

    }

    @Override
    public void printResult() {
        System.out.print("" + result + "\t");
    }
}
