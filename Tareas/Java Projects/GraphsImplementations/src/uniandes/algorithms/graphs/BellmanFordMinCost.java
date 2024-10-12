package uniandes.algorithms.graphs;

import java.util.Arrays;

public class BellmanFordMinCost implements MinimumCost{
    public int [][] getMinCostMatrix(Graph graph){
    	int n = graph.numNodes();
        int[][] m = new int[n][n];
        for(int i=0; i<n;i++) {
        	m[i] = BellmanFord(i,graph);
        }
        return m;
    }

    public int[] BellmanFord(int source, Graph graph ){
    	int n = graph.numNodes();
    	int[] distance = new int[n];
    	boolean[] visited = new boolean[n];
    	
    	Arrays.fill(distance, Integer.MAX_VALUE);
    	distance[source] = 0;
    	
    	for(int i=1;i<n-1;i++) {
    		for(int[] edge: graph.edges()) {
    			int u = edge[0];
    			int v = edge[1];
    			relax(u,v,graph,distance);
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
     */
    public void relax(int u, int v, Graph graph, int[] distance) {
        int edgeCost = graph.cost(u, v);
        int newDistance = adder(distance[u], edgeCost);
        
        if (newDistance < distance[v]) {
            distance[v] = newDistance;
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