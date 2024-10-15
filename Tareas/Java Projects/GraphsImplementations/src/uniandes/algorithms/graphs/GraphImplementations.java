package uniandes.algorithms.graphs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GraphImplementations {
	/**
	 * Main method to execute different graph algorithms based on the provided mode and action.
	 *
	 * @param args Command line arguments where:
	 *             args[0] - Mode of operation (e.g., "minCost", "components", "problems")
	 *             args[1] - Specific action to perform based on the mode (e.g., "Dijkstra", "BFS", "CityCosts")
	 *             args[2] - Input filename for the graph
	 *             args[3] - Output filename for saving results
	 *             args[4] - Flag to indicate whether to print results (for minCost and components modes)
	 *             args[5] - Additional input filename (for problems mode, e.g., for "BooksDelivery")
	 *
	 * @throws Exception If an invalid mode or action is provided, or if there's an error reading files.
	 */
	public static void main(String[] args) throws Exception {
	    // Declare variables to store the mode, action, filenames, flags, timing, and node count
	    String mode = args[0]; // Mode of operation (e.g., minCost, components, problems)
	    String action = args[1]; // Specific action to perform based on the mode
	    String inFilename; // Input filename for the graph
	    String inFilename1; // Second input filename for certain actions
	    String outputFile; // Output filename for saving results
	    boolean print; // Flag to determine if results should be printed
	    long startTime; // Start time for measuring execution time
	    long endTime; // End time for measuring execution time
	    long duration; // Duration of execution time
	    int numNodes; // Number of nodes in the graph

	    // Handle the 'minCost' mode
	    if (mode.equals("minCost")) {
	        inFilename = args[2]; // Get input filename
	        outputFile = args[3]; // Get output filename
	        print = "1".equals(args[4]); // Set print flag based on command line argument
	        InputGraph input = getInputGraph(inFilename); // Read the input graph from the file
	        List<int[]> edges = input.getInput(); // Retrieve edges from the input graph
	        numNodes = input.getNodes(); // Get the number of nodes in the graph
	        WeightedDiGraph graph = new WeightedDiGraph(edges, numNodes); // Create a weighted directed graph
	        MinimumCost minCost; // Declare a variable for the minimum cost algorithm
	        
	        // Select the appropriate minimum cost algorithm based on the action
	        if (action.equals("Dijkstra")) 
	            minCost = new DijkstraMinCost();
	        else if (action.equals("BellmanFord")) 
	            minCost = new BellmanFordMinCost();
	        else if (action.equals("FloydWarshall")) 
	            minCost = new FloydWarshallMinCost();
	        else 
	            throw new IllegalArgumentException("Invalid action for minCost mode");
	        
	        startTime = System.nanoTime(); // Start timing
	        int[][] answer = minCost.getMinCostMatrix(graph); // Compute the minimum cost matrix
	        endTime = System.nanoTime(); // End timing
	        
	        // Print the result if the print flag is set
	        if (print) 
	            printMinCostMatrix(answer, action);
	        saveMatrixToFile(answer, outputFile); // Save the results to the output file
	        
	    // Handle the 'components' mode
	    } else if (mode.equals("components")) {
	        inFilename = args[2]; // Get input filename
	        outputFile = args[3]; // Get output filename
	        print = "1".equals(args[4]); // Set print flag
	        InputGraph input = getInputGraph(inFilename); // Read the input graph from the file
	        List<int[]> edges = input.getInput(); // Retrieve edges from the input graph
	        numNodes = input.getNodes(); // Get the number of nodes in the graph
	        UnweightedGraph graph = new UnweightedGraph(edges, numNodes); // Create an unweighted graph
	        ConnectedComponents conComp; // Declare a variable for connected components algorithm
	        
	        // Select the appropriate algorithm based on the action
	        if (action.equals("BFS")) 
	            conComp = new BFSConnected();
	        else 
	            throw new IllegalArgumentException("Invalid action for components mode");
	        
	        startTime = System.nanoTime(); // Start timing
	        List<List<Integer>> answer = conComp.getComponents(graph); // Compute connected components
	        endTime = System.nanoTime(); // End timing
	        
	        // Print the result if the print flag is set
	        if (print) 
	            printListOfLists(answer, action);
	        saveListsToFile(answer, outputFile); // Save the results to the output file
	        
	    // Handle the 'problems' mode
	    } else if (mode.equals("problems")) {
	        // Handle the 'CityCosts' action
	        if (action.equals("CityCosts")) {
	            inFilename = args[2]; // Get input filename
	            outputFile = args[3]; // Get output filename
	            print = "1".equals(args[4]); // Set print flag
	            InputGraph input = getInputGraph(inFilename); // Read the input graph from the file
	            List<int[]> edges = input.getInput(); // Retrieve edges from the input graph
	            numNodes = input.getNodes(); // Get the number of nodes in the graph
	            WeightedGraph graph = new WeightedGraph(edges, numNodes); // Create a weighted graph
	            MinimumSpanningTree mst = new KruskalMST(); // Initialize Kruskal's algorithm
	            
	            startTime = System.nanoTime(); // Start timing
	            List<int[]> answer = mst.getMST(graph); // Compute the minimum spanning tree
	            endTime = System.nanoTime(); // End timing
	            
	            // Print the result if the print flag is set
	            if (print) 
	                printPairs(answer, graph);
	            savePairsToFile(answer, outputFile); // Save the results to the output file
	            
	        // Handle the 'BooksDelivery' action
	        } else if (action.equals("BooksDelivery")) {
	            inFilename = args[2]; // Get first input filename
	            inFilename1 = args[3]; // Get second input filename
	            outputFile = args[4]; // Get output filename
	            print = "1".equals(args[5]); // Set print flag
	            InputGraph input = getInputBooksProblem(inFilename, inFilename1); // Read the input for books delivery problem
	            List<int[]> edges = input.getInput(); // Retrieve edges from the input graph
	            String[] namesMapper = input.getNames(); // Get names mapping for nodes
	            numNodes = input.getNodes(); // Get the number of nodes in the graph
	            WeightedDiGraph graph = new WeightedDiGraph(edges, numNodes); // Create a weighted directed graph
	            MaximumFlow mFlow = new EdmondsKarpMaxFlow(); // Initialize the maximum flow algorithm
	            
	            startTime = System.nanoTime(); // Start timing
	            MaximumFlowAnswer answer = mFlow.getMaximumFlow(graph, 0, 1); // Compute maximum flow
	            List<String[]> answertoSave = translateBusesCapacityAnswer(answer.getFlows(), namesMapper); // Translate the flow answer
	            endTime = System.nanoTime(); // End timing
	            
	            // Print the maximum flow result if the print flag is set
	            if (print) 
	                System.out.println("Maximum flow gotten was: " + answer.getMaxFlowValue());
	            saveListToFile(answertoSave, answer.getMaxFlowValue(), outputFile); // Save the results to the output file
	            
	        } else 
	            throw new IllegalArgumentException("Invalid action for components mode"); // Handle invalid action
	        
	    } else 
	        throw new IllegalArgumentException("Invalid mode"); // Handle invalid mode
	    
	    // Calculate and print the execution duration in microseconds
	    duration = endTime - startTime;
	    duration /= 1000; // Convert from nanoseconds to microseconds
	    System.out.println("Execution time in microseconds using " + action + " for " + numNodes + " nodes graph: " + duration);
	}

    
	/**
	 * Reads a graph from a file and creates an InputGraph object.
	 *
	 * @param inFilename The name of the input file containing graph edges.
	 *                   Each line of the file should contain two or three integers
	 *                   representing the nodes and the cost of the edge (if applicable).
	 * @return An InputGraph object containing the edges and number of nodes, 
	 *         or null if there was an error reading the file.
	 * @throws IOException If an I/O error occurs while reading the file.
	 */
	public static InputGraph getInputGraph(String inFilename) {
	    List<int[]> edges = new ArrayList<>();
	    int numNodes = 0;

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
	                numNodes = Math.max(numNodes, Math.max(fromNode, toNode) + 1);
	            }
	            if (parts.length == 2) {
	                int fromNode = Integer.parseInt(parts[0]);
	                int toNode = Integer.parseInt(parts[1]);
	                edges.add(new int[]{fromNode, toNode}); // Store the edge
	                numNodes = Math.max(numNodes, Math.max(fromNode, toNode) + 1);
	            }
	        }
	    } catch (IOException e) {
	        System.err.println("Error reading file: " + e.getMessage());
	        return null; // Return null in case of an error
	    }

	    InputGraph input = new InputGraph(edges, numNodes, null);
	    return input; // Return the created InputGraph object
	}

    
	/**
	 * Reads vault capacities and buses information from files to create an InputGraph for a transportation problem.
	 *
	 * @param inFilenameVaultCapacities The name of the input file containing vault capacities.
	 *                                   Each line should contain a vault name followed by its maximum capacity.
	 * @param inFilenameBusesInformation The name of the input file containing buses information.
	 *                                   Each line should contain a source, a destination, and the bus capacity.
	 * @return An InputGraph object containing the edges and number of nodes, 
	 *         or null if there was an error reading either file.
	 * @throws IllegalArgumentException If the input files do not have the required format.
	 * @throws IOException If an I/O error occurs while reading the files.
	 */
	public static InputGraph getInputBooksProblem(String inFilenameVaultCapacities, String inFilenameBusesInformation) {
	    List<int[]> edges = new ArrayList<>();
	    Map<String, Boolean> vaultNames = new HashMap<String, Boolean>();
	    Map<String, Integer> InNodes = new HashMap<String, Integer>();
	    List<String> namesMapper = new ArrayList<String>();
	    // Nodes 0 and 1 are set for the superSource and the superSink
	    namesMapper.add("superSource");
	    namesMapper.add("superSink");
	    int numNodes = 2;

	    // Read the file and parse the edges for vault capacities
	    try (BufferedReader in = new BufferedReader(new FileReader(inFilenameVaultCapacities))) {
	        String line;
	        while ((line = in.readLine()) != null) {
	            String[] parts = line.trim().split("\\s+");
	            if (parts.length == 2) {
	                String vault = parts[0];
	                int maxCapacity = Integer.parseInt(parts[1]);
	                InNodes.put(vault, numNodes);
	                namesMapper.add(vault);
	                namesMapper.add(vault);
	                vaultNames.put(vault, true);
	                edges.add(new int[] { numNodes, numNodes + 1, maxCapacity });
	                numNodes += 2;
	            } else throw new IllegalArgumentException("Vault Capacities file does not have the required format");
	        }
	    } catch (IOException e) {
	        System.err.println("Error reading file: " + e.getMessage());
	        return null; // Return null in case of an error
	    }

	    // Read the file and parse the edges for buses information
	    try (BufferedReader in = new BufferedReader(new FileReader(inFilenameBusesInformation))) {
	        String line;
	        int s, d;
	        while ((line = in.readLine()) != null) {
	            String[] parts = line.trim().split("\\s+");
	            if (parts.length == 3) {
	                String source = parts[0];
	                String destiny = parts[1];
	                int busCapacity = Integer.parseInt(parts[2]);

	                if (InNodes.containsKey(source)) {
	                    if (vaultNames.containsKey(source)) {
	                        s = InNodes.get(source) + 1; // Adjust for vault node
	                    } else {
	                        s = InNodes.get(source);
	                    }
	                } else {
	                    namesMapper.add(source);
	                    s = numNodes;
	                    InNodes.put(source, s);
	                    numNodes += 1;
	                }

	                if (!vaultNames.containsKey(source)) edges.add(new int[] { 0, s, Integer.MAX_VALUE });

	                if (InNodes.containsKey(destiny)) {
	                    d = InNodes.get(destiny);
	                } else {
	                    namesMapper.add(destiny);
	                    d = numNodes;
	                    InNodes.put(destiny, d);
	                    numNodes += 1;
	                }

	                if (!vaultNames.containsKey(destiny)) edges.add(new int[] { d, 1, Integer.MAX_VALUE });

	                edges.add(new int[] { s, d, busCapacity });

	            } else throw new IllegalArgumentException("Buses Information file does not have the required format");
	        }
	    } catch (IOException e) {
	        System.err.println("Error reading file: " + e.getMessage());
	        return null; // Return null in case of an error
	    }

	    String[] names = namesMapper.toArray(new String[0]);
	    InputGraph input = new InputGraph(edges, numNodes, names);
	    return input; // Return the created InputGraph object
	}

    
	/**
	 * Saves a list of string arrays to a file, along with the maximum flow value.
	 *
	 * @param data A List of String arrays, where each String array represents a line of data to be written to the file.
	 * @param maxflow The maximum flow value to be written as the first line in the file.
	 * @param filename The name of the file where the data will be saved.
	 * @throws IOException If an I/O error occurs while writing to the file.
	 */
	public static void saveListToFile(List<String[]> data, int maxflow, String filename) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
	        // Write the maximum flow value as the first line
	        writer.write(Integer.toString(maxflow));
	        writer.newLine();
	        
	        // Iterate through each String[] in the List
	        for (String[] line : data) {
	            // Join the elements of String[] by spaces and write them to the file
	            writer.write(String.join(" ", line));
	            writer.newLine();  // Move to the next line after writing the array
	        }
	    } catch (IOException e) {
	        e.printStackTrace();  // Handle any file I/O exceptions
	    }
	}

    
	/**
	 * Translates the flow information from a map of edge capacities into a list of string arrays representing the flow between bus nodes.
	 *
	 * @param flows A map where each key is an EdgeArray representing the edges, and the value is the flow for that edge.
	 * @param names An array of strings representing the names of the nodes corresponding to their indices.
	 * @return A List of String arrays, each containing the source, destination, and flow value.
	 */
	private static List<String[]> translateBusesCapacityAnswer(Map<EdgeArray, Integer> flows, String[] names) {
	    List<String[]> answer = new ArrayList<>();
	    String source, destiny, f;
	    System.out.println("Nodes names array size: " + names.length);
	    
	    for (EdgeArray edge : flows.keySet()) {
	        int[] flow = edge.getEdge();
	        if (flow[0] > 1 && flow[1] > 1) {
	            source = names[flow[0]];
	            destiny = names[flow[1]];
	            f = String.valueOf(flows.get(edge));
	            if (!source.equals(destiny)) answer.add(new String[]{source, destiny, f});
	        }
	    }
	    
	    return answer;
	}


	/**
	 * Prints the minimum cost matrix in a formatted manner.
	 *
	 * @param matrix A 2D array representing the minimum cost between nodes, where matrix[i][j] is the cost from node i to node j.
	 * @param name A string representing the name of the algorithm or context used for the minimum cost calculation.
	 */
	public static void printMinCostMatrix(int[][] matrix, String name) {
	    int numNodes = matrix.length;
	    
	    System.out.println("\nMinimum Cost Matrix using " + name + ": \n");
	    
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