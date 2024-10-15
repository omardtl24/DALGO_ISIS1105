package uniandes.algorithms.graphs;

public interface MaximumFlow {
	public MaximumFlowAnswer getMaximumFlow(WeightedDiGraph graph , int source, int sink) throws Exception;
}
