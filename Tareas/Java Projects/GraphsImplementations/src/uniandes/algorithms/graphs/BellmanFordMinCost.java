package uniandes.algorithms.graphs;

import java.util.Arrays;

public class BellmanFordMinCost implements MinimumCost {

    /**
     * Computes the minimum cost matrix for all node pairs using the Bellman-Ford algorithm.
     * 
     * @param graph The weighted directed graph where nodes and edges are defined.
     * @return A 2D matrix where element [i][j] represents the minimum cost from node i to node j.
     * @throws Exception If any node pair contains an invalid edge or cost.
     */
    public int[][] getMinCostMatrix(WeightedDiGraph graph) throws Exception {
        int n = graph.numNodes(); // Number of nodes in the graph
        int[][] m = new int[n][n]; // Matrix to store the minimum costs
        
        // Compute the shortest path for each node using Bellman-Ford
        for (int i = 0; i < n; i++) {
            m[i] = BellmanFord(i, graph);
        }
        return m;
    }

    /**
     * Implements the Bellman-Ford algorithm to compute the minimum distances from the source node to all other nodes.
     * 
     * @param source The starting node from which to compute the shortest paths.
     * @param graph The weighted directed graph containing nodes and edges.
     * @return An array where the ith element represents the minimum cost to node i from the source node.
     * @throws Exception If an invalid edge or cost is found during processing.
     */
    public int[] BellmanFord(int source, WeightedDiGraph graph) throws Exception {
        int n = graph.numNodes(); // Number of nodes in the graph
        int[] distance = new int[n]; // Array to store minimum distances
        
        // Initialize all distances to infinity, except the source node which has distance 0
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        // Perform the relaxation step for all edges n-1 times
        for (int i = 1; i < n - 1; i++) {
            for (EdgeArray edge : graph.edges()) {
                int[] array = edge.getEdge();
                int u = array[0]; // Source node of the edge
                int v = array[1]; // Destination node of the edge
                relax(u, v, graph, distance); // Relax the edge
            }
        }
        return distance;
    }

    /**
     * Relaxes an edge by updating the distance to the destination node if a shorter path is found.
     * 
     * @param u The source node of the edge.
     * @param v The destination node of the edge.
     * @param graph The weighted directed graph containing the edge.
     * @param distance The array of minimum distances from the source node to all other nodes.
     * @throws Exception If an invalid cost is encountered while relaxing the edge.
     */
    public void relax(int u, int v, WeightedDiGraph graph, int[] distance) throws Exception {
        int edgeCost = graph.cost(u, v); // Get the cost of the edge from u to v
        int newDistance = adder(distance[u], edgeCost); // Calculate the new possible distance
        
        // Update the distance if a shorter path is found
        if (newDistance < distance[v]) {
            distance[v] = newDistance;
        }
    }

    /**
     * Safely adds two integers, considering Integer.MAX_VALUE as infinity.
     * If either of the integers is Integer.MAX_VALUE, the result is considered infinity.
     * 
     * @param a The first integer (could be a distance or cost).
     * @param b The second integer (could be a cost).
     * @return The sum of a and b, or Integer.MAX_VALUE if the result exceeds limits.
     */
    public int adder(int a, int b) {
        if (a == Integer.MAX_VALUE || b == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE; // Return infinity if either value is infinite
        }
        return a + b; // Otherwise, return the sum
    }
}