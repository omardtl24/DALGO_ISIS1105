import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

public class ProblemaP2BruteForce {

    private int maxFlow;
    private int maxMinFlow;
    private int id_celula;

    public class Pair {
        private final int first;
        private final int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public int getFirst(){
            return this.first;
        }

        public int getSecond(){
            return this.second;
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

        @Override
        public String toString(){
            return "("+first+","+second+")";
        }
    }

    public class Celula {
        public int id;
        public int x;
        public int y;
        public int type;
        public HashSet<String> aminoAcids = new HashSet<String>();

        public Celula(){}
    }

    public double getDistance(Celula c1, Celula c2){
        return Math.sqrt(Math.pow(c1.y - c2.y,2)+Math.pow(c1.x - c2.x,2));
    }

    public int getMaxThoughts(Celula c1, Celula c2){
        HashSet<String> set1 = c1.aminoAcids;
        HashSet<String> set2 = c2.aminoAcids;
        int s = 0;
        HashSet<String> smallerSet = set1.size() < set2.size() ? set1 : set2;
        HashSet<String> largerSet = set1.size() >= set2.size() ? set1 : set2;
        for (String element : smallerSet) {
            if (largerSet.contains(element)) s+=1;
        }
        return s;
    }

    public class EdmondsKarpMaxFlow {
        private Map<Pair, Integer> flows;
        private Map<Integer, Integer> minCutRelatedFlows;
        private int[] nodeFlows;
        private int maxFlow;
        private int source;
        private int sink;

        public Map<Integer, Integer> getMinCutRelatedFlows(){
            return minCutRelatedFlows;
        }
        
        public int getMaxFlow(){
            return maxFlow;
        }

        public int[] getNodeFlows(){
            return nodeFlows;
        }

        public void findMaximumFlow(GrafoCelulas graph , Integer toIgnore){

            flows = new HashMap<Pair, Integer>();
            minCutRelatedFlows = new HashMap<Integer, Integer>();
            maxFlow = 0;
            source = graph.getSuperSource();
            sink = graph.getSuperSink();

            nodeFlows = new int[graph.getNumNodes()];

            for (Pair edge : graph.capacidades.keySet()) {
                flows.put(edge, 0);
            }

            int[] path = BFSPath(graph, toIgnore);
            while (path[sink] != -1){
                int u, v, i = sink;
                int cfp = Integer.MAX_VALUE;

                while (i != source) {
                    u = path[i];
                    v = i;
                    cfp = Math.min(cfp, residualCapacity(u, v, graph));
                    i = u;
                }

                maxFlow += cfp;
                i = sink;
                while (i != source) {
                    u = path[i];
                    v = i;
                    
                    if (graph.containsEdge(u, v)) {
                        updateFlow(u, v, getFlow(u, v) + cfp);
                        nodeFlows[v]+=cfp;
                    } else {
                        updateFlow(v, u, getFlow(v, u) - cfp);
                        nodeFlows[v]-=cfp;
                    }
                    i = u;
                }

                path = BFSPath(graph, toIgnore);
                
            }

            for(Pair edge : flows.keySet()){
                int u = edge.getFirst();
                int v = edge.getSecond();

                if(path[u] != -1 && path[v] == -1){
                    if(!minCutRelatedFlows.containsKey(u)) minCutRelatedFlows.put(u,0);
                    if(!minCutRelatedFlows.containsKey(v)) minCutRelatedFlows.put(v,0);
                    minCutRelatedFlows.put(u,minCutRelatedFlows.get(u)+flows.get(edge));
                    minCutRelatedFlows.put(v,minCutRelatedFlows.get(v)+flows.get(edge));
                }
            }
            
        }

        public void updateFlow(int u, int v, int newValue){
            Pair edge = new Pair(u,v);
            flows.put(edge, newValue);
        }

        public int getFlow(int u, int v){
            Integer val = flows.get(new Pair(u, v));
            return val == null ? 0 : val; 
        }

        public int residualCapacity(int u, int v, GrafoCelulas graph){
            if (graph.containsEdge(u, v)) return graph.cost(u, v) - getFlow(u, v);
            if (graph.containsEdge(v, u)) return getFlow(v, u);
            return 0; 
        }

        public int[] BFSPath(GrafoCelulas graph , Integer toIgnore){
            int n = graph.getNumNodes();
            
            int[] parents = new int[n]; 
            int[] colors = new int[n];

           
            for (int i = 0; i < n; i++) {
                parents[i] = -1; 
            }

            Queue<Integer> q = new LinkedList<Integer>();
            q.add(source);
            colors[source] = 1; 
            parents[source] = source; 
            
            while (!q.isEmpty()) {
                int u = q.poll();
                if(u == sink) return parents;
                
                for (int v = 0; v < n; v++) {

                    if (v != toIgnore){
                        if (colors[v] == 0 && residualCapacity(u, v, graph) > 0) {
                            colors[v] = 1;
                            parents[v] = u; 
                            q.add(v); 
                        }
                    }
                }
                colors[u] = 2;
            }

            return parents;
        }
    }

    public class GrafoCelulas {
        //Atributos asociados al problema
        private List<Integer> iniciadoras = new ArrayList<>();
        private List<Integer> calculadoras = new ArrayList<>();
        private List<Integer> ejecutoras = new ArrayList<>();
        private HashMap<Integer, Celula> celulas = new HashMap<>();
        //Atributos propios de los grafos
        private int superSource = 0;
        private int superSink;
        private int numNodes;
        private HashMap<Pair , Integer> capacidades = new HashMap<>();

        public int cost(int u, int v){
            return capacidades.get(new Pair(u,v));
        }

        public boolean containsEdge(int u, int v){
            return capacidades.containsKey(new Pair(u,v));
        }

        public int getNumNodes(){
            return numNodes;
        }

        public int getSuperSource(){
            return superSource;
        }

        public int getSuperSink(){
            return superSink;
        }

        public List<Integer> getCalculadoras(){
            return calculadoras;
        }

        public Celula mapCelula(int id){
            return celulas.get(id);
        }

        public GrafoCelulas(int d, Celula[] c_s){
            //Clasificamos las celulas en los tres posibles conjuntos
            //Definimos un supernodo y un supersumidero
            superSource = 0;
            superSink = c_s.length + 1;
            //Inicializamos la estructura de adyacencia
            int map_id = 1;
            //Para cada celula
            for(Celula celula : c_s){
                //Mapeamos para la estructura de nodo
                celulas.put(map_id , celula);
                //Clasificamos las celulas en los tres grupos
                if(celula.type == 1) {
                    iniciadoras.add(map_id);
                    capacidades.put(new Pair(superSource, map_id),Integer.MAX_VALUE);
                }
                if(celula.type == 2){
                    calculadoras.add(map_id);
                } 
                if(celula.type == 3){
                    ejecutoras.add(map_id);
                    capacidades.put(new Pair(map_id,superSink),Integer.MAX_VALUE);
                }
                map_id+=1;
            }
            map_id = superSink+1;
            for (int i=0 ; i < calculadoras.size()-1; i++){
                for (int j=i+1 ; j < calculadoras.size() ; j++){
                    int id1 = calculadoras.get(i), id2 = calculadoras.get(j);
                    Celula c1 = celulas.get(id1), c2 = celulas.get(id2);
                    if(getDistance(c1, c2) <= d){
                        int capacidad = getMaxThoughts(c1, c2);
                        //Agregamos las capacidades
                        capacidades.put(new Pair(id1,id2),capacidad);
                        capacidades.put(new Pair(id2,map_id),capacidad);
                        capacidades.put(new Pair(map_id,id1),capacidad);
                        //Actualizamos contador de nodos
                        map_id+=1;
                    }
                }
            }

            for (int i=0 ; i < iniciadoras.size(); i++){
                for (int j=0 ; j < calculadoras.size() ; j++){
                    int id1 = iniciadoras.get(i), id2 = calculadoras.get(j);
                    Celula c1 = celulas.get(id1), c2 = celulas.get(id2);
                    if(getDistance(c1, c2) <= d){
                        int capacidad = getMaxThoughts(c1, c2);
                        //Agregamos las capacidades
                        capacidades.put(new Pair(id1,id2),capacidad);
                    }
                }
            }

            for (int i=0 ; i < calculadoras.size(); i++){
                for (int j=0 ; j < ejecutoras.size() ; j++){
                    int id1 = calculadoras.get(i), id2 = ejecutoras.get(j);
                    Celula c1 = celulas.get(id1), c2 = celulas.get(id2);
                    if(getDistance(c1, c2) <= d){
                        int capacidad = getMaxThoughts(c1, c2);
                        //Agregamos las capacidades
                        capacidades.put(new Pair(id1,id2),capacidad);
                    }
                }
            }
            numNodes = map_id;
        }

    }

    public void solveProblem(Celula[] celulas, int d){
        GrafoCelulas graph = new GrafoCelulas(d,celulas);
        EdmondsKarpMaxFlow edmondsKarpMaxFlow = new EdmondsKarpMaxFlow();
        edmondsKarpMaxFlow.findMaximumFlow(graph,-1);
        maxFlow = edmondsKarpMaxFlow.getMaxFlow();
        List<Integer> celulasCalculadoras = graph.getCalculadoras();
        int minCalculadoraId = celulasCalculadoras.get(0);
        edmondsKarpMaxFlow.findMaximumFlow(graph,minCalculadoraId);
        maxMinFlow = edmondsKarpMaxFlow.getMaxFlow();
        for(int celula_id : celulasCalculadoras){
            edmondsKarpMaxFlow.findMaximumFlow(graph,celula_id);
            if(edmondsKarpMaxFlow.getMaxFlow() <= maxMinFlow){
                maxMinFlow = edmondsKarpMaxFlow.getMaxFlow();
                minCalculadoraId = celula_id;
            }

        }
        Celula candidata = graph.mapCelula(minCalculadoraId);
        id_celula = candidata.id; 
    }

    public static void main(String[] args) {
        ProblemaP2BruteForce problemaP2 = new ProblemaP2BruteForce();
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
                    Celula celula = problemaP2.new Celula();
                    celula.id = Integer.parseInt(informacionCelulas[0]);
                    celula.x = Integer.parseInt(informacionCelulas[1]);
                    celula.y = Integer.parseInt(informacionCelulas[2]);
                    celula.type = Integer.parseInt(informacionCelulas[3]);
                    for(int a=4; a < informacionCelulas.length ; a++){
                        celula.aminoAcids.add(informacionCelulas[a]);
                    }
                    celulas[n] = celula;
                }
                try{
                    problemaP2.solveProblem(celulas,d);
                    System.out.printf("%d %d %d\n", problemaP2.id_celula,
                                                           problemaP2.maxFlow, 
                                                           problemaP2.maxMinFlow);
                }catch(Exception e){
                    e.printStackTrace();

            // Get and print the specific line number
            StackTraceElement element = e.getStackTrace()[0]; // Get the first element of the stack trace
            System.out.println("Exception thrown at: " + element.getClassName() + 
                               "." + element.getMethodName() + 
                               " (Line: " + element.getLineNumber() + ")");
                }

            }

        } catch (IOException e) {
            System.err.println("Error al leer entrada estándar: " + e.getMessage());
        }
        
    }
}
