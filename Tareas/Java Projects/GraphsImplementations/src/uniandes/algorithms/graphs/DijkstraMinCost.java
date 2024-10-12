package uniandes.algorithms.graphs;
import java.util.Arrays;
import java.util.PriorityQueue;

public class DijkstraMinCost implements MinimumCost{
	/**
	 * Calculates the minimum cost path matrix from every pair of nodes
     * in the desired graph
	 * @param graph Representation of a directed graph
	 * @return int [][] minimum cost for each pair of nodes 
	 * @throws Exception 
	 */
    public int [][] getMinCostMatrix(WeightedDiGraph graph) throws Exception{
    	int n = graph.numNodes();
        int[][] m = new int[n][n];
        for(int i=0; i<n;i++) {
        	m[i] = Dijkstra(i,graph);
        }
        return m;
    }
    /**
     * 
     * @param source Node from where distances are going to be calculated
     * @param graph Representation of graph
     * @return Minimum cost from source node to all others
     * @throws Exception 
     */
    public int[] Dijkstra(int source, WeightedDiGraph graph ) throws Exception{
    	int n = graph.numNodes();
    	int[] distance = new int[n];
    	boolean[] visited = new boolean[n];
    	
    	Arrays.fill(distance, Integer.MAX_VALUE);
    	distance[source] = 0;
    	
    	PriorityQueue<int[]> pQ = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
    	pQ.add(new int[] {0, source});
    	
    	while(!pQ.isEmpty()) {
    		int [] current = pQ.poll();
    		int u = current[1];
    		
    		if(!visited[u]) {
    			visited[u] = true;
    			for(int v: graph.adj(u)) {
    				relax(u,  v, graph,  distance, pQ);
    			}
    		}
    	}
    	
        return distance;
    }
    
    /**
     * Updates distance maintaining minimum value property
     * 
     * @param u Node source to take into account in relaxation process
     * @param v Node destiny to take into account in relaxation process
     * @param graph Representation of graph weights
     * @param distance Minimum relative distances from source to any other nodes
     * @param pQ Priority queue to store the new distances
     * @throws Exception 
     */
    public void relax(int u, int v, WeightedDiGraph graph, int[] distance, PriorityQueue<int[]> pQ) throws Exception {
        int edgeCost = graph.cost(u, v);
        int newDistance = adder(distance[u], edgeCost);
        
        if (newDistance < distance[v]) {
            distance[v] = newDistance;
            pQ.add(new int[] {newDistance, v});
        }
    }

    /**
     * Adds two integers, accounting for Integer.MAX_VALUE.
     * If either integer is Integer.MAX_VALUE, the result is Integer.MAX_VALUE.
     *
     * @param a First integer to add.
     * @param b Second integer to add.
     * @return The sum of a and b or Integer.MAX_VALUE if either is max.
     */
    public int adder(int a, int b) {
    	if(a==Integer.MAX_VALUE || b==Integer.MAX_VALUE) return Integer.MAX_VALUE;
    	return a+b;
    }
}
