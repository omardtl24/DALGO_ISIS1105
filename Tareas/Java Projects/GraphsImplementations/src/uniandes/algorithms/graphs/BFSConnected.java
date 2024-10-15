package uniandes.algorithms.graphs;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFSConnected implements ConnectedComponents {

    /**
     * Finds all the connected components of an unweighted graph using BFS.
     * 
     * @param graph The unweighted graph where nodes and edges are defined.
     * @return A list of connected components, where each component is represented as a list of node indices.
     */
    public List<List<Integer>> getComponents(UnweightedGraph graph) {
        
        List<List<Integer>> components = new ArrayList<>(); // List to store all connected components
        int n = graph.numNodes(); // Number of nodes in the graph
        int[] states = new int[n]; // Array to track the state of each node (0: unvisited, 1: in-progress, 2: visited)
        
        // Traverse each node to find new components
        for (int i = 0; i < n; i++) {
            if (states[i] == 0) { // If the node is unvisited
                List<Integer> component = BFSComponent(i, graph, states); // Perform BFS starting from node i
                components.add(component); // Add the found component to the list
            }
        }
        
        return components;
    }

    /**
     * Performs BFS (Breadth-First Search) from the source node to find all nodes in the same connected component.
     * 
     * @param source The starting node for the BFS search.
     * @param graph The unweighted graph containing nodes and edges.
     * @param states An array that tracks the state of each node during BFS (0: unvisited, 1: in-progress, 2: visited).
     * @return A list of nodes that are part of the connected component starting from the source node.
     */
    public List<Integer> BFSComponent(int source, UnweightedGraph graph, int[] states) {
        
        List<Integer> group = new ArrayList<>(); // List to store nodes in the current connected component
        
        states[source] = 1; // Mark the source node as in-progress
        
        Queue<Integer> q = new LinkedList<>(); // Queue for BFS traversal
        
        q.add(source); // Enqueue the source node
        while (!q.isEmpty()) {
            int u = q.poll(); // Dequeue a node
            for (int v : graph.adj(u)) { // Iterate over all adjacent nodes of u
                if (states[v] == 0) { // If the adjacent node is unvisited
                    states[v] = 1; // Mark it as in-progress
                    q.add(v); // Enqueue the adjacent node
                }
            }
            states[u] = 2; // Mark the current node as visited
            group.add(u); // Add the current node to the component
        }
        return group; // Return the connected component
    }
}
