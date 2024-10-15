package uniandes.algorithms.graphs;

public interface MaximumFlow {
    
    /**
     * Computes the maximum flow in a weighted directed graph from a specified source to a sink.
     * 
     * @param graph The weighted directed graph in which to find the maximum flow.
     *              It must not be null and should contain valid edges between the source and sink.
     * @param source The node representing the source of the flow.
     *               It must be a valid node in the graph.
     * @param sink The node representing the sink of the flow.
     *             It must be a valid node in the graph and different from the source.
     * @return A MaximumFlowAnswer object containing the result of the maximum flow computation.
     *         This includes the flow value and possibly other relevant details.
     * @throws Exception If the graph is invalid, if there are issues during computation,
     *                   or if the source and sink nodes are not valid.
     */
    public MaximumFlowAnswer getMaximumFlow(WeightedDiGraph graph, int source, int sink) throws Exception;
}
