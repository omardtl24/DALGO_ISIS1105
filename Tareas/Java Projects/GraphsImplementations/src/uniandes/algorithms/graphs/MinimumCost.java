package uniandes.algorithms.graphs;

public interface MinimumCost {
    /**
	 * Calculates the minimum cost path matrix from every pair of nodes
     * in the desired graph
	 * @param graph Representation of a weighted directed graph
	 * @return int [][] minimum cost for each pair of nodes
	 * @throws Exception if there is any invalid operation
	 */
	public int [][] getMinCostMatrix(WeightedDiGraph graph) throws Exception;
}
