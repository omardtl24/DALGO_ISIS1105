public class implementacionIndiana {

    public int IndianaPath(int[][] A) {
        int R = A.length; 
        int C = A[0].length;
        int[][] I = new int [R][C];
        int inf = Integer.MIN_VALUE;
        for (int r = 0; r < R; r++){
            for (int i = 0; i < C; i++){
                if (r==0 && i==0){ I[r][i] = 0;}
                else if (A[r][i]==-1 || (r==0 && i>0)){I[r][i] = inf;}
                else if (r>0 && i==0){
                    I[r][i] = Math.max(I[r-1][i], I[r-1][i+1]) + A[r][i];
                }
                else if (r>0 && i==(C-1)){
                    I[r][i] = Math.max(I[r-1][i], I[r-1][i-1]) + A[r][i];
                }
                else if (r>0 && i>0 && i<(C-1)){
                    I[r][i] = Math.max(I[r-1][i], Math.max(I[r-1][i+1],I[r-1][i-1])) + A[r][i];
                }  
            }
        }
        int row = R/2;
        int max = I[row][0];
        for (int column = 1; column < C; column++){
            if (I[row][column] > max){
                max = I[row][column];
            }
        }
        return max;
    }

    public int MarionPath(int[][] A) {
        int R = A.length; 
        int C = A[0].length;
        int[][] M = new int [R][C];
        int inf = Integer.MIN_VALUE;
        for (int r = 0; r < R; r++){
            for (int i = 0; i < C; i++){
                if (r==0 && i==(C-1)){ M[r][i] = 0;}
                else if (A[r][i]==-1 || (r==0 && i<(C-1))){M[r][i] = inf;}
                else if (r>0 && i==0){
                    M[r][i] = Math.max(M[r-1][i], M[r-1][i+1]) + A[r][i];
                }
                else if (r>0 && i==(C-1)){
                    M[r][i] = Math.max(M[r-1][i], M[r-1][i-1]) + A[r][i];
                }
                else if (r>0 && i>0 && i<(C-1)){
                    M[r][i] = Math.max(M[r-1][i], Math.max(M[r-1][i+1],M[r-1][i-1])) + A[r][i];
                }  
            }
        }
        int row = R/2;
        int max = M[row][0];
        for (int column = 1; column < C; column++){
            if (M[row][column] > max){
                max = M[row][column];
            }
        }
        return max;
    }

    public int SalahPath(int[][] A) {
        int R = A.length; 
        int C = A[0].length;
        int[][] S = new int [R][C];
        int inf = Integer.MIN_VALUE;
        for (int r=R-1;r>=0;r--){
            for (int i=C-1; i>=0 ;i--){
                if (r==R-1 && i==(C/2)){ S[r][i] = 0;}
                else if (A[r][i]==-1 || (r==(R-1) && i!=(C/2))){S[r][i] = inf;}
                else if (r<(R-1) && i==0){
                    S[r][i] = Math.max(S[r+1][i], S[r+1][i+1]) + A[r][i];
                }
                else if (r<(R-1) && i==(C-1)){
                    S[r][i] = Math.max(S[r+1][i], S[r+1][i-1]) + A[r][i];
                }
                else if (r<(R-1) && i>0 && i<(C-1)){
                    S[r][i] = Math.max(S[r+1][i], Math.max(S[r+1][i+1],S[r+1][i-1])) + A[r][i];
                }
            }
        }
        int row = R/2;
        int max = M[row][0];
        for (int column = 1; column < C; column++){
            if (S[row][column] > max){
                max = S[row][column];
            }
        }
        return max;
    }

    
}