package uniandes.algorithms.graphs;

import java.util.List;
import java.util.ArrayList;

/**
 * Abstract class representing a graph structure. It is intended to be subclassed
 * for specific types of graphs (e.g., directed, undirected).
 */
public abstract class Graph {

    // Number of nodes in the graph
    protected int numNodes;

    // Adjacency list representing the graph, where each node has a list of adjacent nodes.
    protected List<List<Integer>> adjacency;

    // List of edges in the graph, each represented as a pair of integers (two nodes).
    protected List<EdgeArray> edges;

    /**
     * Constructor for the Graph class. Checks basic elements for graph.
     * 
     * @param input List of edges, where each edge is represented as an array of two integers (nodes).
     * @param n     Number of nodes in the graph.
     * @throws Exception if the edge list is empty or the number of nodes is zero.
     */
    public Graph(List<int[]> input, int n) throws Exception {
        if (input.isEmpty() || n == 0) throw new Exception("Invalid set of edges");
        numNodes = n;
        adjacency = new ArrayList<>(numNodes); // Initialize the list with capacity
        edges = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            adjacency.add(new ArrayList<Integer>()); // Initialize each inner list
        }
    }
    
    /**
     * Default constructor for the Graph class. Initializes an empty graph.
     */
    public Graph() {
        numNodes = 0;
        adjacency = new ArrayList<>(numNodes); // Initialize the list with capacity
        edges = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            adjacency.add(new ArrayList<Integer>()); // Initialize each inner list
        }
    }

    /**
     * Returns the list of adjacent nodes for a given node.
     * 
     * @param u The node for which adjacent nodes are requested.
     * @return List of adjacent nodes.
     */
    public List<Integer> adj(int u) {
        return adjacency.get(u);
    }

    /**
     * Returns the list of edges in the graph.
     * 
     * @return List of edges, where each edge is an array of two integers (nodes).
     */
    public List<EdgeArray> edges() {
        return edges;
    }

    /**
     * Returns the total number of nodes in the graph.
     * 
     * @return Number of nodes in the graph.
     */
    public int numNodes() {
        return numNodes;
    }
    
    /**
     * Abstract method to calculate the cost between two nodes.
     * 
     * @param source The starting node.
     * @param destiny The destination node.
     * @return The cost associated with traveling from the source to the destiny node.
     * @throws Exception if the source or destiny node is invalid.
     */
    public abstract int cost(int source, int destiny) throws Exception;
    
    /**
     * Abstract method to calculate the cost associated with a specific edge.
     * 
     * @param edge The edge for which the cost is to be calculated.
     * @return The cost associated with the specified edge.
     * @throws Exception if the edge is invalid.
     */
    public abstract int cost(EdgeArray edge) throws Exception;
    
    /**
     * Abstract method to check if a specific edge exists in the graph.
     * 
     * @param edge The edge to check for existence.
     * @return True if the edge exists in the graph, false otherwise.
     */
    public abstract boolean containsEdge(EdgeArray edge);
}
