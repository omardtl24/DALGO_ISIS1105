package uniandes.algorithms.graphs;

import java.util.List;

public class UnweightedGraph extends Graph {

    /**
     * Constructs an unweighted graph from a list of input edges.
     * Each edge is represented as an array of two integers, indicating the source and destination nodes.
     * 
     * @param input A list of edges, where each edge is an array of two integers [source, destination].
     * @param n The number of nodes in the graph.
     * @throws Exception if the input edges are invalid (not containing exactly two elements).
     */
    public UnweightedGraph(List<int[]> input, int n) throws Exception {
        super(input, n); // Call the constructor of the Graph superclass.

        int source;   // The source node of an edge.
        int destiny;  // The destination node of an edge.

        // Iterate over the input edges to populate the graph and cost map.
        for (int[] edgeInput : input) {
            // Validate that each edge array has exactly two elements (source, destination).
            if (edgeInput.length != 2) throw new Exception("Invalid input edge");

            source = edgeInput[0];  // Extract the source node.
            destiny = edgeInput[1]; // Extract the destination node.

            // Create an EdgeArray object representing the edge from source to destination.
            EdgeArray edge = new EdgeArray(new int[] {source, destiny});

            // Add the destination node to the adjacency list of the source node.
            adjacency.get(source).add(destiny); // 'adjacency' refers to the adjacency list from the superclass.
            adjacency.get(destiny).add(source); // 'adjacency' refers to the adjacency list from the superclass.

            // Add the edge to the list of edges in the superclass.
            edges.add(edge);
        }
    }

    /**
     * Default constructor for an unweighted graph.
     * Initializes an empty graph.
     */
    public UnweightedGraph() {
        super(); // Call the constructor of the Graph superclass.
    }

    /**
     * Returns the cost of traversing from the source node to the destiny node.
     * Since this is an unweighted graph, the cost is always zero.
     * 
     * @param source The source node.
     * @param destiny The destination node.
     * @return The cost of the edge, which is always zero in an unweighted graph.
     * @throws Exception Not thrown in this implementation.
     */
    @Override
    public int cost(int source, int destiny) throws Exception {
        return 0; // Cost is always zero in an unweighted graph.
    }

    /**
     * Returns the cost of traversing the given edge.
     * Since this is an unweighted graph, the cost is always zero.
     * 
     * @param edge The edge for which to get the cost.
     * @return The cost of the edge, which is always zero in an unweighted graph.
     * @throws Exception Not thrown in this implementation.
     */
    @Override
    public int cost(EdgeArray edge) throws Exception {
        return 0; // Cost is always zero in an unweighted graph.
    }

    /**
     * Checks if the graph contains the specified edge.
     * 
     * @param edge The edge to check for presence in the graph.
     * @return True if the edge exists in the graph, false otherwise.
     */
    @Override
    public boolean containsEdge(EdgeArray edge) {
        return edges.contains(edge); // Check if the edge is in the list of edges.
    }

    /**
     * Adds a new edge to the graph if it does not already exist.
     * 
     * @param edge The edge to be added.
     */
    public void addEdge(EdgeArray edge) {
        if (!containsEdge(edge)) {
            int[] edgeArray = edge.getEdge();
            adjacency.get(edgeArray[0]).add(edgeArray[1]); // Add to adjacency list.
            edges.add(edge); // Add edge to the list of edges.
        }
    }

    /**
     * Adds an edge defined by two nodes to the graph.
     * 
     * @param u The source node.
     * @param v The destination node.
     * @throws Exception if there is an error while adding the edge.
     */
    public void addEdge(int u, int v) throws Exception {
        EdgeArray edge = new EdgeArray(new int[]{u, v}); // Create an edge from u to v.
        addEdge(edge); // Add the edge to the graph.
    }

    /**
     * Removes an edge from the graph if it exists.
     * 
     * @param edge The edge to be removed.
     */
    public void removeEdge(EdgeArray edge) {
        if (containsEdge(edge)) {
            int[] edgeArray = edge.getEdge();
            adjacency.get(edgeArray[0]).remove(edgeArray[1]); // Remove from adjacency list.
            edges.remove(edge); // Remove edge from the list of edges.
        }
    }

    /**
     * Removes an edge defined by two nodes from the graph.
     * 
     * @param u The source node.
     * @param v The destination node.
     * @throws Exception if there is an error while removing the edge.
     */
    public void removeEdge(int u, int v) throws Exception {
        EdgeArray edge = new EdgeArray(new int[]{u, v}); // Create an edge from u to v.
        removeEdge(edge); // Remove the edge from the graph.
    }
}
