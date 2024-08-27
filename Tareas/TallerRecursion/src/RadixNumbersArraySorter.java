import java.util.List;
import java.util.Collections;

/**
 * Implements the bubble sort algorithm for number arrays
 */
public class RadixNumbersArraySorter implements NumbersArraySorter {
	/**
	 * Sorts the given numbers in ascending order using count sort algorithm
	 * @param numbers to sort
	 */
	@Override
	public void sort(List<Integer> numbers) {
		
		int maxval = Collections.max(numbers);
		int exp = 1;

		while(maxval / exp > 0){
			digitCountingSort(numbers, exp);
			exp*=10;
		}
		
	}

	public void digitCountingSort(List<Integer> numbers, int digit_exp){
		int[] count = new int[10];
		int[] sorted = new int[numbers.size()];
		int index;
		int value;
		
		for(int i=0; i < numbers.size(); i++) {
			index = numbers.get(i) / digit_exp;
			count[index] ++;
		}
		
		for(int i=1; i < 10; i++) {
			count[i] += count[i-1];
		}
		
		for(int i=numbers.size()-1; i >= 0 ; i--) {
			value = numbers.get(i);
			index = value/10;
			count[index] --;
			sorted[count[index]] = value;
		}
		
		for(int i=0; i < numbers.size(); i++) {
			numbers.set(i, sorted[i]);
		}
	}

}
