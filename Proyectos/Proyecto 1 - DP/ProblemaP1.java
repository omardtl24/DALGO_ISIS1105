import java.util.ArrayList;

public class ProblemaP1 {

    public class Resultado {
        private int maximo;
        private int[][] matriz;
    
        // Constructor
        public Resultado(int entero, int[][] matriz) {
            this.maximo = entero;
            this.matriz = matriz;
        }
    
        // Getters
        public int getMaximo() {
            return maximo;
        }
    
        public int[][] getMatriz() {
            return matriz;
        }
    }
    

    public Resultado IndianaPath(int[][] A) {
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
        int c = 0;
        for (int column = 1; column < C; column++){
            if (I[row][column] > max){
                max = I[row][column];
                c = column;
            }
        }
        if(max < 0) return new Resultado(inf,A);
        //Backtrack si se encontro una solución para reconstruir el camino
        ArrayList<int[]> moves = new ArrayList<int[]>();
        int k;
        while(row>0){
            k = A[row][c];
            A[row][c] = 0;

            if(I[row][c] != I[row-1][c]+k){
                moves.clear();
                if(c > 0) moves.add(new int[]{row-1,c-1});
                if(c < C-1) moves.add(new int[]{row-1,c+1});
                for( int[] move: moves){
                    if (I[move[0]][move[1]] + k == I[row][c]) {
                        c = move[1];
                        break;
                    }
                }
            }
            row--;
        }
        return new Resultado(max, A);
    }

    public Resultado MarionPath(int[][] A) {
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
        int c = 0;
        for (int column = 1; column < C; column++){
            if (M[row][column] > max){
                max = M[row][column];
                c = column;
            }
        }
        if(max < 0) return new Resultado(inf,A);
        //Backtrack si se encontro una solución para reconstruir el camino
        ArrayList<int[]> moves = new ArrayList<int[]>();
        int k;
        while(row>0){
            k = A[row][c];
            A[row][c] = 0;

            if(M[row][c] != M[row-1][c]+k){
                moves.clear();
                if(c > 0) moves.add(new int[]{row-1,c-1});
                if(c < C-1) moves.add(new int[]{row-1,c+1});
                for( int[] move: moves){
                    if (M[move[0]][move[1]] + k == M[row][c]) {
                        c = move[1];
                        break;
                    }
                }
            }
            row--;
        }
        return new Resultado(max, A);
    }

    public Resultado SalahPath(int[][] A) {
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
        int max = S[row][0];
        int c = 0;
        for (int column = 1; column < C; column++){
            if (S[row][column] > max){
                max = S[row][column];
                c = column;
            }
        }

        if(max < 0) return new Resultado(inf,A);

        ArrayList<int[]> moves = new ArrayList<int[]>();
        int k;
        while(row < R-1){
            k = A[row][c];
            A[row][c] = 0;

            if(S[row][c] != S[row+1][c]+k){
                moves.clear();
                if(c > 0) moves.add(new int[]{row+1,c-1});
                if(c < C-1) moves.add(new int[]{row+1,c+1});
                for( int[] move: moves){
                    if (S[move[0]][move[1]] + k == S[row][c]) {
                        c = move[1];
                        break;
                    }
                }
            }
            row++;
        }
        return new Resultado(max, A);
        
    }

    public static void main(String[] args) {
        ProblemaP1 problema1 = new ProblemaP1();

        int[][] A = {
            {0, -1, -1, -1, 0},
            {2, 3, -1, 5, 4},
            {-1, 5, 15, 2, 7},
            {-1, 1, -1, 2, -1},
            {-1, -1, 0, -1, -1}
        };

        int maxIndiana = problema1.IndianaPath(A).getMaximo();
        int maxMarion = problema1.MarionPath(A).getMaximo();
        int maxSalah = problema1.SalahPath(A).getMaximo();

        System.out.println("Max Indiana Path: " + maxIndiana);
        System.out.println("Max Marion Path: " + maxMarion);
        System.out.println("Max Salah Path: " + maxSalah);
    }
    
}
