import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public static int[][] copyMatrix(int[][] original) {
        int rows = original.length;
        int cols = original[0].length;
        
        int[][] copy = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                copy[i][j] = original[i][j];
            }
        }
        
        return copy;
    }

    public int getPreviousMax(int[][][] P, int r, int i, int m) {
        int maxTreasure = P[r-1][i][m];
    
        int dimI = P[0].length; 
        int dimM = P[0][0].length;
    
        if (i > 0) {
            maxTreasure = Math.max(maxTreasure, P[r-1][i-1][m]);
        }
    
        if (i < dimI - 1) {
            maxTreasure = Math.max(maxTreasure, P[r-1][i+1][m]);
        }
    
        if (m > 0) {
            maxTreasure = Math.max(maxTreasure, P[r-1][i][m-1]);
        }
    
        if (m < dimM - 1) {
            maxTreasure = Math.max(maxTreasure, P[r-1][i][m+1]);
        }
    
        if (i > 0 && m > 0) {
            maxTreasure = Math.max(maxTreasure, P[r-1][i-1][m-1]);
        }

        if (i > 0 && m < dimM - 1) {
            maxTreasure = Math.max(maxTreasure, P[r-1][i-1][m+1]);
        }
    
        if (i < dimI - 1 && m > 0) {
            maxTreasure = Math.max(maxTreasure, P[r-1][i+1][m-1]);
        }
    
        if (i < dimI - 1 && m < dimM - 1) {
            maxTreasure = Math.max(maxTreasure, P[r-1][i+1][m+1]);
        }
    
        return maxTreasure;
    }
    

    public int getPreviousMax(int[][][][] P, int r, int i, int m, int s) {
        int maxTreasure = P[r-1][i][m][s];
    
        int dimI = P[0].length;
        int dimM = P[0][0].length;
        int dimS = P[0][0][0].length;
    
        if (s > 0) {
            maxTreasure = Math.max(maxTreasure, P[r-1][i][m][s-1]);
        }
        if (s < dimS - 1) {
            maxTreasure = Math.max(maxTreasure, P[r-1][i][m][s+1]);
        }
    
        if (i > 0) {
            maxTreasure = Math.max(maxTreasure, P[r-1][i-1][m][s]);
            if (m > 0) {
                maxTreasure = Math.max(maxTreasure, P[r-1][i-1][m-1][s]);
                if (s > 0) {
                    maxTreasure = Math.max(maxTreasure, P[r-1][i-1][m-1][s-1]);
                }
                if (s < dimS - 1) {
                    maxTreasure = Math.max(maxTreasure, P[r-1][i-1][m-1][s+1]);
                }
            }
            if (m < dimM - 1) {
                maxTreasure = Math.max(maxTreasure, P[r-1][i-1][m+1][s]);
                if (s > 0) {
                    maxTreasure = Math.max(maxTreasure, P[r-1][i-1][m+1][s-1]);
                }
                if (s < dimS - 1) {
                    maxTreasure = Math.max(maxTreasure, P[r-1][i-1][m+1][s+1]);
                }
            }
            if (s > 0) {
                maxTreasure = Math.max(maxTreasure, P[r-1][i-1][m][s-1]);
            }
            if (s < dimS - 1) {
                maxTreasure = Math.max(maxTreasure, P[r-1][i-1][m][s+1]);
            }
        }
    
        if (i < dimI - 1) {
            maxTreasure = Math.max(maxTreasure, P[r-1][i+1][m][s]);
            if (m > 0) {
                maxTreasure = Math.max(maxTreasure, P[r-1][i+1][m-1][s]);
                if (s > 0) {
                    maxTreasure = Math.max(maxTreasure, P[r-1][i+1][m-1][s-1]);
                }
                if (s < dimS - 1) {
                    maxTreasure = Math.max(maxTreasure, P[r-1][i+1][m-1][s+1]);
                }
            }
            if (m < dimM - 1) {
                maxTreasure = Math.max(maxTreasure, P[r-1][i+1][m+1][s]);
                if (s > 0) {
                    maxTreasure = Math.max(maxTreasure, P[r-1][i+1][m+1][s-1]);
                }
                if (s < dimS - 1) {
                    maxTreasure = Math.max(maxTreasure, P[r-1][i+1][m+1][s+1]);
                }
            }
            if (s > 0) {
                maxTreasure = Math.max(maxTreasure, P[r-1][i+1][m][s-1]);
            }
            if (s < dimS - 1) {
                maxTreasure = Math.max(maxTreasure, P[r-1][i+1][m][s+1]);
            }
        }
    
        if (m > 0) {
            maxTreasure = Math.max(maxTreasure, P[r-1][i][m-1][s]);
            if (s > 0) {
                maxTreasure = Math.max(maxTreasure, P[r-1][i][m-1][s-1]);
            }
            if (s < dimS - 1) {
                maxTreasure = Math.max(maxTreasure, P[r-1][i][m-1][s+1]);
            }
        }
        if (m < dimM - 1) {
            maxTreasure = Math.max(maxTreasure, P[r-1][i][m+1][s]);
            if (s > 0) {
                maxTreasure = Math.max(maxTreasure, P[r-1][i][m+1][s-1]);
            }
            if (s < dimS - 1) {
                maxTreasure = Math.max(maxTreasure, P[r-1][i][m+1][s+1]);
            }
        }
    
        return maxTreasure;
    }

    public int adderThreeCharacters(int[][] A, int r, int i, int m, int s) {
        int R = A.length;
    
        int indiValue = A[r][i];
        int marionValue = A[r][m];
        int salahValue = A[R-1-r][s];
    
        // Indiana y Marion están en la misma casilla antes de la mitad de la pirámide
        if (r < R/2 && i == m) return indiValue + salahValue;
    
        // Los tres personajes caen en la misma casilla en la mitad de la pirámide
        if (r == R/2 && i == m && i == s) return indiValue;
    
        // Indiana y Marion están en la misma casilla en la mitad de la pirámide, pero Salah no
        if (r == R/2 && i == m && i != s) return indiValue + salahValue;
    
        // Indiana y Salah caen en la misma casilla en la mitad de la pirámide, pero Marion no
        if (r == R/2 && i == s && i != m) return indiValue + marionValue;
    
        // Marion y Salah caen en la misma casilla en la mitad de la pirámide, pero Indiana no
        if (r == R/2 && m == s && i != m) return indiValue + salahValue;
    
        // Los tres estan en casillas diferentes
        return indiValue + marionValue + salahValue;
    }

    public int adderTwoCharactersNoSalah(int[][] A, int r, int i, int m){

        int indiValue = A[r][i];
        int marionValue = A[r][m];

        // Indiana y Marion están en la misma casilla
        if (i == m) return indiValue;

        //Indiana y Marion no están en la misma casilla
        return indiValue + marionValue;
    }

    public int adderTwoCharactersWithSalah(int[][] A, int r, int p1, int s){
        int R = A.length;

        int p1Value = A[r][p1];
        int salahValue = A[R-1-r][s];

        // El otro personaje y Salah caen en la misma casilla en la mitad e la piramide
        if (r == R/2 && p1 == s) return salahValue;

        //El otro personaje y Salah no están en la misma casilla
        return p1Value + salahValue;
    }

    public int solvePathsTwoCharactersNoSalah(int[][] A){
        int R = A.length;
        int C = A[0].length;

        int [][][] P = new int[R/2 + 1][C][C];

        int inf = Integer.MIN_VALUE;

        for(int r=0 ; r <= R/2 ; r++){
            for(int i=0 ; i < C ; i++){
                for(int m=0 ; m < C ; m++){
                    if(r==0 && i==0 && m==C-1){
                        P[r][i][m] = 0;
                    }
                    else if ((r==0 && (i>0 || m<C-1)) || A[r][i]==-1 || A[r][m]==-1){
                        P[r][i][m] = inf;
                    }
                    else{
                        P[r][i][m] = getPreviousMax(P,r,i,m) + adderTwoCharactersNoSalah(A,r,i,m); 
                    }
                }
            }
        }

        int result = P[R/2][0][0];
        int aux = inf;
        for(int i=0 ; i < C ; i++){
            for(int m=0 ; m < C ; m++){
                aux = P[R/2][i][m];
                if (aux > result){
                    result = aux;
                }
            }
        }

        return result;
    }

    public int solvePathsTwoCharactersNoIndiana(int[][] A){
        int R = A.length;
        int C = A[0].length;

        int [][][] P = new int[R/2 + 1][C][C];

        int inf = Integer.MIN_VALUE;

        for(int r=0 ; r <= R/2 ; r++){
            for(int m=0 ; m < C ; m++){
                for(int s=0 ; s < C ; s++){
                    if(r==0 && m==C-1 && s==C/2){
                        P[r][m][s] = 0;
                    }
                    else if ((r==0 && (m<C-1 || s!=C/2)) || A[r][m]==-1 || A[R-1-r][s]==-1){
                        P[r][m][s] = inf;
                    }
                    else{
                        P[r][m][s] = getPreviousMax(P,r,m,s) + adderTwoCharactersWithSalah(A,r,m,s); 
                    }
                }
            }
        }

        int result = P[R/2][0][0];
        int aux = inf;
        for(int m=0 ; m < C ; m++){
            for(int s=0 ; s < C ; s++){
                aux = P[R/2][m][s];
                if (aux > result){
                    result = aux;
                }
            }
        }

        return result;
    }

    public int solvePathsTwoCharactersNoMarion(int[][] A){
        int R = A.length;
        int C = A[0].length;

        int [][][] P = new int[R/2 + 1][C][C];

        int inf = Integer.MIN_VALUE;

        for(int r=0 ; r <= R/2 ; r++){
            for(int i=0 ; i < C ; i++){
                for(int s=0 ; s < C ; s++){
                    if(r==0 && i==0 && s==C/2){
                        P[r][i][s] = 0;
                    }
                    else if ((r==0 && (i>0 || s!=C/2)) || A[r][i]==-1 || A[R-1-r][s]==-1){
                        P[r][i][s] = inf;
                    }
                    else{
                        P[r][i][s] = getPreviousMax(P,r,i,s) + adderTwoCharactersWithSalah(A,r,i,s); 
                    }
                }
            }
        }

        int result = P[R/2][0][0];
        int aux = inf;
        for(int i=0 ; i < C ; i++){
            for(int s=0 ; s < C ; s++){
                aux = P[R/2][i][s];
                if (aux > result){
                    result = aux;
                }
            }
        }

        return result;
    }
    

    public int solvePathsThreeCharacters(int[][] A){
        int R = A.length;
        int C = A[0].length;

        int [][][][] P = new int[R/2 + 1][C][C][C];
        int inf = Integer.MIN_VALUE;

        for(int r=0 ; r <= R/2 ; r++){
            for(int i=0 ; i < C ; i++){
                for(int m=0 ; m < C ; m++){
                    for(int s=0 ; s < C ; s++){
                        if(r==0 && i==0 && m==C-1 && s==C/2){
                            P[r][i][m][s] = 0;
                        }
                        else if ((r==0 && (i>0 || m<C-1 || s!=C/2)) || A[r][i]==-1 || A[r][m]==-1 || A[R-1-r][s]==-1){
                            P[r][i][m][s] = inf;
                        }
                        else{
                            P[r][i][m][s] = getPreviousMax(P,r,i,m,s) + adderThreeCharacters(A,r,i,m,s); 
                        }
                    }
                }
            }
        }

        int result = P[R/2][0][0][0];
        int aux = inf;
        for(int i=0 ; i < C ; i++){
            for(int m=0 ; m < C ; m++){
                for(int s=0 ; s < C ; s++){
                    aux = P[R/2][i][m][s];
                    if (aux > result){
                        result = aux;
                    }
                }
            }
        }

        return result;

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

    public Boolean areDisjoint(int[][] A, int[][] Ai, int[][] Am, int[][] As){
        int R = A.length; 
        int C = A[0].length;
        int count;
        for(int i=0; i<R; i++){
            for(int j=0; j<C;j++){
                count = 0;
                if (A[i][j]!=0 && Ai[i][j]==0) count++;
                if (A[i][j]!=0 && Am[i][j]==0) count++;
                if (A[i][j]!=0 && As[i][j]==0) count++;
                if (count>1) return false;
            }
        }
        return true;
    }

    public int solvePathProblems(int[][] A){

        Resultado maxIndiana = IndianaPath(copyMatrix(A));
        Resultado maxMarion = MarionPath(copyMatrix(A));
        Resultado maxSalah = SalahPath(copyMatrix(A));

        Boolean iPath = maxIndiana.getMaximo() >= 0;
        Boolean mPath = maxMarion.getMaximo() >= 0;
        Boolean sPath = maxSalah.getMaximo() >= 0;

        Boolean disjoint = areDisjoint(A, maxIndiana.getMatriz(), 
                                          maxMarion.getMatriz(), 
                                          maxSalah.getMatriz());

        if(!iPath && !mPath && !sPath) return -1;

        if(iPath && !mPath && !sPath) return maxIndiana.getMaximo();
        if(!iPath && mPath && !sPath) return maxMarion.getMaximo();
        if(!iPath && !mPath && sPath) return maxSalah.getMaximo();
        

        if(!iPath && mPath && sPath) return solvePathsTwoCharactersNoIndiana(A);
        if(iPath && !mPath && sPath) return solvePathsTwoCharactersNoMarion(A);
        if(iPath && mPath && !sPath) return solvePathsTwoCharactersNoSalah(A);

        if(disjoint) return  maxIndiana.getMaximo() + maxMarion.getMaximo() +  maxSalah.getMaximo();
        return solvePathsThreeCharacters(A);
        
    }

    public static void main(String[] args) {
        ProblemaP1 problema1 = new ProblemaP1();
        int sol;
        try ( 
			InputStreamReader is= new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);
		) {
			String line = br.readLine();
			int casos = Integer.parseInt(line);
			
            for (int caso = 0; caso < casos; caso++) {
                line = br.readLine();
                if (line != null) {
                    String[] dimensiones = line.split(" ");
                    int r = Integer.parseInt(dimensiones[0]);
                    int c = Integer.parseInt(dimensiones[1]);

                    int[][] matriz = new int[r][c];
                    for (int i = 0; i < r; i++) {
                        line = br.readLine();
                        String[] elementos = line.split(" ");
                        for (int j = 0; j < c; j++) {
                            matriz[i][j] = Integer.parseInt(elementos[j]);
                        }
                    }

                    try{
                        sol = -1;
                        sol = problema1.solvePathProblems(matriz);
                        System.out.println(sol);
                        //line = br.readLine();
                    }catch(Exception e){

                    }
                }
            }

		} catch (IOException e) {
            System.err.println("Error al leer entrada estándar: " + e.getMessage());
        }
    }
        
    
}
