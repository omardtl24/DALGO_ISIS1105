package uniandes.algorithms.graphs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a weighted undirected graph (WeightedGraph).
 * In this graph, each edge has an associated cost (or weight).
 * It extends the abstract Graph class.
 */
public class WeightedGraph extends Graph {
    // A map to store the cost (or weight) of each edge, with the edge represented by an EdgeArray.
    private Map<EdgeArray, Integer> costs = new HashMap<>();

    /**
     * Constructor for the WeightedGraph class. It initializes the graph with a list of weighted edges and the number of nodes.
     *
     * @param input List of edges, where each edge is represented as an int array of length 3: [source, destination, cost].
     * @param n     Number of nodes in the graph.
     * @throws Exception if the input edge array is invalid or does not meet the required format.
     */
    public WeightedGraph(List<int[]> input, int n) throws Exception {
        super(input, n);

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

            // Create an EdgeArray object representing the edge from source to destiny.
            EdgeArray edge = new EdgeArray(new int[] {source, destiny});

            // Store the cost of the edge in the costs map.
            costs.put(edge, cost);

            // Add the destination node to the adjacency list of the source node and vice versa.
            adjacency.get(source).add(destiny);
            adjacency.get(destiny).add(source);

            // Add the edge to the list of edges in the superclass.
            edges.add(edge);
        }
    }

    // Default constructor
    public WeightedGraph() {
        super();
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
        if (source == destiny) return 0;
        return value == null ? Integer.MAX_VALUE : value;
    }

    /**
     * Returns the cost of the specified edge.
     *
     * @param edge The edge for which the cost is requested.
     * @return The cost of the edge, or Integer.MAX_VALUE if the edge does not exist.
     * @throws Exception if the edge is invalid.
     */
    @Override
    public int cost(EdgeArray edge) throws Exception {
        int[] ar = edge.getEdge();
        
        // Retrieve the cost of the edge from the costs map.
        return cost(ar[0], ar[1]);
    }

    /**
     * Checks whether the specified edge exists in the graph.
     *
     * @param edge The edge to check.
     * @return true if the edge exists, false otherwise.
     */
    @Override
    public boolean containsEdge(EdgeArray edge) {
        return costs.containsKey(edge);
    }

    /**
     * Adds an edge to the graph with the specified cost if it does not already exist.
     *
     * @param edge The edge to add.
     * @param cost The cost associated with the edge.
     */
    public void addEdge(EdgeArray edge, int cost) {
        if (!containsEdge(edge)) {
            edges.add(edge);
            costs.put(edge, cost);
        }
    }

    /**
     * Adds an edge between two nodes with the specified cost.
     * This creates an undirected edge by adding the edge in both directions.
     *
     * @param u     The source node.
     * @param v     The destination node.
     * @param cost  The cost of the edge.
     * @throws Exception if an invalid edge is provided.
     */
    public void addEdge(int u, int v, int cost) throws Exception {
        EdgeArray edge = new EdgeArray(new int[] {u, v});
        addEdge(edge, cost);
        edge = new EdgeArray(new int[] {v, u});
        addEdge(edge, cost);
    }

    /**
     * Removes the specified edge from the graph if it exists.
     *
     * @param edge The edge to remove.
     */
    public void removeEdge(EdgeArray edge) {
        if (containsEdge(edge)) {
            int[] edgeArray = edge.getEdge();
            adjacency.get(edgeArray[0]).remove(edgeArray[1]);
            edges.remove(edge);
            costs.remove(edge);
        }
    }

    /**
     * Removes the edge between two nodes if it exists.
     * This removes the edge in both directions for the undirected graph.
     *
     * @param u The source node.
     * @param v The destination node.
     * @throws Exception if an invalid edge is provided.
     */
    public void removeEdge(int u, int v) throws Exception {
        EdgeArray edge = new EdgeArray(new int[] {u, v});
        removeEdge(edge);
        edge = new EdgeArray(new int[] {v, u});
        removeEdge(edge);
    }
}
