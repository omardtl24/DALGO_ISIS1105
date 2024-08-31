import java.util.List;

/**
 * Implements the quick sort algorithm for number arrays
 */
public class QuickNumbersArraySorter implements NumbersArraySorter {
	@Override
	public void sort(List<Integer> numbers) {
		quickSort (numbers,0,numbers.size()-1);

	}

	private void quickSort(List<Integer> numbers, int first, int last) {
		if(first >= last) return;
		int p = organize(numbers,first,last);
		quickSort(numbers, first, p-1);
		quickSort(numbers, p+1, last);
		
	}

	private int organize(List<Integer> numbers, int first, int last) {
		int pivot = numbers.get(last);
		int i = first;
		for(int j=first;j<=last;j++) {
			int n = numbers.get(j);
			if(n<=pivot) {
				numbers.set(j, numbers.get(i));
				numbers.set(i, n);
				i++;
			}
		}
		return i-1;
	}

}
