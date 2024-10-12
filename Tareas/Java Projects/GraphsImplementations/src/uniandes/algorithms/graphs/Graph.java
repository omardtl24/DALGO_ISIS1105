package uniandes.algorithms.graphs;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Graph {

    class IntArrayKey {
        private final int[] key;

        public IntArrayKey(int[] key) {
            this.key = key;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof IntArrayKey)) return false;
            IntArrayKey other = (IntArrayKey) obj;
            return Arrays.equals(this.key, other.key);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(key);
        }
    }

    private int nodes;
    private List<List<Integer>> edges; // Change to List<List<Integer>>
    private Map<IntArrayKey, Integer> costs = new HashMap<>();
    private List<int[]> edgesList = new ArrayList<>();
    private boolean directed;
    private boolean weighted;

    public Graph(List<int[]> input, int numNodes, boolean d, boolean w) throws Exception {
    	directed = d;
    	weighted = w;
        nodes = numNodes;
        edges = new ArrayList<>(nodes); // Initialize the list with capacity
        for (int i = 0; i < nodes; i++) {
            edges.add(new ArrayList<>()); // Initialize each inner list
        }

        for (int[] edge : input) {
            int fromNode = edge[0];
            int toNode = edge[1];
            int cost;
            if(weighted)  cost= edge[2];
            else cost = 0;
            
            if(cost(fromNode,toNode) < Integer.MAX_VALUE) throw new Exception("Repeated edge can't be stored");
            
            if(directed) {
            	costs.put(new IntArrayKey(new int[]{fromNode, toNode}), cost);
            	edges.get(fromNode).add(toNode); // Use get to add adjacent nodes
                edgesList.add(new int[]{fromNode, toNode});
            }else {
            	costs.put(new IntArrayKey(new int[]{fromNode, toNode}), cost);
            	costs.put(new IntArrayKey(new int[]{toNode, fromNode}), cost);
            	edges.get(fromNode).add(toNode); // Use get to add adjacent nodes
            	edges.get(toNode).add(fromNode);
                edgesList.add(new int[]{fromNode, toNode});
            }
            
        }
    }

    public int cost(int source, int destiny) {
        IntArrayKey edge = new IntArrayKey(new int[]{source, destiny});
        Integer value = costs.get(edge);
        return value == null || !weighted? Integer.MAX_VALUE : value;
    }

    public List<Integer> adj(int u) {
        return edges.get(u); // Use get to return adjacent nodes
    }
    
    public List<int[]> edges(){
    	return edgesList;
    }

    public int numNodes() {
        return nodes;
    }
    
    public boolean isDirected() {
    	return directed;
    }
}