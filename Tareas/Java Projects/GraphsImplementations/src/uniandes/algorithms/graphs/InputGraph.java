package uniandes.algorithms.graphs;

import java.util.List;

public class InputGraph {
    private List<int[]> input;       // List of edges represented as integer arrays
    private int nodes;               // Number of nodes in the graph
    private String[] namesMapper;    // Array of names for nodes

    /**
     * Constructor for InputGraph.
     * 
     * @param i List of edges represented as integer arrays, where each edge is defined by 
     *          an array of integers (e.g., {fromNode, toNode, cost}).
     * @param n Total number of nodes in the graph.
     * @param names Array of names corresponding to each node in the graph.
     */
    public InputGraph(List<int[]> i, int n, String[] names) {
        input = i;               // Initialize the input edges
        nodes = n;              // Initialize the number of nodes
        namesMapper = names;    // Initialize the names of the nodes
    }

    /**
     * Gets the number of nodes in the graph.
     * 
     * @return The total number of nodes in the graph.
     */
    public int getNodes() {
        return nodes;           // Return the number of nodes
    }

    /**
     * Gets the list of input edges.
     * 
     * @return A List of edges represented as integer arrays.
     */
    public List<int[]> getInput() {
        return input;          // Return the list of edges
    }

    /**
     * Gets the array of names corresponding to each node.
     * 
     * @return An array of strings representing the names of the nodes.
     */
    public String[] getNames() {
        return namesMapper;    // Return the array of node names
    }
}
