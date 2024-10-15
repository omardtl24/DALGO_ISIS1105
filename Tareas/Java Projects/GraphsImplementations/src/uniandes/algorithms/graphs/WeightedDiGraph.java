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
    
    // Default constructor for the WeightedDiGraph class.
    public WeightedDiGraph(){
        super(); // Call the constructor of the Graph superclass.
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

        // If the source and destination are the same, the cost is zero.
        if(source==destiny) return 0;
        
        // Return the cost if it exists; otherwise, return Integer.MAX_VALUE to indicate no edge.
        return value == null ? Integer.MAX_VALUE : value;
    }
    
    @Override
    public int cost(EdgeArray edge) throws Exception {
        int[] ar = edge.getEdge(); // Get the edge array from the EdgeArray object.
        
        // Retrieve the cost of the edge from the costs map.
        return cost(ar[0], ar[1]); // Call the cost method using the source and destination nodes.
    }
    
    @Override
    public boolean containsEdge(EdgeArray edge) {
        // Check if the edge exists in the costs map.
        return costs.containsKey(edge);
    }
    
    /**
     * Removes an edge from the graph.
     * 
     * @param edge The edge to be removed.
     */
    public void removeEdge(EdgeArray edge) {
        if(containsEdge(edge)) { // Check if the edge exists.
            int[] edgeArray = edge.getEdge(); // Get the edge array.
            adjacency.get(edgeArray[0]).remove(edgeArray[1]); // Remove the destination from the adjacency list.
            edges.remove(edge); // Remove the edge from the edges list.
            costs.remove(edge); // Remove the edge from the costs map.
        }
    }
    
    /**
     * Removes an edge between two specified nodes.
     * 
     * @param u The source node.
     * @param v The destination node.
     * @throws Exception if the edge is invalid.
     */
    public void removeEdge(int u, int v) throws Exception {
        EdgeArray edge = new EdgeArray(new int[] {u, v}); // Create an EdgeArray for the specified edge.
        removeEdge(edge); // Call the removeEdge method with the created edge.
    }
    
    /**
     * Adds an edge to the graph with a specified cost.
     * 
     * @param edge The edge to be added.
     * @param cost The cost associated with the edge.
     */
    public void addEdge(EdgeArray edge, int cost) {
        if(!containsEdge(edge)) { // Check if the edge does not already exist.
            int[] edgeArray = edge.getEdge(); // Get the edge array.
            adjacency.get(edgeArray[0]).add(edgeArray[1]); // Add the destination to the adjacency list.
            edges.add(edge); // Add the edge to the edges list.
        }
        costs.put(edge, cost); // Store the cost of the edge in the costs map.
    }

    /**
     * Adds an edge between two specified nodes with an associated cost.
     * 
     * @param u The source node.
     * @param v The destination node.
     * @param cost The cost associated with the edge.
     * @throws Exception if the edge is invalid.
     */
    public void addEdge(int u, int v, int cost) throws Exception {
        EdgeArray edge = new EdgeArray(new int[] {u, v}); // Create an EdgeArray for the specified edge.
        addEdge(edge, cost); // Call the addEdge method with the created edge and cost.
    }

    /**
     * Checks if an edge exists between two specified nodes.
     * 
     * @param u The source node.
     * @param v The destination node.
     * @return true if the edge exists; false otherwise.
     * @throws Exception if the edge is invalid.
     */
    public boolean containsEdge(int u, int v) throws Exception {
        EdgeArray edge = new EdgeArray(new int[] {u, v}); // Create an EdgeArray for the specified edge.
        return containsEdge(edge); // Call the containsEdge method with the created edge.
    }
}
