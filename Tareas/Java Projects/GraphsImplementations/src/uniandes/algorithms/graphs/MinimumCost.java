package uniandes.algorithms.graphs;

public interface MinimumCost {
    /**
	 * Calculates the minimum cost path matrix from every pair of nodes
     * in the desired graph
	 * @param graph Representation of a directed graph
	 * @return int [][] minimum cost for each pair of nodes 
	 */
	public int [][] getMinCostMatrix(Graph graph);
}
