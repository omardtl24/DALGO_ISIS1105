package uniandes.algorithms.graphs;

import java.util.Arrays;
import java.util.PriorityQueue;

public class DijkstraMinCost implements MinimumCost {

    /**
     * Calculates the minimum cost path matrix from every pair of nodes in the graph.
     * For each node, it uses Dijkstra's algorithm to compute the shortest paths.
     * 
     * @param graph The weighted directed graph on which the minimum cost matrix is calculated.
     * @return int[][] A matrix where the value at [i][j] represents the minimum cost to reach node j from node i.
     * @throws Exception If any error occurs during graph processing or edge relaxation.
     */
    public int[][] getMinCostMatrix(WeightedDiGraph graph) throws Exception {
        int n = graph.numNodes(); // Number of nodes in the graph
        int[][] m = new int[n][n]; // Matrix to store minimum costs
        
        // Calculate minimum costs from each node to every other node
        for (int i = 0; i < n; i++) {
            m[i] = Dijkstra(i, graph);
        }
        return m;
    }

    /**
     * Implements Dijkstra's algorithm to calculate the shortest paths from the source node.
     * 
     * @param source The node from which distances will be calculated.
     * @param graph The weighted directed graph on which the algorithm is executed.
     * @return int[] An array where the value at index i is the minimum cost to reach node i from the source node.
     * @throws Exception If any error occurs during graph processing or edge relaxation.
     */
    public int[] Dijkstra(int source, WeightedDiGraph graph) throws Exception {
        int n = graph.numNodes(); // Number of nodes in the graph
        int[] distance = new int[n]; // Array to store minimum distances from source
        boolean[] visited = new boolean[n]; // Array to track visited nodes
        
        Arrays.fill(distance, Integer.MAX_VALUE); // Initialize all distances to infinity
        distance[source] = 0; // Distance to the source node is 0
        
        // Priority queue to store nodes along with their current minimum distances
        PriorityQueue<int[]> pQ = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        pQ.add(new int[] {0, source}); // Add the source node with distance 0
        
        // Main Dijkstra's algorithm loop
        while (!pQ.isEmpty()) {
            int[] current = pQ.poll(); // Get the node with the smallest distance
            int u = current[1];
            
            if (!visited[u]) {
                visited[u] = true; // Mark the node as visited
                
                // Relax edges for all adjacent nodes
                for (int v : graph.adj(u)) {
                    relax(u, v, graph, distance, pQ);
                }
            }
        }
        return distance; // Return the array of minimum distances
    }

    /**
     * Performs the relaxation of an edge during the Dijkstra algorithm, updating distances and the priority queue.
     * 
     * @param u The source node of the edge.
     * @param v The destination node of the edge.
     * @param graph The weighted directed graph.
     * @param distance An array containing the current minimum distances from the source node.
     * @param pQ The priority queue used to store nodes and their updated distances.
     * @throws Exception If an error occurs when retrieving edge costs from the graph.
     */
    public void relax(int u, int v, WeightedDiGraph graph, int[] distance, PriorityQueue<int[]> pQ) throws Exception {
        int edgeCost = graph.cost(u, v); // Get the cost of the edge from u to v
        int newDistance = adder(distance[u], edgeCost); // Calculate the new potential distance
        
        // If the new distance is shorter, update it and add the node to the priority queue
        if (newDistance < distance[v]) {
            distance[v] = newDistance;
            pQ.add(new int[] {newDistance, v});
        }
    }

    /**
     * Adds two integers, taking care of cases where one or both integers are Integer.MAX_VALUE.
     * If either integer is Integer.MAX_VALUE, the result will also be Integer.MAX_VALUE.
     * 
     * @param a The first integer to add.
     * @param b The second integer to add.
     * @return The sum of a and b, or Integer.MAX_VALUE if either is Integer.MAX_VALUE.
     */
    public int adder(int a, int b) {
        if (a == Integer.MAX_VALUE || b == Integer.MAX_VALUE) return Integer.MAX_VALUE;
        return a + b;
    }
}
