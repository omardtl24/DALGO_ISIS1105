import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Example program to test algorithms to build spanning trees
 */
public class SpanningTreesExample {

	/**
	 * Main function. The program receives a single argument with a file containing the edges of the graph
	 * The file must be a text file with at least three coluns separated by tab:
	 * 1. First vertex
	 * 2. Second vertex
	 * 3. Edge weight
	 * @param args Array of program arguments. Only the first argument is required.
	 * @throws Exception If the file can not be processed
	 */
	public static void main(String[] args) throws Exception {
		SpanningTreesExample instance = new SpanningTreesExample();
		instance.process(args[0]);
	}
	/**
	 * Process the given file. Writes the edges of the output tree to the standard output
	 * @param graphFile FIle with the edges of the graph.
	 * See the format in the main function or in the loadGraph method
	 * @throws IOException If the file can not be processed
	 */
	public void process(String graphFile) throws IOException {
		List<UndirectedWeightedEdge> graph = loadGraph(graphFile);
		if(!validateConnected(graph)) {
			System.out.println("ERROR: The given graph is not connected");
			return;
		}
		SpanningTreeAlgorithm algorithm = new SpanningTreeAlgorithmKruskal();
		List<UndirectedWeightedEdge> tree = algorithm.findSpanningTree(graph);
		printTree(tree);
	}
	/**
	 * Validates if the undirected graph defined by the given edges is connected
	 * @param graph List of edges defining the graph
	 * @return boolean True if the graph is connected. false otherwise
	 */
	public boolean validateConnected(List<UndirectedWeightedEdge> graph) {
		Map<Integer, List<Integer>> adjacencyList = buildAdjacencyList(graph);
		return validateConnected(adjacencyList);
	}
	/**
	 * Builds an adjacency list representation of the given graph
	 * @param graph List of edges defining the graph
	 * @return Map<Integer, List<Integer>> graph defined as a map with vertices as keys.
	 * For each vertex v, the value corresponds to the list of vertices connected to v in the graph
	 */
	public Map<Integer, List<Integer>> buildAdjacencyList(List<UndirectedWeightedEdge> graph) {
		Map<Integer,List<Integer>> adjacencyList = new HashMap<>();
		for(UndirectedWeightedEdge edge:graph) {
			int v1 = edge.getV1();
			int v2 = edge.getV2();
			List<Integer> edges1 = adjacencyList.computeIfAbsent(v1, v->new ArrayList<Integer>());
			edges1.add(v2);
			List<Integer> edges2 = adjacencyList.computeIfAbsent(v2, v->new ArrayList<Integer>());
			edges2.add(v1);
		}
		return adjacencyList;
	}
	/**
	 * Validates if the undirected graph defined by the given edges is connected
	 * @param graph Defied as as a map with vertices as keys.
	 * For each vertex v, the value corresponds to the list of vertices connected to v in the graph. 
	 * @return boolean True if the graph is connected. false otherwise
	 */
	public boolean validateConnected(Map<Integer, List<Integer>> graph) {
		int n = graph.size();
		Queue<Integer> queue = new LinkedList<Integer>();
		boolean [] visited = new boolean[n];
		visited[0] = true;
		queue.add(0);
		int nprocessed = 0;
		while(queue.size()>0) {
			int next = queue.remove();
			nprocessed++;
			for(int v2:graph.get(next)) {
				if(!visited[v2]) {
					visited[v2]=true;
					queue.add(v2);
				}
			}
		}
		return nprocessed == n;
	}
	
	
	/**
	 * Loads an undirected graph from the given file.
	 * The file must be a text file with at least three coluns separated by tab:
	 * 1. First vertex
	 * 2. Second vertex
	 * 3. Edge weight
	 * @param graphFile File with the information to load 
	 * @return List<UndirectedWeightedEdge> Graph defined by the list of edges
	 * @throws IOException If the file can not be processed
	 */
	public List<UndirectedWeightedEdge> loadGraph(String graphFile) throws IOException {
		List<UndirectedWeightedEdge> graph = new ArrayList<UndirectedWeightedEdge>();
		try (FileReader reader = new FileReader(graphFile);
				BufferedReader in = new BufferedReader(reader)) {
			String line = in.readLine();
			while(line!=null) {
				String [] items = line.split("\t");
				graph.add(new UndirectedWeightedEdge(Integer.parseInt(items[0]), Integer.parseInt(items[1]), Integer.parseInt(items[2])));
				line = in.readLine();
			}
		}
		return graph;
	}
	/**
	 * Prints to the standard output the information of the tree defined by the given edges.
	 * It also prints the total cost
	 * @param tree represented as a list of undirected edges
	 */
	public void printTree(List<UndirectedWeightedEdge> tree) {
		int totalWeight = 0;
		for(UndirectedWeightedEdge edge: tree) {
			totalWeight += edge.getWeight();
			System.out.println("v1: "+edge.getV1()+" v2: "+edge.getV2()+" weight: "+edge.getWeight());
		}
		System.out.println("Total weight tree: "+totalWeight);
	}
}
