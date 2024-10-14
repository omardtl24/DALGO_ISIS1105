package uniandes.algorithms.graphs;

import java.util.List;

public class InputGraph{
	private List<int []> input;
	private int nodes;
	
	public InputGraph(List<int []> i , int n){
		input = i;
		nodes = n;
	}
	
	public int getNodes() {
		return nodes;
	}
	
	public List<int []> getInput() {
		return input;
	}
}