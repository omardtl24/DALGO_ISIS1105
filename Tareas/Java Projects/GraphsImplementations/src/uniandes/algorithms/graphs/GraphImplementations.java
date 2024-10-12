package uniandes.algorithms.graphs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GraphImplementations {
    public static void main(String[] args) throws Exception{
        String mode = args[0];
        String action = args[1];
        String inFilename = args[2];
        int numNodes = 0;
        long startTime;
        long endTime;
        long duration;

        List<int[]> edges = new ArrayList<>();

        // Read the file and parse the edges
        try (BufferedReader in = new BufferedReader(new FileReader(inFilename))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 3) {
                    int fromNode = Integer.parseInt(parts[0]);
                    int toNode = Integer.parseInt(parts[1]);
                    int cost = Integer.parseInt(parts[2]);
                    edges.add(new int[]{fromNode, toNode, cost}); // Store the edge
                    numNodes = Math.max(numNodes,Math.max(fromNode,toNode)+1);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        if(mode.equals("minCost")){
        	WeightedDiGraph graph = new WeightedDiGraph(edges, numNodes);
            MinimumCost minCost;
            
	        if(action.equals("Dijkstra"))  minCost = new DijkstraMinCost();
	        else if(action.equals("BellmanFord")) minCost = new BellmanFordMinCost();
	        else if(action .equals("FloydWarshall")) minCost = new FloydWarshallMinCost();
	        else throw new IllegalArgumentException("Invalid action for minCost mode");
	        startTime = System.nanoTime();
	        int[][] answer = minCost.getMinCostMatrix(graph);
	        endTime = System.nanoTime();
	        printMinCostMatrix(answer,action);
	        duration = endTime - startTime;
	        duration/=1000;
	        System.out.println("Execution time in microseconds: " + duration);
            
        }else throw new IllegalArgumentException("Invalid mode");
        

    }

    public static void printMinCostMatrix(int[][] matrix, String name) {
    	
    	int numNodes = matrix.length;
    	
        System.out.println("\nMinimum Cost Matrix using "+name+": \n");
        
        // Print header
        System.out.print("      |");  // Indentation for row labels
        for (int i = 0; i < numNodes; i++) {
            System.out.printf("%5d", i); // Print column headers
        }
        System.out.println();
    
        // Print separator line
        System.out.println("---------------------------------");
    
        // Print matrix with row labels and formatted values
        for (int i = 0; i < numNodes; i++) {
            System.out.printf("%5d |", i); // Print row header and vertical separator
    
            for (int j = 0; j < numNodes; j++) {
                if (matrix[i][j] == Integer.MAX_VALUE) {
                    System.out.printf("%5s", "INF"); // Print "INF" for infinite values
                } else {
                    System.out.printf("%5d", matrix[i][j]); // Print matrix values
                }
            }
            System.out.println(); // Move to the next row
        }
        System.out.println();
    }
    
}
