package uniandes.algorithms.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KruskalMST implements MinimumSpanningTree {

    /**
     * Computes the Minimum Spanning Tree (MST) of a weighted graph using Kruskal's algorithm.
     * 
     * @param graph The weighted graph for which the MST is to be computed.
     *              It must not be null and should have at least one edge.
     * @return A List of edges (each represented as an integer array) that make up the MST.
     *         If the graph is empty or invalid, an empty list is returned.
     */
    public List<int[]> getMST(WeightedGraph graph) {
        List<int[]> answer = new ArrayList<>(); // List to store the edges of the MST
        Partition set = new Partition(graph.numNodes()); // Initialize partition for union-find

        List<EdgeArray> edges = graph.edges(); // Retrieve all edges from the graph
        // Sort edges based on their weights (cost)
        Collections.sort(edges, new Comparator<EdgeArray>() {
            @Override
            public int compare(EdgeArray a1, EdgeArray a2) {
                try {
                    return Integer.compare(graph.cost(a1), graph.cost(a2)); // Compare edge costs
                } catch (Exception e) {
                    return Integer.MAX_VALUE; // In case of an exception, return a high value
                }
            }
        });

        // Process each edge in sorted order
        for (EdgeArray edge : edges) {
            int[] u_v = edge.getEdge(); // Get the nodes of the edge
            // Check if the nodes are in different subsets
            if (!set.sameSubsets(u_v[0], u_v[1])) {
                answer.add(u_v); // Add edge to MST
                set.union(u_v[0], u_v[1]); // Union the subsets
            }
        }

        return answer; // Return the edges of the Minimum Spanning Tree
    }
}
