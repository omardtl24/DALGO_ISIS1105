package uniandes.algorithms.graphs;
import java.util.Map;

public class MaximumFlowAnswer{
	private int maximumFlow;
	private Map<EdgeArray , Integer> flows;
	public MaximumFlowAnswer(Map<EdgeArray , Integer> f,  int maxFlow) {
		maximumFlow = maxFlow;
		flows = f;
	}
	
	public int getMaxFlowValue() {
		return maximumFlow;
	}
	
	public Map<EdgeArray , Integer> getFlows() {
		return flows;
	}
}
