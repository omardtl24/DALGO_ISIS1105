package uniandes.algorithms.graphs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class EdmondsKarpMaxFlow implements MaximumFlow {
    
    private Map<EdgeArray, Integer> flows; // Stores the flow for each edge

    /**
     * Calculates the maximum flow from source to sink in the given graph using the Edmonds-Karp algorithm.
     * 
     * @param graph The directed graph with weights representing capacities.
     * @param source The source node from where flow originates.
     * @param sink The sink node where flow is collected.
     * @return MaximumFlowAnswer containing the flow details and total maximum flow value.
     * @throws Exception If any error occurs during flow calculation.
     */
    public MaximumFlowAnswer getMaximumFlow(WeightedDiGraph graph, int source, int sink) throws Exception {
        int n = graph.numNodes();
        flows = new HashMap<EdgeArray, Integer>();
        int maxFlow = 0;

        // Initialize flow values for all edges to 0
        for (EdgeArray edge : graph.edges()) {
            flows.put(edge, 0);
        }

        // Find augmenting paths using BFS
        int[] path = BFSPath(graph, source, sink);

        // While there's an augmenting path from source to sink
        while (path[sink] != -1) {
            int u, v, i = sink;
            int cfp = Integer.MAX_VALUE; // Current flow path capacity

            // Find the minimum residual capacity (cfp) along the path
            while (i != source) {
                u = path[i];
                v = i;
                cfp = Math.min(cfp, residualCapacity(u, v, graph));
                i = u;
            }
            
            maxFlow += cfp; // Increase total max flow

            // Augment flow along the path
            i = sink;
            while (i != source) {
                u = path[i];
                v = i;
                if (graph.containsEdge(u, v)) {
                    updateFlow(u, v, getFlow(u, v) + cfp); // Update flow for forward edge
                } else {
                    updateFlow(v, u, getFlow(v, u) - cfp); // Update flow for backward edge
                }
                i = u; // Move to the parent node
            }

            // Re-run BFS to find the next augmenting path
            path = BFSPath(graph, source, sink);
        }

        return new MaximumFlowAnswer(flows, maxFlow); // Return the result
    }
    
    /**
     * Updates the flow value for a given edge.
     * 
     * @param u Source node of the edge.
     * @param v Destination node of the edge.
     * @param newValue New flow value for the edge.
     * @throws Exception If an error occurs during update.
     */
    public void updateFlow(int u, int v, int newValue) throws Exception {
        EdgeArray edge = new EdgeArray(new int[]{u, v});
        flows.put(edge, newValue);
    }
    
    /**
     * Calculates the residual capacity of an edge.
     * 
     * @param u Source node of the edge.
     * @param v Destination node of the edge.
     * @param graph The directed graph.
     * @return The residual capacity of the edge.
     * @throws Exception If an error occurs during calculation.
     */
    public int residualCapacity(int u, int v, WeightedDiGraph graph) throws Exception {
        if (graph.containsEdge(u, v)) return graph.cost(u, v) - getFlow(u, v);
        if (graph.containsEdge(v, u)) return getFlow(v, u);
        return 0; // No capacity if no edge exists
    }
    
    /**
     * Gets the current flow value for an edge.
     * 
     * @param u Source node of the edge.
     * @param v Destination node of the edge.
     * @return Current flow value for the edge.
     * @throws Exception If an error occurs during retrieval.
     */
    public int getFlow(int u, int v) throws Exception {
        Integer val = flows.get(new EdgeArray(new int[]{u, v}));
        return val == null ? 0 : val; // Return flow value or 0 if not found
    }

    /**
     * Performs BFS to find an augmenting path from source to sink.
     * 
     * @param graph The directed graph.
     * @param source The source node.
     * @param sink The sink node.
     * @return An array representing the parent of each node in the path.
     * @throws Exception If an error occurs during the search.
     */
    public int[] BFSPath(WeightedDiGraph graph, int source, int sink) throws Exception {
        int n = graph.numNodes();
        int[] parents = new int[n]; // Parent array to reconstruct the path
        int[] colors = new int[n];  // 0 = unvisited, 1 = visited, 2 = processed

        // Initialize parents and colors
        for (int i = 0; i < n; i++) {
            parents[i] = -1; // No parents initially
        }

        Queue<Integer> q = new LinkedList<Integer>();
        q.add(source);
        colors[source] = 1; // Mark source as visited

        // BFS loop
        while (!q.isEmpty()) {
            int u = q.poll();
            
            // Explore neighbors
            for (int v = 0; v < n; v++) {
                if (colors[v] == 0 && residualCapacity(u, v, graph) > 0) {
                    colors[v] = 1; // Mark as visited
                    parents[v] = u; // Set parent
                    q.add(v); // Add to the queue
                }
            }
            
            colors[u] = 2; // Mark node as fully processed
        }

        return parents; // Return parents array to reconstruct path
    }
}
