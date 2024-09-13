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
        while(n>0){
            answer[n-1] = totalValue / denominations[n-1];
            totalValue =  totalValue % denominations[n-1];
            n--;
        }
        return answer;
    }
}
