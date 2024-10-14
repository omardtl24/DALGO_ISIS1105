package uniandes.algorithms.graphs;

import java.util.List;

public class UnweightedGraph extends Graph{
	
	public UnweightedGraph(List<int[]> input, int n) throws Exception {
        super(input, n); // Call the constructor of the Graph superclass.
        
        int source;   // The source node of an edge.
        int destiny;  // The destination node of an edge.
        
        // Iterate over the input edges to populate the graph and cost map.
        for (int[] edgeInput : input) {
            // Validate that each edge array has exactly three elements (source, destination, and cost).
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

	@Override
	public int cost(int source, int destiny) throws Exception {
		return 0;
	}

	@Override
	public int cost(EdgeArray edge) throws Exception {
		return 0;
	}
}