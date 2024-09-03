package uniandes.algorithms.coinchange;

import java.util.Arrays;

public class RecursiveCoinChangeAlgorithm implements CoinChangeAlgorithm{
    /**
	 * Returns the solution of the CoinChange problem by a recursive approach
	 * @param totalValue to reach with the coins
     * @param denominations of the coins to choose
	 */
	@Override
	public int [] calculateOptimalChange(int totalValue, int [] denominations){
        int [] answer = new int[denominations.length];
        return C(totalValue, denominations.length, denominations, answer);
    }

    public int getValue(int[] solution){
        if(solution==null) return Integer.MAX_VALUE;
        int sum = 0;
        for(int i=0; i<solution.length;i++){
            sum += solution[i];
        }
        return sum;

    }

    public int[] C(int i, int j, int[] a ,int[] partial_answer){
        if (i==0) return partial_answer;
        if (i>0 && j==0) return null;
        if (j>0 && i>0 && a[j-1] > i) return C(i,j-1,a,partial_answer);
        
        int [] partial_answer_copy =  Arrays.copyOf(partial_answer, partial_answer.length);
        
        partial_answer_copy[j-1] += 1;

        int[] case1 = C(i-a[j-1],j,a,partial_answer_copy);
        int[] case2 = C(i,j-1,a,partial_answer);

        if (getValue(case1) > getValue(case2)) return case2;
        else return case1;

    }
    
}
