import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RecursionExample {

	/**
	 * Main method for the recursion example. It has three parameters:
	 * args[0]: Path to the input file with numbers to process. It must be a text file with one number per line
	 * args[1]: Path to the output file with the numbers sorted
	 * args[2]: (Optional) Algorithm used to sort the numbers. It can be Bubble, Merge or Quick.
	 * If not provided, the default algorithm implemented in the static method sort of the class
	 * java.util.Collections is used
	 * @param args Array with the arguments described above 
	 * @throws Exception if the input file does not exist or it can not be read
	 * @throws Exception If the algorithm is not implmented
	 */
	public static void main(String[] args) throws Exception {
		//Read parameters
		String inFilename = args[0];
		String outFilename = args[1];
		String algorithm = null;
		if(args.length>2) algorithm = args[2];
		
		//Read input file
		RecursiveIntegerLinkedList numbersRL = new RecursiveIntegerLinkedList();
		ArrayList<Integer> numbers = new ArrayList<>();
		
		try (FileReader reader = new FileReader(inFilename);
			 BufferedReader in = new BufferedReader(reader)) { 
			String line = in.readLine();
			for (int i=0;line != null;i++) {
				try {
					int number = Integer.parseInt(line);
					numbersRL.add(number);
					numbers.add(number);		
				} catch (Exception e) {
					System.err.println("Can not read number from line "+i+" content: "+line);
					e.printStackTrace();
				}
				line = in.readLine();
			}
		}
		
		//Test methods on numbers recursive list
		System.out.println("ArrayList size: "+numbers.size()+" recursive list size: "+numbersRL.size());
		
		int pos = (new Random()).nextInt(numbers.size());
		int value = numbers.get(pos);
		
		System.out.println("First location of number "+value+". In ArrayList: "+numbers.indexOf(value) +" in recursive list: " + numbersRL.indexOf(value));
		System.out.println("Last location of number "+value+". In ArrayList: "+numbers.lastIndexOf(value) +" in recursive list: " + numbersRL.lastIndexOf(value));
		try {
			System.out.println("Element in position "+pos+". ArrayList: "+numbers.get(pos)+" recursive list "+numbersRL.get(pos));
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("The list has less than "+pos+" elements");
		}
		if(numbersRL.isSorted()) System.out.println("The list is sorted");
		else System.out.println("The list is not sorted");
		
		//Test divide and conquer methods on unsorted lists
		
		DivideAndConquerIntegerArrays divideConquer = new DivideAndConquerIntegerArrays(numbers);
		System.out.println("Number of even elements. Divide and conquer: "+divideConquer.countEven()+". recursive list "+numbersRL.countEven());
		System.out.println("Maximum. Divide and conquer: "+divideConquer.max()+". recursive list "+numbersRL.max());
		
		int posDC = divideConquer.searchUnsorted(value);
		if(posDC>=0) System.out.println("Position of value "+value+". Divide and conquer: "+posDC+". Value in numbers "+numbers.get(posDC));
		
		//Sort values
		long startTime;
		long endTime;
		if(algorithm==null) {
			startTime = System.currentTimeMillis();
			Collections.sort(numbers);
			endTime = System.currentTimeMillis();
		} else {
			String classname = algorithm+"NumbersArraySorter";
			NumbersArraySorter sortAlgorithm;
			try {
				Class<?> algorithmClass = Class.forName(classname);
				Constructor<?> emptyConstructor = algorithmClass.getConstructor();
				sortAlgorithm = (NumbersArraySorter)emptyConstructor.newInstance();
			} catch (Exception e) {
				throw new Exception("Invalid algorithm "+algorithm,e);
			}
			startTime = System.currentTimeMillis();
			sortAlgorithm.sort(numbers);
			endTime = System.currentTimeMillis();
		}
		
		//Output answer
		try (PrintStream out=new PrintStream(outFilename)) {
			for(int i=0;i<numbers.size();i++) {
				if(i>0 && (numbers.get(i)<numbers.get(i-1))) throw new RuntimeException("ERROR: Disorder detected at position "+i+" values: "+numbers.get(i-1)+","+numbers.get(i));
				out.println(numbers.get(i));
			}
		}
		
		System.out.println("Numbers sorted. Total time(milliseconds): "+(endTime-startTime));
		
		posDC = divideConquer.searchSorted(value);
		if(posDC>=0) System.out.println("Position of value "+value+". Divide and conquer: "+posDC+". Value in numbers "+numbers.get(posDC));
	}

}
