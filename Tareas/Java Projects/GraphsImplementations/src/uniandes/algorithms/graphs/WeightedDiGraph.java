package uniandes.algorithms.graphs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a weighted directed graph (WeightedDiGraph).
 * In this graph, each edge has an associated cost (or weight).
 * It extends the abstract Graph class.
 */
public class WeightedDiGraph extends Graph {

    // A map to store the cost (or weight) of each edge, with the edge represented by an EdgeArray.
    private Map<EdgeArray, Integer> costs = new HashMap<>();

    /**
     * Constructor for the WeightedDiGraph class. It initializes the graph with a list of weighted edges and the number of nodes.
     * 
     * @param input List of edges, where each edge is represented as an int array of length 3: [source, destination, cost].
     * @param n     Number of nodes in the graph.
     * @throws Exception if the input edge array is invalid or does not meet the required format.
     */
    public WeightedDiGraph(List<int[]> input, int n) throws Exception {
        super(input, n); // Call the constructor of the Graph superclass.
        
        int source;   // The source node of an edge.
        int destiny;  // The destination node of an edge.
        int cost;     // The weight (cost) of the edge.
        
        // Iterate over the input edges to populate the graph and cost map.
        for (int[] edgeInput : input) {
            // Validate that each edge array has exactly three elements (source, destination, and cost).
            if (edgeInput.length != 3) throw new Exception("Invalid input edge");
            
            source = edgeInput[0];  // Extract the source node.
            destiny = edgeInput[1]; // Extract the destination node.
            cost = edgeInput[2];    // Extract the cost of the edge.

            // Create an EdgeArray object representing the edge from source to destination.
            EdgeArray edge = new EdgeArray(new int[] {source, destiny});
            
            // Store the cost of the edge in the costs map.
            costs.put(edge, cost);

            // Add the destination node to the adjacency list of the source node.
            adjacency.get(source).add(destiny); // 'adjacency' refers to the adjacency list from the superclass.

            // Add the edge to the list of edges in the superclass.
            edges.add(edge);
        }
    }

    /**
     * Returns the cost of traveling from a source node to a destination node.
     * If the edge does not exist, it returns Integer.MAX_VALUE to indicate that the edge is not reachable.
     * 
     * @param source  The source node.
     * @param destiny The destination node.
     * @return The cost of the edge from source to destination, or Integer.MAX_VALUE if the edge does not exist.
     * @throws Exception if an invalid edge is provided.
     */
    @Override
    public int cost(int source, int destiny) throws Exception {
        // Create an EdgeArray object representing the edge from source to destination.
        EdgeArray edge = new EdgeArray(new int[] {source, destiny});

        // Retrieve the cost of the edge from the costs map.
        Integer value = costs.get(edge);

        // If the edge does not exist, return Integer.MAX_VALUE; otherwise, return the cost.
        if(source==destiny) return 0;
        return value == null ? Integer.MAX_VALUE : value;
    }
}