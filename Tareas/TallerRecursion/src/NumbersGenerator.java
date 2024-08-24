import java.util.Random;

public class NumbersGenerator {

	public static void main(String[] args) {
		int numValues = Integer.parseInt(args[0]);
		int minValue = Integer.parseInt(args[1]);
		int maxValue = Integer.parseInt(args[2]);
		Random random = new Random();
		for(int i=0;i<numValues;i++) {
			int number = random.nextInt(maxValue-minValue+1)+minValue;
			System.out.println(number);
		}
	}

}
