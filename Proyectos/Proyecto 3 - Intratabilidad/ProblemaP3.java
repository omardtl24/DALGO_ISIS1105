import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ProblemaP3{

    public Integer[] partition;
    public LinkedList<LinkedList<Celula>> sets;
    public int[] degrees;

    public class Celula {
        public int id;
        public int x;
        public int y;
        public BitSet aminoAcids = new BitSet(100);
        public Celula(){}

        public void addAminoAcid(int aminoAcidIndex) {
            if (aminoAcidIndex < 100) {
                aminoAcids.set(aminoAcidIndex);
            }
        }
    }

    public double getDistance(Celula c1, Celula c2){
        return Math.sqrt(Math.pow(c1.y - c2.y,2)+Math.pow(c1.x - c2.x,2));
    }

    public boolean canConnect(Celula c1, Celula c2) {
        return c1.aminoAcids.intersects(c2.aminoAcids);
    }    

    public void initGraph(Celula[] celulas, int d) {
        int n = celulas.length;
        this.degrees = new int[n];
        this.partition = new Integer[n];
        this.sets = new LinkedList<LinkedList<Celula>>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (areConnected(celulas[i], celulas[j],d)){
                    this.degrees[celulas[i].id-1]++;
                    this.degrees[celulas[j].id-1]++;
                }
            }
        }
    }

    public boolean areConnected(Celula c1, Celula c2, int d){
        if(getDistance(c1, c2)<=d) return canConnect(c1, c2);
        return false;
    }

    public Integer findClique(Celula celula,int d){
        int i = 0;
        for(List<Celula> set : this.sets){
            boolean fullyConnected = true;
            for(Celula cell: set){
                fullyConnected = fullyConnected && areConnected(cell, celula, d);
            }
            if(fullyConnected) return i;
            i+=1;
        }
        return null;
    }

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
                LinkedList<Celula> newClique = new LinkedList<Celula>();
                newClique.add(curCelula);
                clique = this.sets.size();
                this.sets.add(newClique);
            }else{
                //If it fits any clique, add element to corresponding clique
                this.sets.get(clique).add(curCelula);
                
            }
            this.partition[curCelula.id-1] = clique;
        }
    }

    public void printException(Exception e){
        e.printStackTrace();
        StackTraceElement element = e.getStackTrace()[0];
        System.out.println("Exception thrown at: " + element.getClassName() + 
                            "." + element.getMethodName() + 
                            " (Line: " + element.getLineNumber() + ")");
    }

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
                    problemaP3.printException(e);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer entrada estándar: " + e.getMessage());
        }
        
    }
}