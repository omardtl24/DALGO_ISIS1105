import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a structure to store partitions of the numbers from 0 to n-1
 * It corresponds to the structures named disjoint sets or union-find 
 */
public class ParticionArrayList {
	int n;
	//TODO: Define attributes to implement structure
	List<List<Integer>> subsets;
	
	/**
	 * Builds a new partition for n elements.
	 * Assigns each element to a different subset
	 * @param n Number of elements to consider
	 */
	public Partition (int n) {
		this.n = n;
		subsets = new ArrayList<>();
		//TODO: finish implementation
		for(int i=0;i<n;i++){
			List<Integer> subset = new ArrayList<>();
			subset.add(i);
			subsets.add(subset);
		}

	}
	/**
	 * Modifies the partition merging into a single subset the subsets to which the given elements belong
	 * If the two elements belong to the same subset it leaves the structure unchanged
	 * @param e1 Element belonging to the first subset. 0<=e1<n
	 * @param e2 Element belonging to the second subset. 0<=e2<=n
	 */
	public void union (int e1, int e2) {
		int s1 = find(e1);
		int s2 = find(e2);
		if(s1==s2) return;
		//TODO: Finish immplementation
		List<Integer> set1 = subsets.get(s1);
		List<Integer> set2 = subsets.get(s2);
		set1.addAll(set2);
		subsets.remove(s2);
	}
	/**
	 * Returns the identifier of the subset to which the given element belongs
	 * @param e Element to find. 0<=e<n
	 * @return int Subset to which the element belongs
	 */
	public int find (int e) {
		//TODO: Implement
		for(int i=0;i<subsets.size();i++){
			if (subsets.get(i).contains(e)) return i;
		}
		return 0;
	}
	/**
	 * Determines if the two elements belong to the same subset.
	 * @param e1 First element
	 * @param e2 Second element
	 * @return boolean true if e1 and e2 belong to the same subset. False otherwise
	 */
	public boolean sameSubsets(int e1, int e2) {
		return find(e1)==find(e2);
	}
}
