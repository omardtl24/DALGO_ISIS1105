package uniandes.algorithms.graphs;

import java.util.List;

public interface ConnectedComponents {

    /**
     * Retrieves a list of the disjoint connected components of an undirected graph.
     * Each component is represented as a list of node indices that are connected.
     * 
     * @param graph The undirected graph in which connected components are to be identified.
     * @return A list of connected components, where each component is a list of node indices.
     */
    public List<List<Integer>> getComponents(UnweightedGraph graph);
}
