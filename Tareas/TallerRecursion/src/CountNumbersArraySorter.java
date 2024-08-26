import java.util.List;
import java.util.Collections;

/**
 * Implements the bubble sort algorithm for number arrays
 */
public class CountNumbersArraySorter implements NumbersArraySorter {
	/**
	 * Sorts the given numbers in ascending order using count sort algorithm
	 * @param numbers to sort
	 */
	@Override
	public void sort(List<Integer> numbers) {
		
		int maxval = Collections.max(numbers);
		int[] count = new int[maxval+1];
		int[] sorted = new int[numbers.size()];
		int index;
		
		for(int i=0; i < numbers.size(); i++) {
			index = numbers.get(i);
			count[index] ++;
		}
		
		for(int i=1; i <= maxval; i++) {
			count[i] += count[i-1];
		}
		
		for(int i=numbers.size()-1; i >= 0 ; i--) {
			index = numbers.get(i);
			count[index] --;
			sorted[count[index]] = index;
		}
		
		for(int i=0; i < numbers.size(); i++) {
			numbers.set(i, sorted[i]);
		}
		
	}

}
