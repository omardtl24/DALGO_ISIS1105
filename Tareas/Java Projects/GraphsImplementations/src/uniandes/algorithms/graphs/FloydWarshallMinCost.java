package uniandes.algorithms.graphs;

public class FloydWarshallMinCost implements MinimumCost {
	/**
     * Calculates the minimum cost matrix using the Floyd-Warshall algorithm.
     *
     * @param graph A 2D array representing the graph, where graph[i][j] is the cost from node i to node j.
     *              Integer.MAX_VALUE is used to indicate no direct connection.
     * @return A 2D array representing the minimum cost between all pairs of nodes.
     */
    public int [][] getMinCostMatrix(int [][] graph){
        int n = graph.length;
        int[][] m = new int[n][n];

        for (int k=0;k<=n;k++){
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    if (k==0) m[i][j] = graph[i][j];
                    if (k>0 && i!=k-1 && j!=k-1) m[i][j] = Math.min(m[i][j],adder(m[i][k-1],m[k-1][j]));
                }
            }
        }
        return m;
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