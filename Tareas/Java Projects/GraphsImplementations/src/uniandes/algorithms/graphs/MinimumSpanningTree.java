package uniandes.algorithms.graphs;

import java.util.List;

public interface MinimumSpanningTree {
	/* Get the list of edges that are part of a minimum spanning tree
	 * 
	 * @param graph Representation of graph
	 * @return Minimum Spanning Tree linked to the graph
	 */
	public List<int[]> getMST(Graph graph);
}
