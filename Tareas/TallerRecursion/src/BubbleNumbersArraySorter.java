import java.util.List;

/**
 * Implements the bubble sort algorithm for number arrays
 */
public class BubbleNumbersArraySorter implements NumbersArraySorter {
	/**
	 * Sorts the given numbers in ascending order using bubble sort algorithm
	 * @param numbers to sort
	 */
	@Override
	public void sort(List<Integer> numbers) {
		int n = numbers.size()-1;
		int swap;
		for(int i=0;i<=n-1;i++) {
			for(int j=n; j>=i+1; j--) {
				//Check if swapping is needed
				if (numbers.get(j)<numbers.get(j-1)) {
					swap = numbers.get(j);
					numbers.set(j,numbers.get(j-1));
					numbers.set(j-1,swap);
				}
			}
		}
	}

}
