package uniandes.algorithms.graphs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class EdmondsKarpMaxFlow implements MaximumFlow {
	
	private Map<EdgeArray, Integer> flows;

    public MaximumFlowAnswer getMaximumFlow(WeightedDiGraph graph, int source, int sink) throws Exception {
        int n = graph.numNodes();
        flows = new HashMap<EdgeArray, Integer>();
        int maxFlow = 0;
        //Initialize residualGraph
        for(EdgeArray edge: graph.edges()) {
        	flows.put(edge, 0);
        }

        // Find augmenting paths using BFS
        int[] path = BFSPath(graph, source, sink);

        while (path[sink] != -1) { // Check if there's a path from source to sink
            int u, v, i = sink;
            int cfp = Integer.MAX_VALUE;
            // Find the minimum residual capacity (cfp) along the path
            while (i != source) {
                u = path[i];
                v = i;
                cfp = Math.min(cfp, residualCapacity(u, v, graph));
                i = u;
            }
            
            maxFlow += cfp;

            // Augment flow along the path
            i = sink;
            while (i != source) {
                u = path[i];
                v = i;
                if(graph.containsEdge(u,v)) {
                	updateFlow(u,v,getFlow(u,v)+cfp);
                }else {
                	updateFlow(v,u,getFlow(v,u)-cfp);
                }
                i = u;
            }

            // Re-run BFS to find next augmenting path
            path = BFSPath(graph, source, sink);
        }

        return new MaximumFlowAnswer(flows, maxFlow);
    }
    
    public void updateFlow(int u, int v, int newValue) throws Exception {
    	EdgeArray edge = new EdgeArray (new int [] {u,v});
    	flows.put(edge, newValue);
    }
    
    public int residualCapacity(int u, int v, WeightedDiGraph graph) throws Exception {
    	
    	if (graph.containsEdge(u,v)) return graph.cost(u,v)-getFlow(u,v);
    	if (graph.containsEdge(v,u)) return getFlow(v,u);
    	return 0;
    }
    
    public int getFlow(int u, int v) throws Exception {
    	Integer val = flows.get(new EdgeArray (new int [] {u,v}));
    	if(val== null) return 0;
    	return val;
    }

    // BFS to find augmenting path, returns parents array
    public int[] BFSPath(WeightedDiGraph graph, int source, int sink) throws Exception {
        int n = graph.numNodes();
        int[] parents = new int[n];
        int[] colors = new int[n];  // 0 = unvisited, 1 = visited, 2 = processed

        for (int i = 0; i < n; i++) {
            parents[i] = -1;
        }

        Queue<Integer> q = new LinkedList<Integer>();
        q.add(source);
        colors[source] = 1;

        while (!q.isEmpty()) {
            int u = q.poll();
            
            for(int v=0; v<n; v++) {
            	if(colors[v]==0 && residualCapacity(u,v,graph)>0) {
            		colors[v] = 1;
            		parents[v] = u;
            		q.add(v);
            	}
            }
            
            colors[u] = 2;  // Mark node as fully processed
        }

        return parents;
    }
    
}
