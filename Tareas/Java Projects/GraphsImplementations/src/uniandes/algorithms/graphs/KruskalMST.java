package uniandes.algorithms.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KruskalMST implements MinimumSpanningTree{
	
	public List<int []> getMST(WeightedGraph graph){
		List<int []> answer = new ArrayList<>();
		Partition set = new Partition(graph.numNodes());
		
		List<EdgeArray> edges = graph.edges();
		Collections.sort(edges, new Comparator<EdgeArray>() {
			@Override
			public int compare(EdgeArray a1, EdgeArray a2) {
				try {
					return Integer.compare(graph.cost(a1), graph.cost(a2));
				} catch (Exception e) {
					return Integer.MAX_VALUE;
				}
					
			}
		});
		
		for (EdgeArray edge: edges) {
			int[] u_v = edge.getEdge();
			if (!set.sameSubsets(u_v[0],u_v[1])) {
				answer.add(u_v);
				set.union(u_v[0],u_v[1]);
			}
		}
		
		return answer;
	}
}
