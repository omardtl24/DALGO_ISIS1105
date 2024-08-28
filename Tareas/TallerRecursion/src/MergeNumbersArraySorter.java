import java.util.ArrayList;
import java.util.List;

/**
 * Implements the merge sort algorithm for number arrays
 */
public class MergeNumbersArraySorter implements NumbersArraySorter {
	/**
	 * Sorts the given numbers in ascending order using merge sort algorithm
	 * @param numbers to sort
	 */
	@Override
	public void sort(List<Integer> numbers) {
		// TODO Implement
		int n = numbers.size()-1;
		merge(numbers,0,n);

	}
	/**
	 * Sorts the given numbers in ascending order from index s to f
	 * @param numbers to sort
	 * @param index where we begin the sorting process
	 * @param index where we finish the sorting process
	 */
	public void merge(List<Integer> numbers, int s, int f) {
		//Apply case base
		if(s==f) return;
		int mid = (s+f)/2;
		//Apply recursive vision for each subarray
		merge(numbers,s,mid);
		merge(numbers,mid+1,f);
		//Join both solution subproblems
		mergesorted(numbers,s,f,mid);
	}
	/**
	 * Merges the two list sorted parts into a single one from index s to f
	 * @param numbers to sort
	 * @param index where we begin the sorting process
	 * @param index where we finish the sorting process
	 * @param index where the sorted sub-lists are divided
	 */
	public void mergesorted(List<Integer> numbers, int s, int f, int mid) {
		int i = s;
		int j = mid+1;

		int size = f-s+1;
		int lasti = mid;
		int lastj = f;
		
		ArrayList<Integer> sorted = new ArrayList<Integer>();

		//Merge elements of the two subarrays
		
		while(i<=lasti && j<=lastj) {
			if (numbers.get(i) <= numbers.get(j)){
				sorted.add(numbers.get(i));
				i++;
			}else {
				sorted.add(numbers.get(j));
				j++;
			}
		}
		
		for(;i<=lasti;i++) sorted.add(numbers.get(i));
		for(;j<=lastj;j++) sorted.add(numbers.get(j));

		//Write the sorted answer into the base List
		
		for(int k=0; k<size;k++) {
			numbers.set(s+k, sorted.get(k));
		}
	}

}
