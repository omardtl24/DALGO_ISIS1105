import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class ProblemaP2 {

    public class Pair {
    private final int first;
    private final int second;

    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return first == pair.first && second == pair.second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}

    public class Celula {
        public int id;
        public int x;
        public int y;
        public int type;
        public HashSet<String> aminoAcids = new HashSet<String>();
    }

    public class GrafoCelulas {
        //Atributos asociados al problema
        private Celula[] celulas;
        private HashSet<Integer> iniciadoras;
        private HashSet<Integer> calculadoras;
        private HashSet<Integer> ejecutoras;
        //Atributos propios de los grafos
        private int numNodes;
        private List<Integer>[] ejes;


        public GrafoCelulas(int d, Celula[] c_s){
            //Clasificamos las celulas en los tres posibles conjuntos
            celulas = c_s;
            for(Celula celula : c_s){
                if(celula.id == 1) iniciadoras.add(celula.id);
                if(celula.id == 2) calculadoras.add(celula.id);
                if(celula.id == 3) ejecutoras.add(celula.id);
            }
            //Se añaden 2 nodos, uno para la superfuente y el supersumidero
            numNodes = c_s.length + 2; 
        }

    }

    public static void main(String[] args) {
        ProblemaP2 problemaP2 = new ProblemaP2();
        int d , c;
        Celula[] celulas;
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
                for(int n = 0; n < c; n++){
                    line = br.readLine();
                    String [] informacionCelulas = line.split(" ");
                    celulas[n].id = Integer.parseInt(informacionCelulas[0]);
                    celulas[n].x = Integer.parseInt(informacionCelulas[1]);
                    celulas[n].y = Integer.parseInt(informacionCelulas[2]);
                    for(int a=3; a < informacionCelulas.length ; a++){
                        celulas[n].aminoAcids.add(informacionCelulas[a]);
                    }
                }

            }

        } catch (IOException e) {
            System.err.println("Error al leer entrada estándar: " + e.getMessage());
        }
        
    }
}
