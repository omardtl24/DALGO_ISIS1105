package uniandes.algorithms.graphs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GraphImplementations {
    public static void main(String[] args) throws Exception{
        String mode = args[0];
        String action = args[1];
        String inFilename = args[2];
        boolean print = "1".equals(args[3]);
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
                if (parts.length == 2) {
                    int fromNode = Integer.parseInt(parts[0]);
                    int toNode = Integer.parseInt(parts[1]);
                    edges.add(new int[]{fromNode, toNode}); // Store the edge
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
	        
	        if(print) printMinCostMatrix(answer,action);
	        saveMatrixToFile(answer, "data/distances"+numNodes+action+"solution.txt");
	        
        }else if(mode.equals("components")) {
        	UnweightedGraph graph = new  UnweightedGraph(edges,numNodes);
        	ConnectedComponents conComp;
        	
        	if(action.equals("BFS")) conComp = new BFSConnected();
        	else throw new IllegalArgumentException("Invalid action for components mode");
        	
        	startTime = System.nanoTime();
	        List<List<Integer>> answer = conComp.getComponents(graph);
	        endTime = System.nanoTime();
	        
	        if(print) printListOfLists(answer,action);
	        saveListsToFile(answer, "data/components"+numNodes+action+"solution.txt");
        	
        }else if(mode.equals("Problems")) {
        	if(action.equals("CityCosts")) {
        		
        		WeightedGraph graph = new WeightedGraph(edges,numNodes);
        		MinimumSpanningTree mst = new KruskalMST();
        		
        		startTime = System.nanoTime();
    	        List<int []> answer = mst.getMST(graph);
    	        endTime = System.nanoTime();
    	        
    	        if(print) printPairs(answer, graph);
    	        savePairsToFile(answer, "data/ProblemsCity"+numNodes+"solution.txt");
        		
        	}else throw new IllegalArgumentException("Invalid action for components mode");
        	
        }else throw new IllegalArgumentException("Invalid mode");
        
        duration = endTime - startTime;
        duration/=1000;
        System.out.println("Execution time in microseconds using "+action+" for "+numNodes+" nodes graph: " + duration);

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
    
    /**
     * Saves an integer matrix to a txt file with elements tab-separated.
     *
     * @param matrix The integer matrix to be saved.
     * @param filename The name of the file where the matrix will be saved.
     * @throws IOException If an I/O error occurs during file writing.
     */
    public static void saveMatrixToFile(int[][] matrix, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int[] row : matrix) {
                for (int i = 0; i < row.length; i++) {
                    writer.write(String.valueOf(row[i]));
                    if (i < row.length - 1) {
                        writer.write("\t"); // Add tab between elements
                    }
                }
                writer.newLine(); // Move to the next line after each row
            }
        }
    }
    
    /**
     * Prints a List of Lists of integers in a specific format.
     * 
     * Each list is printed as an array enclosed in braces and separated by commas.
     *
     * @param listOfLists The List of Lists of integers to be printed.
     */
    public static void printListOfLists(List<List<Integer>> listOfLists, String action) {
        StringBuilder sb = new StringBuilder();  // Efficient string construction
        sb.append("Components found using "+action+": {");  // Start with the opening brace for the whole structure

        // Loop through the outer list (list of lists)
        for (int i = 0; i < listOfLists.size(); i++) {
            List<Integer> innerList = listOfLists.get(i);  // Get the current inner list
            sb.append("{");  // Add opening brace for the inner list

            // Loop through each element in the inner list
            for (int j = 0; j < innerList.size(); j++) {
                sb.append(innerList.get(j));  // Append the element
                if (j < innerList.size() - 1) {
                    sb.append(",");  // Add a comma between elements in the inner list
                }
            }
            
            sb.append("}");  // Close the inner list
            if (i < listOfLists.size() - 1) {
                sb.append(",");  // Add a comma between inner lists
            }
        }

        sb.append("} \n");  // Close the outer structure
        System.out.println(sb.toString());  // Output the final formatted string
    }
    
    /**
     * Saves a list of lists of integers to a specified text file.
     *
     * @param lists The list of lists of integers to save.
     * @param filePath The path to the text file where the data will be saved.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public static void saveListsToFile(List<List<Integer>> lists, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (List<Integer> list : lists) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i));
                    if (i < list.size() - 1) {
                        sb.append(" "); // Add space between elements
                    }
                }
                writer.write(sb.toString());
                writer.newLine(); // Write a new line after each list
            }
        }
    }
    
    /**
     * Prints a List of int arrays, each containing two integers.
     *
     * @param pairs The List of int arrays to be printed.
     * @throws Exception 
     */
    public static void printPairs(List<int[]> pairs, WeightedGraph graph) throws Exception {
        StringBuilder sb = new StringBuilder();  // StringBuilder for efficient string construction
        sb.append("Edges from MST: {");  // Start with the opening brace
        int cost = 0;

        // Loop through each int array in the list
        for (int i = 0; i < pairs.size(); i++) {
            int[] pair = pairs.get(i);  // Get the current int array (pair)
            sb.append("(");  // Add opening parenthesis for the pair
            sb.append(pair[0]);  // Append the first element of the pair
            sb.append(",");  // Append a comma between the elements
            sb.append(pair[1]);  // Append the second element of the pair
            sb.append(")");  // Close the parenthesis for the pair
            cost += graph.cost(pair[0],pair[1]);
            // Add a comma between pairs, except after the last pair
            if (i < pairs.size() - 1) {
                sb.append(",");  
            }
        }
        

        sb.append("} \nThe minimum cost associated is: "+cost+"\n");  // Close the outer brace
        System.out.println(sb.toString());  // Output the final formatted string
    }
    
    /**
     * Saves a List of integer pairs to a text file, each pair on a new line.
     * The format of each pair is "x y".
     *
     * @param pairs    The List of integer pairs (int arrays) to be written.
     * @param filename The name of the output text file.
     * @throws IOException If an I/O error occurs during file writing.
     */
    public static void savePairsToFile(List<int[]> pairs, String filename) throws IOException {
        // Use BufferedWriter for efficient writing to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Loop through each pair (int array) in the list
            for (int[] pair : pairs) {
                // Write the pair in the format "x y"
                writer.write(pair[0] + " " + pair[1]);
                writer.newLine();  // Move to the next line after writing each pair
            }
        }
    }
}
