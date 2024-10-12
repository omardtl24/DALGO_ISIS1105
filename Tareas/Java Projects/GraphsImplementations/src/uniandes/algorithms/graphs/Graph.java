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

    public Graph(List<int[]> input, int numNodes) {
        nodes = numNodes;
        edges = new ArrayList<>(nodes); // Initialize the list with capacity
        for (int i = 0; i < nodes; i++) {
            edges.add(new ArrayList<>()); // Initialize each inner list
        }

        for (int[] edge : input) {
            int fromNode = edge[0];
            int toNode = edge[1];
            int cost = edge[2];

            costs.put(new IntArrayKey(new int[]{fromNode, toNode}), cost);
            edges.get(fromNode).add(toNode); // Use get to add adjacent nodes
            edgesList.add(new int[]{fromNode, toNode});
        }
    }

    public int cost(int source, int destiny) {
        IntArrayKey edge = new IntArrayKey(new int[]{source, destiny});
        Integer value = costs.get(edge);
        return value == null ? Integer.MAX_VALUE : value;
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
}