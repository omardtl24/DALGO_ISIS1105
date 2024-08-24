import java.util.ArrayList;

public class DivideAndConquerIntegerArrays {

	// Array of numbers
	private ArrayList<Integer> numbers;

	/**
	 * Creates a new object with the given numbers
	 * @param numbers
	 */
	public DivideAndConquerIntegerArrays(ArrayList<Integer> numbers) {
		this.numbers = numbers;
	}
	
	/**
	 * Calculates the maximum value in this list
	 * @return int Maximum value in the list
	 */
	public int max() {
		//TODO: implementar en una linea utilizando el siguiente metodo
		return (numbers.size()==0) ? Integer.MIN_VALUE : max(0,numbers.size()-1);
	}
	
	/**
	 * Calculates the maximum value within the given indexes
	 * @param first index
	 * @param last index
	 * @return int maximum value within the given limits
	 */
	private int max (int first, int last) {
		//TODO: Implementar utilizando la tecnica de dividir y conquistar
		//Check base case
		if (last == first) return numbers.get(last);
		//Divide into two subproblems
		int mid = (first+last)/2;
		int firsthalf = max(first,mid);
		int secondhalf = max(mid+1,last);
		//Join solutions by comparing and choosing largest
		return (firsthalf > secondhalf)? firsthalf : secondhalf;
	}
	/**
	 * Finds a position in this numbers where the given value is located
	 * @param value to search
	 * @return int position where the given value is located
	 */
	public int searchUnsorted (int value) {
		//TODO: implementar en una linea utilizando el siguiente metodo
		return searchUnsorted(value,0,numbers.size()-1);
	}
	/**
	 * Finds a position in this numbers between the given limits where the given value is located
	 * @param value to search
	 * @param first index
	 * @param last index
	 * @return int position between first and last where the given value is located
	 */
	private int searchUnsorted (int value, int first, int last) {
		//TODO: Implementar utilizando la tecnica de dividir y conquistar
		//Check case base
		if (last < first) return -1;
		//Divide into two subproblems
		int mid = (first+last)/2;
		if (value==numbers.get(mid)) return mid;
		int firsthalf = searchUnsorted(value,first,mid-1);
		int secondhalf = searchUnsorted(value,mid+1,last);
		//Join solutions by checking if any of it found the requested value
		return (firsthalf != -1)? firsthalf : secondhalf;
	}
	/**
	 * Finds a position in this numbers where the given value is located
	 * PRE: the numbers are sorted
	 * @param value to search
	 * @return int position where the given value is located
	 */
	public int searchSorted (int value) {
		//TODO: implementar en una linea utilizando el siguiente metodo
		return searchSorted(value,0,numbers.size()-1);
	}
	/**
	 * Finds a position in this numbers between the given limits where the given value is located
	 * PRE: the numbers are sorted
	 * @param value to search
	 * @param first index
	 * @param last index
	 * @return int position between first and last where the given value is located
	 */
	private int searchSorted (int value, int first, int last) {
		//TODO: Implementar busqueda binaria
		//Check case base
		if(last < first) return -1;
		//Split into two subproblems
		int mid = (first+last)/2;
		if (value < numbers.get(mid)) return searchSorted(value,first,mid-1);
		if (value > numbers.get(mid)) return searchSorted(value,mid+1,last);
		//If value matches return the solution
		return mid;
		
	}
	
	/**
	 * Counts the number of even elements in this list of numbers
	 * @return int Number of values that are even
	 */
	public int countEven() {
		//TODO: implementar en una linea utilizando el siguiente metodo
		return countEven(0,numbers.size()-1);
	}
	
	/**
	 * Counts the number of even elements in this list of numbers between the given limits
	 * @param first index
	 * @param last index
	 * @return int count of even values within the given limits
	 */
	private int countEven (int first, int last) {
		//TODO: Implementar utilizando la tecnica de dividir y conquistar
		//Check case base
		if(last < first) return 0;
		//Split into two subproblems
		int mid = (first+last)/2;
		int value = (numbers.get(mid)%2==0) ? 1: 0;
		int firsthalf = countEven (first, mid-1);
		int secondhalf = countEven (mid+1, last);
		//Join the two solutions
		return firsthalf+secondhalf+value;		
	}
}
