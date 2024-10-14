package uniandes.algorithms.graphs;

import java.util.List;

public class InputGraph{
	private List<int []> input;
	private int nodes;
	private String[] namesMapper;
	
	public InputGraph(List<int []> i , int n, String[] names){
		input = i;
		nodes = n;
		namesMapper = names;
	}
	
	public int getNodes() {
		return nodes;
	}
	
	public List<int []> getInput() {
		return input;
	}
	
	public String[] getNames() {
		return namesMapper;
	}
}