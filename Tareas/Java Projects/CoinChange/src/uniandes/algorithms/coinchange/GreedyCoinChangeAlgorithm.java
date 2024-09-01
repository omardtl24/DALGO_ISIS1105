package uniandes.algorithms.coinchange;

public class GreedyCoinChangeAlgorithm implements CoinChangeAlgorithm{
    /**
	 * Returns the solution of the CoinChange problem by a greedy approach
     * The approach consists in choosing the coin with the highest feasible value
	 * @param totalValue to reach with the coins
     * @param denominations of the coins to choose
	 */
	@Override
	public int [] calculateOptimalChange(int totalValue, int [] denominations){
        int n = denominations.length;
        int [] answer = new int[n];
        boolean feasible = true;
        int max_index = 0;
        //Apply the process while there is a feseable value and totalValue is not 0
        while(feasible && totalValue>0){
            //Look for a feasible max value
            feasible = false;
            for (int i=0; i<n; i++){
                if(denominations[i]<=totalValue && denominations[i]>=denominations[max_index]){
                    max_index = i;
                    feasible = true;
                }
            }
            //If feasible value was found, update the answer
            if (feasible){
                answer[max_index] = totalValue / max_index;
                totalValue -= (denominations[max_index] * totalValue / max_index);
            }
            max_index = 0;
        }
        return answer;
    }
}
