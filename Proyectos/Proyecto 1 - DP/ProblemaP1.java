import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Desarrollo del Proyecto 1 - DALGO. 
 * @author Jose Miguel Alvear Zapata - 202010602
 * @author Omar David Toledo Leguizamón - 202424446
 *
 */

public class ProblemaP1 {
    /**
     * La clase {@code Resultado} es una clase auxiliar para retornar un entero y 
     * una matriz desde una misma función.
     * 
     * Solo cuenta con los getters de sus atributos
     *
     */
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
    /**
     * Retorna una copia exacta de una matriz.
     *
     * @param original Matriz que se desea copiar (Dimensiones R x C).
     * @return Copia exacta de la matriz de entrada (Dimensiones R x C).
     * 
     * Complejidad temporal: O(RC)
     * Complejidad espacial: O(RC)
     */
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
     /**
     * Retorna el caso maximo requerido por la recurrencia de dos personajes.
     * Simula el llamado recursivo que se usa en DP 
     *
     * @param P Estructura que memoiza los diferentes casos recursivos (Dimensiones R x C x C).
     * @param r Fila relativa en la que estan los personajes
     * @param i Columna en la que se encuentra el personaje 1
     * @param m Columna en la que se encuentra en personaje 2
     * @return Dependencia que cumpla con la condición de ser aquella que maximiza las reliquias.
     * 
     * Complejidad temporal: O(1)
     * Complejidad espacial: O(1)
     */
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
    
     /**
     * Retorna el caso maximo requerido por la recurrencia de tres personajes.
     * Simula el llamado recursivo que se usa en DP 
     *
     * @param P Estructura que memoiza los diferentes casos recursivos (Dimensiones R x C x C x C).
     * @param r Fila relativa en la que estan los personajes
     * @param i Columna en la que se encuentra el personaje 1
     * @param m Columna en la que se encuentra en personaje 2
     * @param s Columna en la que se encuentra en personaje 3
     * @return Dependencia que cumpla con la condición de ser aquella que maximiza las reliquias.
     * 
     * Complejidad temporal: O(1)
     * Complejidad espacial: O(1)
     */
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
    /**
     * Retorna la cantidad de reliquias que pueden recoger los tres personajes.
     * Si llegan a la misma casilla, solo uno puede recoger las reliquias 
     *
     * @param A Matriz que representa la cantidad de reliquias o maldiciones en cada posición (Dimensiones R x C).
     * @param r Fila relativa en la que estan los personajes
     * @param i Columna en la que se encuentra el personaje 1
     * @param m Columna en la que se encuentra en personaje 2
     * @param s Columna en la que se encuentra en personaje 3
     * @return Cantidad de reliquias que se pueden recoger con la especificación dada.
     * 
     * Complejidad temporal: O(1)
     * Complejidad espacial: O(1)
     */
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
    /**
     * Retorna la cantidad de reliquias que pueden recoger los dos personajes que inician de la parte superior.
     * Si llegan a la misma casilla, solo uno puede recoger las reliquias 
     *
     * @param A Matriz que representa la cantidad de reliquias o maldiciones en cada posición (Dimensiones R x C).
     * @param r Fila relativa en la que estan los personajes
     * @param i Columna en la que se encuentra el personaje 1
     * @param m Columna en la que se encuentra en personaje 2
     * @return Cantidad de reliquias que se pueden recoger con la especificación dada.
     * 
     * Complejidad temporal: O(1)
     * Complejidad espacial: O(1)
     */
    public int adderTwoCharactersNoSalah(int[][] A, int r, int i, int m){

        int indiValue = A[r][i];
        int marionValue = A[r][m];

        // Indiana y Marion están en la misma casilla
        if (i == m) return indiValue;

        //Indiana y Marion no están en la misma casilla
        return indiValue + marionValue;
    }
    /**
     * Retorna la cantidad de reliquias que pueden recoger los dos personajes que inician uno en la parte superior
     * y el otro en la parte inferior.
     * Si llegan a la misma casilla, solo uno puede recoger las reliquias 
     *
     * @param A Matriz que representa la cantidad de reliquias o maldiciones en cada posición (Dimensiones R x C).
     * @param r Fila relativa en la que estan los personajes
     * @param p1 Columna en la que se encuentra el personaje que inicio de la parte superior
     * @param s Columna en la que se encuentra en personaje que inicio de la parte inferior
     * @return Cantidad de reliquias que se pueden recoger con la especificación dada.
     * 
     * Complejidad temporal: O(1)
     * Complejidad espacial: O(1)
     */
    public int adderTwoCharactersWithSalah(int[][] A, int r, int p1, int s){
        int R = A.length;

        int p1Value = A[r][p1];
        int salahValue = A[R-1-r][s];

        // El otro personaje y Salah caen en la misma casilla en la mitad e la piramide
        if (r == R/2 && p1 == s) return salahValue;

        //El otro personaje y Salah no están en la misma casilla
        return p1Value + salahValue;
    }
    /**
     * Retorna la cantidad de reliquias maxima que pueden recoger Marion e Indiana
     * suponiendo que Salah muere en el trayecto
     *
     * @param A Matriz que representa la cantidad de reliquias o maldiciones en cada posición (Dimensiones R x C).
     * @return Cantidad de reliquias que pueden recoger Indiana y Marion si Salah muere.
     * 
     * Complejidad temporal: O(RC²)
     * Complejidad espacial: O(RC²)
     */
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
    /**
     * Retorna la cantidad de reliquias maxima que pueden recoger Marion y Salah
     * suponiendo que Indiana muere en el trayecto
     *
     * @param A Matriz que representa la cantidad de reliquias o maldiciones en cada posición (Dimensiones R x C).
     * @return Cantidad de reliquias que pueden recoger Salah y Marion si Indiana muere.
     * 
     * Complejidad temporal: O(RC²)
     * Complejidad espacial: O(RC²)
     */
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
    /**
     * Retorna la cantidad de reliquias maxima que pueden recoger Salah e Indiana
     * suponiendo que Marion muere en el trayecto
     *
     * @param A Matriz que representa la cantidad de reliquias o maldiciones en cada posición (Dimensiones R x C).
     * @return Cantidad de reliquias que pueden recoger Indiana y Salah si Marion muere.
     * 
     * Complejidad temporal: O(RC²)
     * Complejidad espacial: O(RC²)
     */

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
    
    /**
     * Retorna la cantidad de reliquias maxima que pueden recoger Indiana, Marion y Salah
     * suponiendo que los tres sobreviven
     *
     * @param A Matriz que representa la cantidad de reliquias o maldiciones en cada posición (Dimensiones R x C).
     * @return Cantidad de reliquias que pueden recoger Salah, Indiana y Marion.
     * 
     * Complejidad temporal: O(RC³)
     * Complejidad espacial: O(RC³)
     */
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
    /**
     * Retorna la cantidad de reliquias maxima suponiendo que solo Indiana sobrevive
     *
     * @param A Matriz que representa la cantidad de reliquias o maldiciones en cada posición (Dimensiones R x C).
     * @return Cantidad de reliquias que puede recoger Indiana si Salah y Marion mueren.
     * 
     * Complejidad temporal: O(RC)
     * Complejidad espacial: O(RC)
     */
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
    /**
     * Retorna la cantidad de reliquias maxima suponiendo que solo Marion sobrevive
     *
     * @param A Matriz que representa la cantidad de reliquias o maldiciones en cada posición (Dimensiones R x C).
     * @return Cantidad de reliquias que puede recoger Marion si Salah e Indiana mueren.
     * 
     * Complejidad temporal: O(RC)
     * Complejidad espacial: O(RC)
     */
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
    /**
     * Retorna la cantidad de reliquias maxima suponiendo que solo Salah sobrevive
     *
     * @param A Matriz que representa la cantidad de reliquias o maldiciones en cada posición (Dimensiones R x C).
     * @return Cantidad de reliquias que puede recoger Salah si Marion e Indiana mueren.
     * 
     * Complejidad temporal: O(RC)
     * Complejidad espacial: O(RC)
     */
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
    /**
     * Verifica si el camino maximo de cada personaje se llega a cruzar con el de otro (Si los caminos disjuntos)
     *
     * @param A Matriz que representa la cantidad de reliquias o maldiciones en cada posición (Dimensiones R x C).
     * @param Ai Matriz con el camino tomado por Indiana, donde estuvo las reliquias son ceros (Dimensiones R x C).
     * @param Am Matriz con el camino tomado por Marion, donde estuvo las reliquias son ceros (Dimensiones R x C).
     * @param As Matriz con el camino tomado por Salah, donde estuvo las reliquias son ceros (Dimensiones R x C).
     * @return Si los caminos son disjuntos o no.
     * 
     * Complejidad temporal: O(RC)
     * Complejidad espacial: O(1)
     */
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
    /**
     * Resuelve el problema de maximización de reliquias. Tiene los siguientes casos:
     *  - Si los tres personajes mueren retorna -1
     *  - Recupera el maximo de reliquias de los personajes que sobrevivan.
     *  - Evita calcular el problema mas pesado si identifica que los tres personajes sobreviven y
     *    sus caminos son disjuntos
     *
     * @param A Matriz que representa la cantidad de reliquias o maldiciones en cada posición (Dimensiones R x C).
     * @param Ai Matriz con el camino tomado por Indiana, donde estuvo las reliquias son ceros (Dimensiones R x C).
     * @param Am Matriz con el camino tomado por Marion, donde estuvo las reliquias son ceros (Dimensiones R x C).
     * @param As Matriz con el camino tomado por Salah, donde estuvo las reliquias son ceros (Dimensiones R x C).
     * @return Si los caminos son disjuntos o no.
     * 
     * Complejidad temporal: O(RC³)
     * Complejidad espacial: O(RC³)
     */
    public int solvePathProblems(int[][] A){
        //Calculo el camino independiente de cada personaje
        Resultado maxIndiana = IndianaPath(copyMatrix(A));
        Resultado maxMarion = MarionPath(copyMatrix(A));
        Resultado maxSalah = SalahPath(copyMatrix(A));
        //Determina si los personajes sobrevivieron o no
        Boolean iPath = maxIndiana.getMaximo() >= 0;
        Boolean mPath = maxMarion.getMaximo() >= 0;
        Boolean sPath = maxSalah.getMaximo() >= 0;
        //Verifica que los caminos son disjuntos
        Boolean disjoint = areDisjoint(A, maxIndiana.getMatriz(), 
                                          maxMarion.getMatriz(), 
                                          maxSalah.getMatriz());
        //Si los tres personajes mueren devuelve -1
        if(!iPath && !mPath && !sPath) return -1;
        //Si 1 personaje vive, retorna el maximo de su camino
        if(iPath && !mPath && !sPath) return maxIndiana.getMaximo();
        if(!iPath && mPath && !sPath) return maxMarion.getMaximo();
        if(!iPath && !mPath && sPath) return maxSalah.getMaximo();
        //Si 2 personajes viven, retorna el maximo de sus recorridos
        if(!iPath && mPath && sPath) return solvePathsTwoCharactersNoIndiana(A);
        if(iPath && !mPath && sPath) return solvePathsTwoCharactersNoMarion(A);
        if(iPath && mPath && !sPath) return solvePathsTwoCharactersNoSalah(A);
        //Si los tres sobreviven y los caminos son disjuntos, retorna la suma de los tres
        if(disjoint) return  maxIndiana.getMaximo() + maxMarion.getMaximo() +  maxSalah.getMaximo();
        //Si los tres sobreviven y los caminos no son disjuntos, calcula el resultado mas robusto
        //Este seria el peor caso que deriva en la complejidad O(RC³)
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
                    }catch(Exception e){

                    }
                }
            }

		} catch (IOException e) {
            System.err.println("Error al leer entrada estándar: " + e.getMessage());
        }
    }
        
    
}
