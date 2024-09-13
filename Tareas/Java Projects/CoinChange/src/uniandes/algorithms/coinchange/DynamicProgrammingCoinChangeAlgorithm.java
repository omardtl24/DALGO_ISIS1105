package uniandes.algorithms.coinchange;

public class DynamicProgrammingCoinChangeAlgorithm implements CoinChangeAlgorithm{
    /**
	 * Returns the solution of the CoinChange problem by a recursive approach
	 * @param totalValue to reach with the coins
     * @param denominations of the coins to choose
	 */
	@Override
	public int [] calculateOptimalChange(int totalValue, int [] denominations){
        //Find the Optimal Value
        int n = denominations.length;
        int [][] C = new int [totalValue+1][n+1];
        for(int i=0; i<=totalValue;i++){
            for(int j=0;j<=n;j++){
                if (i==0) C[i][j] = 0;
                else if (i>0 && j==0) C[i][j] = Integer.MAX_VALUE;
                else if (denominations[j-1]>i) C[i][j] = C[i][j-1];
                else C[i][j] = Math.min(C[i][j-1], 1+C[i-denominations[j-1]][j]);
            }
        }
        //Recover configuration
        int [] answer = new int[n];
        int i = totalValue;
        int j = n;
        while (i > 0 && j > 0) {
            if (C[i][j] == C[i][j-1]) {
                j--;
            } else {
                answer[j - 1]++;
                i -= denominations[j - 1];
            }
        }
        return answer;
    }
}
