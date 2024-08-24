/**
 * Implementation of a linked list with recursive methods
 */
public class RecursiveIntegerLinkedList {

	//Number in this position of the list
	private int number;
	//Tells if this object represents an empty list
	private boolean empty;
	//Next element of the list. null if this is the last element of a list
	private RecursiveIntegerLinkedList next;
	//Last element of the list. It should only be used by the head of a list in the add method
	private RecursiveIntegerLinkedList last;
	
	/**
	 * Creates an empty list
	 */
	public RecursiveIntegerLinkedList () {
		next = last = null;
		empty = true;
	}
	/**
	 * Creates a list with the given value
	 * @param value to be added to the list
	 */
	public RecursiveIntegerLinkedList (int value) {
		number = value;
		next = null;
		last = this;
		empty = false;
	}
	/**
	 * Adds the value at the end of the current list
	 * @param value to be added at the end
	 */
	public void add (int value) {
		if(empty) {
			number = value;
			last = this;
			empty= false;
		} else {
			RecursiveIntegerLinkedList newLast = new RecursiveIntegerLinkedList(value);
			last.next = newLast;
			last = newLast;
		}
	}
	
	/**
	 * Returns the element in the current position of the list
	 * @param pos position in the list
	 * @return int the value present at the given position.
	 * @throws ArrayIndexOutOfBoundsException if the given position is invalid
	 */
	public int get (int pos) {
		if(empty) throw new ArrayIndexOutOfBoundsException("Get can not be used on an empty list");
		if(next == null) throw new ArrayIndexOutOfBoundsException("Invalid argument for get: "+pos);
		if(pos ==0) return number;
		return next.get(pos-1);
	}
	/**
	 * Calculates the size of the list
	 * @return int Size of the list
	 */
	public int size () {
		//Check base case
		if(empty) return 0;
		if(next==null) return 1;
		//Apply recursive call
		return 1+next.size();
	}
	
	/**
	 * Returns the index of the first appearance of the given value
	 * @param value to be searched
	 * @return int position of the list where the value is located
	 */
	public int indexOf(int value) {
		//Check base case
		if(empty) return -1;
		//Apply recursive call
		return firstAppearance(value,0);
		
	}
	/**
	 * Returns the index of the first appearance of the given value
	 * @param value to be searched
	 * @param index that is candidate for matching
	 * @return int position of the list where the value is located
	 */
	public int firstAppearance(int value, int index) {
		//Check base cases
		if (value==number) return index;
		if (next==null) return -1; 
		//Apply recursive call
		return next.firstAppearance(value,index+1);
	}
	
	/**
	 * Returns the index of the first appearance of the given value
	 * @param value to be searched
	 * @return int position of the list whare the value is located
	 */
	public int lastIndexOf(int value) {
		//Check base case
		if(empty) return -1;
		//Apply recursive call
		return lastAppearance(value,0);
	}
	
	public int lastAppearance(int value, int index) {
		//Apply recursive call and recursive call using ternary operator
		int forward_check = (next==null)? -1: next.lastAppearance(value,index+1);
		if (value==number) return (index > forward_check) ? index : forward_check;
		return forward_check;
	}
	
	/**
	 * Calculates the maximum value in the list
	 * @return int Maximum value
	 */
	public int max () {
		//Check base cases
		if(empty) return Integer.MIN_VALUE;
		if(next==null) return number;
		//Apply recursive call
		int nm = next.max();
		return (nm > number) ? nm : number;
	}
	
	/**
	 * Counts the number of even elements in the list
	 * @return int Number of values that are even
	 */
	public int countEven () {
		//Check base cases
		int value = (number%2 == 0 && !empty) ? 1 : 0;
		if(next==null) return value;
		//Apply recursive call
		return value + next.countEven();
	}
	
	/**
	 * Tells if the list is sorted
	 * @return boolean if the list is sorted by value. False otherwise
	 */
	public boolean isSorted () {
		//Check base case
		if(empty || next==null) return true;
		//Apply recursive call
		return number <= next.get(0) && next.isSorted();
	}
}
