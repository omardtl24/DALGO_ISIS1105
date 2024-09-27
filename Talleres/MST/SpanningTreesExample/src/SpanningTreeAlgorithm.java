import java.util.List;

/**
 * Common interface for algorithms to calculate spanning trees
 * @author Jorge Duitama
 */
public interface SpanningTreeAlgorithm {
	/**
	 * Calculates an spanning tree for the undirected graph defined by the given edges
	 * PRE: Vertices of the graph are the numbers from 0 to n-1. The graph is connected.
	 * POST: The graph induced by the output edges connects all vertices of the input graph
	 * without cicles and has minimum cost.
	 * @param graph List of edges defining the graph. 
	 * @return List<UndirectedWeightedEdge> edges making the spanning tree.
	 */
	public List<UndirectedWeightedEdge> findSpanningTree(List<UndirectedWeightedEdge> graph);
}
