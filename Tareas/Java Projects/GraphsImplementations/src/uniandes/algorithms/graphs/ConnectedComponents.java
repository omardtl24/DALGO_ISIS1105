package uniandes.algorithms.graphs;

import java.util.List;

public interface ConnectedComponents {
	/*
	 * Get a list of the disjoint components of an undirected graph
	 */
	public List<List<Integer>> getComponents(UnweightedGraph graph);
}