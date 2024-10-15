package uniandes.algorithms.graphs;

public class FloydWarshallMinCost implements MinimumCost {
    /**
     * Calculates the minimum cost matrix using the Floyd-Warshall algorithm.
     *
     * @param graph A 2D array representing the graph, where graph[i][j] is the cost from node i to node j.
     *              Integer.MAX_VALUE is used to indicate no direct connection.
     * @return A 2D array representing the minimum cost between all pairs of nodes.
     * @throws Exception 
     */
    public int[][] getMinCostMatrix(WeightedDiGraph graph) throws Exception {
        int n = graph.numNodes(); // Get the number of nodes in the graph
        int[][] m = new int[n][n]; // Initialize the cost matrix

        // Initialize the minimum cost matrix with direct connections
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = graph.cost(i, j); // Set initial costs from the graph
            }
        }

        // Floyd-Warshall algorithm to find minimum costs
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // Update the cost m[i][j] to the minimum cost considering node k
                    m[i][j] = Math.min(m[i][j], adder(m[i][k], m[k][j]));
                }
            }
        }

        return m; // Return the final minimum cost matrix
    }

    /**
     * Adds two integers, accounting for Integer.MAX_VALUE.
     * If either integer is Integer.MAX_VALUE, the result is Integer.MAX_VALUE.
     *
     * @param a First integer to add.
     * @param b Second integer to add.
     * @return The sum of a and b or Integer.MAX_VALUE if either is max.
     */
    public int adder(int a, int b) {
        if (a == Integer.MAX_VALUE || b == Integer.MAX_VALUE) return Integer.MAX_VALUE;
        return a + b; // Return the sum of a and b
    }
}
