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
    public static void main(String[] args) throws Exception{
        String mode = args[0];
        String action = args[1];
        String inFilename;
        String inFilename1;
        String outputFile;
        boolean print;
        long startTime;
        long endTime;
        long duration;
        int numNodes;

        if(mode.equals("minCost")){
        	inFilename = args[2];
        	outputFile = args[3];
            print = "1".equals(args[4]);
        	InputGraph input = getInputGraph(inFilename);
        	List<int []> edges = input.getInput();
        	numNodes = input.getNodes(); 
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
	        saveMatrixToFile(answer, outputFile);
	        
        }else if(mode.equals("components")) {
        	inFilename = args[2];
        	outputFile = args[3];
            print = "1".equals(args[4]);
        	InputGraph input = getInputGraph(inFilename);
        	List<int []> edges = input.getInput();
        	numNodes = input.getNodes(); 
         	UnweightedGraph graph = new  UnweightedGraph(edges,numNodes);
        	ConnectedComponents conComp;
        	
        	if(action.equals("BFS")) conComp = new BFSConnected();
        	else throw new IllegalArgumentException("Invalid action for components mode");
        	
        	startTime = System.nanoTime();
	        List<List<Integer>> answer = conComp.getComponents(graph);
	        endTime = System.nanoTime();
	        
	        if(print) printListOfLists(answer,action);
	        saveListsToFile(answer, outputFile);
        	
        }else if(mode.equals("problems")) {
        	if(action.equals("CityCosts")) {
        		inFilename = args[2];
            	outputFile = args[3];
                print = "1".equals(args[4]);
        		InputGraph input = getInputGraph(inFilename);
            	List<int []> edges = input.getInput();
            	numNodes = input.getNodes(); 
        		WeightedGraph graph = new WeightedGraph(edges,numNodes);
        		MinimumSpanningTree mst = new KruskalMST();
        		
        		startTime = System.nanoTime();
    	        List<int []> answer = mst.getMST(graph);
    	        endTime = System.nanoTime();
    	        
    	        if(print) printPairs(answer, graph);
    	        savePairsToFile(answer, outputFile);
        		
        	}else if(action.equals("BooksDelivery")){
        		inFilename = args[2];
        		inFilename1 = args[3];
            	outputFile = args[4];
                print = "1".equals(args[5]);
                InputGraph input =  getInputBooksProblem( inFilename, inFilename1);
                List<int[]> edges = input.getInput();
                String[] namesMapper = input.getNames();
                numNodes = input.getNodes();
                WeightedDiGraph graph = new WeightedDiGraph(edges, numNodes);
                MaximumFlow mFlow = new EdmondsKarpMaxFlow();
                startTime = System.nanoTime();
                MaximumFlowAnswer answer = mFlow.getMaximumFlow(graph, 0,1);
                List<String[]> answertoSave = translateBusesCapacityAnswer(answer.getFlows(), namesMapper);
    	        endTime = System.nanoTime();
    	        
    	        if(print) System.out.println("Maximum flow gotten was: "+answer.getMaxFlowValue());
    	        saveListToFile(answertoSave,answer.getMaxFlowValue(),outputFile);
        	}else throw new IllegalArgumentException("Invalid action for components mode");
        	
        }else throw new IllegalArgumentException("Invalid mode");
        
        duration = endTime - startTime;
        duration/=1000;
        System.out.println("Execution time in microseconds using "+action+" for "+numNodes+" nodes graph: " + duration);

    }
    
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
            return null;
        }
        InputGraph input = new InputGraph(edges,numNodes,null);
        return input;
    }
    
    public static InputGraph getInputBooksProblem(String inFilenameVaultCapacities, String inFilenameBusesInformation) {
    	List<int[]> edges = new ArrayList<>();
    	Map<String, Boolean> vaultNames = new HashMap<String,Boolean>();
    	Map<String, Integer> InNodes = new HashMap<String, Integer>();
    	List<String> namesMapper = new ArrayList<String>();
    	//Nodes 0 and 1 are set for the superSource and the superSink
    	namesMapper.add("superSource");
    	namesMapper.add("superSink");
    	int numNodes = 2;
        // Read the file and parse the edges
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
                    vaultNames.put(vault,true);
                    edges.add(new int[] {numNodes,numNodes+1,maxCapacity});
                    numNodes+=2;
                } else throw new IllegalArgumentException("Vault Capacities file does not have the required format");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
        
        
        try (BufferedReader in = new BufferedReader(new FileReader(inFilenameBusesInformation))) {
            String line;
            int s,d;
            while ((line = in.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 3) {
                    String source = parts[0];
                    String destiny = parts[1];
                    int busCapacity = Integer.parseInt(parts[2]);
                    
                    if(InNodes.containsKey(source)) {
                    	if(vaultNames.containsKey(source)) {
                    		s = InNodes.get(source)+1;
                    	}else {
                    		s = InNodes.get(source);
                    	}
                    }else{
                    	namesMapper.add(source);
                    	s = numNodes;
                    	InNodes.put(source, s);
                    	numNodes+=1;
                    }
                    
                    if(!vaultNames.containsKey(source)) edges.add(new int[] {0,s,Integer.MAX_VALUE});
                    
                    if(InNodes.containsKey(destiny)) {
                    	d = InNodes.get(destiny);
                    }else{
                    	namesMapper.add(destiny);
                    	d = numNodes;
                    	InNodes.put(destiny, d);
                    	numNodes+=1;
                    }
                    
                    if(!vaultNames.containsKey(destiny)) edges.add(new int[] {d,1,Integer.MAX_VALUE});
                    
                    edges.add(new int[] {s,d,busCapacity});

                } else throw new IllegalArgumentException("Buses Information file does not have the required format");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
        
        String[] names = namesMapper.toArray(new String[0]);
        InputGraph input = new InputGraph(edges,numNodes,names);
        return input;
    }
    
    public static void saveListToFile(List<String[]> data, int maxflow, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Iterate through each String[] in the List
        	 writer.write(Integer.toString(maxflow));
             writer.newLine(); 
            for (String[] line : data) {
                // Join the elements of String[] by spaces and write them to the file
                writer.write(String.join(" ", line));
                writer.newLine();  // Move to the next line after writing the array
            }
        } catch (IOException e) {
            e.printStackTrace();  // Handle any file I/O exceptions
        }
    }
    
    private static List<String[]> translateBusesCapacityAnswer( Map<EdgeArray , Integer> flows , String[] names) {
    	List<String []> answer = new ArrayList<>();
    	String source, destiny, f;
    	System.out.println("Nodes  names array size: "+names.length);
    	for(EdgeArray edge : flows.keySet()) {
    		int[] flow = edge.getEdge();
    		if(flow[0]>1 && flow[1]>1) {
    			source = names[flow[0]];
        		destiny = names[flow[1]];
        		f = String.valueOf(flows.get(edge));
        		if(!source.equals(destiny)) answer.add(new String[] {source, destiny, f});
    		}
    	}
    	
    	return answer;
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
