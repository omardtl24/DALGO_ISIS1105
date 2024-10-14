package uniandes.algorithms.graphs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightedGraph extends Graph{
	private Map<EdgeArray, Integer> costs = new HashMap<>();
	
	public WeightedGraph(List<int[]> input, int n) throws Exception {
		super(input, n);
        
        int source;   
        int destiny; 
        int cost; 
        
        
        for (int[] edgeInput : input) {
            
            if (edgeInput.length != 3) throw new Exception("Invalid input edge");
            
            source = edgeInput[0]; 
            destiny = edgeInput[1]; 
            cost = edgeInput[2]; 

           
            EdgeArray edge = new EdgeArray(new int[] {source, destiny});
            
            
            costs.put(edge, cost);

           
            adjacency.get(source).add(destiny);
            adjacency.get(destiny).add(source);

         
            edges.add(edge);
        }
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
        if(source==destiny) return 0;
        return value == null ? Integer.MAX_VALUE : value;
    }
    
    @Override
    public int cost(EdgeArray edge) throws Exception {
    	int[] ar = edge.getEdge();
    	
    	// Retrieve the cost of the edge from the costs map.
        return cost(ar[0],ar[1]);
    }
}
