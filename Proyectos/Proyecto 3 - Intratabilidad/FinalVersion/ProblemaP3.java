import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Omar David Toledo Leguizamón -- 202424446 
 * @author Juan David Camargo -- 202220493
 */

public class ProblemaP3{

    public Integer[] partition;
    public List<Group> sets;
    public int[] degrees;

    public class Group{
        public int maxSize;
        public Set<Celula> celulas;

        public Group(Celula inicial){
            celulas = new HashSet<Celula>();
            celulas.add(inicial);
            maxSize = degrees[inicial.id-1]+1;
        }

        public boolean isFull(){
            return celulas.size() == maxSize;
        }

        public void addCell(Celula celula){
            celulas.add(celula);
        }
    }

    public class Celula {
        public int id;
        public int x;
        public int y;
        public BitArray aminoAcids = new BitArray();
        public Celula(){}

        public void addAminoAcid(int aminoAcidIndex) {
            if (aminoAcidIndex < 100) {
                aminoAcids.setBit(aminoAcidIndex);
            }
        }

        public boolean hasCommonAminoAcids(Celula other) {
            return this.aminoAcids.hasCommonBit(other.aminoAcids);
        }
    }

    public class BitArray {
        private long part1; // Representa los bits 0-63
        private long part2; // Representa los bits 64-99
    
        // Constructor: inicializa todos los bits en 0
        public BitArray() {
            this.part1 = 0L;
            this.part2 = 0L;
        }
    
        /**
         * Activa un bit en el índice dado.
         * @param index Índice del bit a activar (0-99).
         * @throws IllegalArgumentException Si el índice no está en el rango válido.
         */
        public void setBit(int index) {

            if (index < 64) {
                part1 |= (1L << index); // Activa el bit en part1 (bits 0-63)
            } else {
                part2 |= (1L << (index - 64)); // Activa el bit en part2 (bits 64-99)
            }
        }
    
        /**
         * Compara este BitArray con otro para verificar si tienen al menos un bit en común.
         * @param other Otro BitArray para comparar.
         * @return true si tienen al menos un bit en común, false en caso contrario.
         */
        public boolean hasCommonBit(BitArray other) {
            return (this.part1 & other.part1) != 0 || (this.part2 & other.part2) != 0;
        }
    }

    /**Gets euclidian distance between a pair of cells
     * 
     * @param c1 Cell 1 to get distance to cell 2
     * @param c2 Cell 2 to get distance to cell 1
     * @return Euclidian distance between cell 1 and cell 2
     */
    public double getDistance(Celula c1, Celula c2){
        return Math.sqrt(Math.pow(c1.y - c2.y,2)+Math.pow(c1.x - c2.x,2));
    }

    /**Checks if a pair of cells have aminoacids in common
     * 
     * @param c1 Cell 1 to check if it has aminoacids in common with cell 2
     * @param c2 Cell 2 to check if it has aminoacids in common with cell 1
     * @return True or False (Does the cells have aminoacids in common?)
     */
    public boolean canConnect(Celula c1, Celula c2) {
        return c1.hasCommonAminoAcids(c2);
    }    

    /** Inits the information necessary for the graph and stores it in the class attributes.
     * It gets the cell number of connections its involved (Or degree) and inits the partition
     * structures
     * 
     * @param celulas Cells that are going to be grouped by the criteria stablished
     * @param d Maximum distance from which a pair of cells can send data between them
     */
    public void initGraph(Celula[] celulas, int d) {
        int n = celulas.length;
        this.degrees = new int[n];
        this.partition = new Integer[n];
        this.sets = new ArrayList<>();;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (areConnected(celulas[i], celulas[j],d)){
                    this.degrees[i]++;
                    this.degrees[j]++;
                }
            }
        }
    }
    /**Checks if a pair of cells can send messages between them. This is criteria is resolved as
     * their distance is on the range defined by d and if the pair has common aminoacids
     * 
     * @param c1 Cell 1 we want to check if it can send messages to cell 2
     * @param c2 Cell 2 we want to check if it can send messages to cell 1
     * @param d Maximum distance from which a pair of cells can send data between them
     * @return True or false (Does the cells can send messages between them?)
     */
    public boolean areConnected(Celula c1, Celula c2, int d){
        if (!(Math.abs(c1.x - c2.x) > d || Math.abs(c1.y - c2.y) > d)){
            if(getDistance(c1, c2)<=d) return canConnect(c1, c2);
        }
        return false;
    }

    /**Finds a subgroup where a cell can join. This process relies in verify if the cell is 
     * connected to all the cells in a group. If this is possible, the group id is returned, otherwise,
     * the result is null (Which means it is c¿going to be created a new group)
     * 
     * @param celula Represents the cell that we want to find its subptye group
     * @param d Maximum distance from which a pair of cells can send data between them
     * @return Id of the group that the cell can be part of, or null if any group is feasible for it
     */
    public Integer findClique(Celula celula, int d) {
        for (int i = 0; i < this.sets.size(); i++) {
            Group set = this.sets.get(i);
            if(!set.isFull()){
                boolean fullyConnected = true;
                Set<Celula> cells = set.celulas;
                for (Celula cell : cells) {
                    if (!areConnected(cell, celula, d)) {
                        fullyConnected = false;
                        break;
                    }
                }
                if (fullyConnected) return i;
            }
        }
        return null;
    }
    /**
     * Prints the partition gotten as problem solution. This structure is sequential as the problem
     * output design requires this format
     */
    public void printAnswer(){
        int n = this.partition.length;
        for(int i=0;i<n;i++){
            System.out.printf("%d %d\n", i+1, this.partition[i]+1);
        }
    }
    /** Finds an approximation to the minimum groups of cells of same subtype
     * 
     * @param celulas Array with the cells that are involving the graph design
     * @param d Minimum distance that lets to cells to send messages
     */
    public void solveProblem(Celula[] celulas ,int d){
        //Build graph and init sets
        initGraph(celulas, d);
        //Sort celulas by degree
        Arrays.sort(celulas, Comparator.comparingInt(celula -> this.degrees[celula.id-1]));
        //Iterate through all cells and build cliques
        int n = celulas.length;
        for(int i=0; i<n;i++){
            Celula curCelula = celulas[i];
            Integer clique = findClique(curCelula,d);
            if(clique==null){
                //If it does not fits any clique, create a clique for the element
                Group newClique = new Group(curCelula);
                clique = this.sets.size();
                this.sets.add(newClique);
            }else{
                //If it fits any clique, add element to corresponding clique
                this.sets.get(clique).addCell(curCelula);
                
            }
            this.partition[curCelula.id-1] = clique;
        }
    }
    /**Prints the trace of an exception gotten during execution. This method will not be included
     * in the final versoon callables as it is only for debuigging
     * 
     * @param e Exception trown by program
     */
    public void printException(Exception e){
        e.printStackTrace();
        StackTraceElement element = e.getStackTrace()[0];
        System.out.println("Exception thrown at: " + element.getClassName() + 
                            "." + element.getMethodName() + 
                            " (Line: " + element.getLineNumber() + ")");
    }
    /** Main problem executable code. It receives inputs and generates outputs using the format
     * given in the problem document
     * 
     * @param args No args expected
     */
    public static void main(String[] args) {
        ProblemaP3 problemaP3 = new ProblemaP3();
        int d , c;
        Celula[] celulas;
        HashMap<String, Integer> chainMapper;
        try ( 
			InputStreamReader is= new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);
		){
            String line = br.readLine();
			int casos = Integer.parseInt(line);
            for(int caso = 0; caso < casos; caso++) {
                line = br.readLine();
                String[] informacionCaso = line.split(" ");
                c = Integer.parseInt(informacionCaso[0]);
                d = Integer.parseInt(informacionCaso[1]);
                celulas = new Celula[c];
                chainMapper = new HashMap<>();
                int i = 0;
                for(int n = 0; n < c; n++){
                    line = br.readLine();
                    String [] informacionCelulas = line.split(" ");
                    Celula celula = problemaP3.new Celula();
                    celula.id = Integer.parseInt(informacionCelulas[0]);
                    celula.x = Integer.parseInt(informacionCelulas[1]);
                    celula.y = Integer.parseInt(informacionCelulas[2]);
                    for(int a=3; a < informacionCelulas.length ; a++){
                        if(!chainMapper.containsKey(informacionCelulas[a])){
                            chainMapper.put(informacionCelulas[a],i);
                            i+=1;
                        }
                        celula.addAminoAcid(chainMapper.get(informacionCelulas[a]));
                    }
                    celulas[n] = celula;
                }
                try{
                    problemaP3.solveProblem(celulas,d);
                    problemaP3.printAnswer();
                    
                }catch(Exception e){
                    //problemaP3.printException(e);
                }
            }
        } catch (IOException e) {
            //System.err.println("Error al leer entrada estándar: " + e.getMessage());
        }
        
    }
}