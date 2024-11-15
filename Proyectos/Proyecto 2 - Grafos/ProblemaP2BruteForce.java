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
import java.util.Iterator;

public class ProblemaP2 {

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
        public HashSet<Integer> aminoAcids = new HashSet<Integer>();

        public Celula(){}
    }

    public double getDistance(Celula c1, Celula c2){
        return Math.sqrt(Math.pow(c1.y - c2.y,2)+Math.pow(c1.x - c2.x,2));
    }

    public int getMaxThoughts(Celula c1, Celula c2){
        HashSet<Integer> set1 = c1.aminoAcids;
        HashSet<Integer> set2 = c2.aminoAcids;
        int s = 0;
        HashSet<Integer> smallerSet = set1.size() < set2.size() ? set1 : set2;
        HashSet<Integer> largerSet = set1.size() >= set2.size() ? set1 : set2;
        for (Integer element : smallerSet) {
            if (largerSet.contains(element)) s+=1;
        }
        return s;
    }

    public int[] BFSFlowPath(GrafoCelulas graph , Integer toIgnore,  Map<Pair, Integer> flows){
        int n = graph.getNumNodes();
        int source = graph.getSuperSource();
        int sink = graph.getSuperSink();
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
                    if (colors[v] == 0 && FlowOperations.residualCapacity(u, v, graph, flows) > 0) {
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


    public class FlowOperations{

        public static int getFlow(int u, int v, Map<Pair, Integer> flows){
            Pair pair = new ProblemaP2().new Pair(u, v);
            Integer val = flows.get(pair);
            return val == null ? 0 : val; 
        }

        public static int residualCapacity(int u, int v, GrafoCelulas graph ,  Map<Pair, Integer> flows){
            if (graph.containsEdge(u, v)) return graph.cost(u, v) - getFlow(u, v, flows);
            if (graph.containsEdge(v, u)) return getFlow(v, u, flows);
            return 0; 
        }

        public static HashMap<Integer, Integer> getNodeFlows(Map<Pair, Integer> flows){
            HashMap<Integer, Integer> nodeFlows = new HashMap<>();
            for(Pair edge : flows.keySet()){
                if(nodeFlows.containsKey(edge.getSecond())) nodeFlows.put(edge.getSecond(),flows.get(edge));
                else nodeFlows.put(edge.getSecond(),nodeFlows.get(edge.getSecond())+flows.get(edge));
            }
            return nodeFlows;
        }

        public static HashMap<Integer, Integer> getMinCutNodeFlows(Map<Pair, Integer> flows, int[] path){
            HashMap<Integer, Integer> minCutNodes = new HashMap<>();
            for(Pair edge : flows.keySet()){
                int u = edge.getFirst();
                int v = edge.getSecond();

                if(path[u] != -1 && path[v] == -1){
                    if(minCutNodes.containsKey(u)) minCutNodes.put(u,flows.get(edge));
                    else minCutNodes.put(u,minCutNodes.get(u)+flows.get(edge));

                    if(minCutNodes.containsKey(u)) minCutNodes.put(u,flows.get(edge));
                    else minCutNodes.put(u,minCutNodes.get(u)+flows.get(edge));
                }
            }
            return minCutNodes;
        }
    }

    public class DinicMaxFlow {
        public Map<Pair, Integer> flows;
        public Map<Integer, HashSet<Integer>> residualGraph;
        int maxFlow;

        public Map<Integer, HashSet<Integer>> getResidualGraph(){
            return residualGraph;
        }

        public int getMaxFlow(){
            return maxFlow;
        }

        public void findMaximumFlow(GrafoCelulas graph , Integer toIgnore){

            flows = new HashMap<Pair, Integer>();
            residualGraph = new HashMap<Integer , HashSet<Integer>>();
            maxFlow = 0;
            int sink = graph.getSuperSink();
            int source = graph.getSuperSource();

            for (Pair edge : graph.capacidades.keySet()) {
                flows.put(edge, 0);
                int first = edge.getFirst(), second =  edge.getSecond();
                
                if(first!=toIgnore && second!=toIgnore){

                    if(!residualGraph.containsKey(first)){
                        residualGraph.put(first, new HashSet<Integer>());
                    }
                    if(!residualGraph.containsKey(second)){
                        residualGraph.put(second, new HashSet<Integer>());
                    }

                    residualGraph.get(first).add(second);
                }    
            }

            int[] levels = BFSLevelGraph(graph, toIgnore);

            while(levels[sink]>0){
                int flow = DFSSendFlow(graph,source,levels,Integer.MAX_VALUE);
                
                while(flow>0){
                    maxFlow += flow;
                    flow = DFSSendFlow(graph,source,levels,Integer.MAX_VALUE);
                }

                levels = BFSLevelGraph(graph, toIgnore);
            }
        }

        public void updateFlow(int u, int v, int adder){
            Pair edge = new Pair(u,v);
            if(flows.containsKey(edge)) flows.put(edge, flows.get(edge)+adder);
            else flows.put(edge, adder);
        }

        public int DFSSendFlow(GrafoCelulas graph , int u, int[] levels, int flow){

            if(u == graph.getSuperSink()) return flow;

            for(Integer v : residualGraph.get(u)){
                if(levels[v] == levels[u]+1 && graph.containsEdge(u, v)){
                    int curr_flow = Math.min(flow,FlowOperations.residualCapacity(u,v,graph,flows));
                    int appliable_flow = DFSSendFlow(graph,v,levels,curr_flow);

                    if(appliable_flow > 0){
                        updateFlow(u, v, appliable_flow);
                        if(FlowOperations.residualCapacity(u,v,graph,flows)<=0) residualGraph.get(u).remove(v);
                        else residualGraph.get(u).add(v);
                        if(FlowOperations.residualCapacity(v,u,graph,flows)<=0) residualGraph.get(v).remove(u);
                        else residualGraph.get(v).add(u);
                        return appliable_flow;
                    }
                }
            }
            return 0;
        }

        public int[] BFSLevelGraph(GrafoCelulas graph, int toIgnore){
            int n = graph.getNumNodes();
            int source = graph.getSuperSource();
            int[] levels = new int[n]; 
            int[] colors = new int[n];
    
           
            for (int i = 0; i < n; i++) {
                levels[i] = -1; 
            }
    
            Queue<Integer> q = new LinkedList<Integer>();
            q.add(source);
            colors[source] = 1; 
            levels[source] = 0; 
            
            while (!q.isEmpty()) {
                int u = q.poll();
                
                for (Integer v : residualGraph.get(u)) {
                        if (colors[v] == 0 && FlowOperations.residualCapacity(u, v, graph, flows) > 0) {
                            colors[v] = 1;
                            levels[v] = levels[u]+1; 
                            q.add(v);
                        }
                }
                colors[u] = 2;
            }
    
            return levels;
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
        List<Integer> calculadoras = graph.getCalculadoras();
        DinicMaxFlow dinicMaxFlow = new DinicMaxFlow();
        dinicMaxFlow.findMaximumFlow(graph,-1);
        maxFlow = dinicMaxFlow.getMaxFlow();
        int candidate = -1;
        maxMinFlow = maxFlow;
        int newVal;
        
        for(int celula_id : calculadoras){
            dinicMaxFlow.findMaximumFlow(graph,celula_id);
            newVal = dinicMaxFlow.getMaxFlow();
            if(newVal <= maxMinFlow){
                maxMinFlow = newVal;
                candidate = celula_id;
            } 
        }
        id_celula = graph.celulas.get(candidate).id;
        
    }

    public static void main(String[] args) {
        ProblemaP2 problemaP2 = new ProblemaP2();
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
                    Celula celula = problemaP2.new Celula();
                    celula.id = Integer.parseInt(informacionCelulas[0]);
                    celula.x = Integer.parseInt(informacionCelulas[1]);
                    celula.y = Integer.parseInt(informacionCelulas[2]);
                    celula.type = Integer.parseInt(informacionCelulas[3]);
                    for(int a=4; a < informacionCelulas.length ; a++){
                        if(!chainMapper.containsKey(informacionCelulas[a])){
                            chainMapper.put(informacionCelulas[a],i);
                            i+=1;
                        }
                        celula.aminoAcids.add(chainMapper.get(informacionCelulas[a]));
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