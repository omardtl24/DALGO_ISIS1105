package uniandes.algorithms.graphs;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFSConnected implements ConnectedComponents{
	public List<List<Integer>> getComponents(UnweightedGraph graph){
		
		List<List<Integer>> components = new ArrayList<>();
		int n = graph.numNodes();
		int[] states = new int[n];
		
		for(int i=0; i<n; i++) {
			if(states[i]==0) {
				List<Integer> component = BFSComponent(i,graph,states);
				components.add(component);
			}
		}
		
		return components;
		
	}
	
	public List<Integer> BFSComponent(int source , UnweightedGraph graph, int[] states){
		
		List<Integer> group = new ArrayList<Integer>();
		
		states[source] = 1;
		
		Queue<Integer> q = new LinkedList<Integer>();
		
		q.add(source);
		while(!q.isEmpty()) {
			int u = q.poll();
			for(int v: graph.adj(u)) {
				if (states[v]==0) {
					states[v]=1;
					q.add(v);
				}
				
			}
			states[u] = 2;
			group.add(u);
		}
		return group;
	}
}
