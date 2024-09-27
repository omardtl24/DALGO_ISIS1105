import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Construction of spanning trees implementing the Kruskal algorithm 
 * @author Jorge Duitama
 */
public class SpanningTreeAlgorithmKruskal implements SpanningTreeAlgorithm {

	@Override
	public List<UndirectedWeightedEdge> findSpanningTree(List<UndirectedWeightedEdge> graph) {
		int n = findMaxVertex(graph)+1;
		List<UndirectedWeightedEdge> copy = new ArrayList<UndirectedWeightedEdge>(graph);
		Collections.sort(copy, (e1,e2)->e1.getWeight()-e2.getWeight());
		List<UndirectedWeightedEdge> answer = new ArrayList<UndirectedWeightedEdge>();
		Partition p = new Partition(n);
		for(int i=0;i<copy.size() && answer.size()<n;i++) {
			UndirectedWeightedEdge next = copy.get(i);
			if(!p.sameSubsets(next.getV1(), next.getV2())) {
				answer.add(next);
				p.union(next.getV1(), next.getV2());
			}
		}
		return answer;
	}
	/**
	 * FInds the vertex with maximum number in the graph defined by the given set of edges 
	 * @param graph List of edges defining the graph.
	 * @return int Maximum on the numbers assigned to the vertices
	 */
	private int findMaxVertex(List<UndirectedWeightedEdge> graph) {
		int max = 0;
		for(UndirectedWeightedEdge edge:graph) {
			max = Math.max(max, edge.getV1());
			max = Math.max(max, edge.getV2());
		}
		return max;
	}

}
