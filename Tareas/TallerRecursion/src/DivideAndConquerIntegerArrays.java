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
		return Integer.MIN_VALUE;
	}
	
	/**
	 * Calculates the maximum value within the given indexes
	 * @param first index
	 * @param last index
	 * @return int maximum value within the given limits
	 */
	private int max (int first, int last) {
		//TODO: Implementar utilizando la tecnica de dividir y conquistar
		return Integer.MIN_VALUE;
	}
	/**
	 * Finds a position in this numbers where the given value is located
	 * @param value to search
	 * @return int position where the given value is located
	 */
	public int searchUnsorted (int value) {
		//TODO: implementar en una linea utilizando el siguiente metodo
		return -1;
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
		return -1;
	}
	/**
	 * Finds a position in this numbers where the given value is located
	 * PRE: the numbers are sorted
	 * @param value to search
	 * @return int position where the given value is located
	 */
	public int searchSorted (int value) {
		//TODO: implementar en una linea utilizando el siguiente metodo
		return -1;
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
		return -1;
	}
	
	/**
	 * Counts the number of even elements in this list of numbers
	 * @return int Number of values that are even
	 */
	public int countEven() {
		//TODO: implementar en una linea utilizando el siguiente metodo
		return 0;
	}
	
	/**
	 * Counts the number of even elements in this list of numbers between the given limits
	 * @param first index
	 * @param last index
	 * @return int count of even values within the given limits
	 */
	private int countEven (int first, int last) {
		//TODO: Implementar utilizando la tecnica de dividir y conquistar
		return 0;
	}
}
