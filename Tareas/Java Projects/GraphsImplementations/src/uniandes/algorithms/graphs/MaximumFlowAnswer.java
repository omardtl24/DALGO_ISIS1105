package uniandes.algorithms.graphs;

import java.util.Map;

public class MaximumFlowAnswer {
    
    private int maximumFlow;
    private Map<EdgeArray, Integer> flows;

    /**
     * Constructs a MaximumFlowAnswer object to store the results of a maximum flow computation.
     * 
     * @param f A map representing the flow values for each edge in the graph.
     *          The keys are EdgeArray objects representing the edges, and the values are the corresponding flow amounts.
     * @param maxFlow The total maximum flow value from the source to the sink.
     */
    public MaximumFlowAnswer(Map<EdgeArray, Integer> f, int maxFlow) {
        maximumFlow = maxFlow;
        flows = f;
    }

    /**
     * Retrieves the maximum flow value calculated from the source to the sink.
     * 
     * @return The total maximum flow value.
     */
    public int getMaxFlowValue() {
        return maximumFlow;
    }

    /**
     * Retrieves the map of flows for each edge in the graph.
     * 
     * @return A map where the keys are EdgeArray objects and the values are the corresponding flow amounts.
     */
    public Map<EdgeArray, Integer> getFlows() {
        return flows;
    }
}
