package uniandes.algorithms.stategraphs;

import java.util.*;

public class IntersectionsDecisionProblem {
    private HashSet<Pair> streets;
    private String[] intersections;
    private HashMap<String, Integer> intersectionsMap;
    private PriorityQueue<boolean[]> agenda;
    private int t;
    private boolean[] finalState;

    public IntersectionsDecisionProblem(String[] in, HashSet<Pair> s){
        this.intersections = in;
        this.intersectionsMap = new HashMap<String, Integer>();

        this.streets = s;

        for (int i = 0; i < in.length; i++) {
            this.intersectionsMap.put(in[i], i);
        }

        this.agenda = new PriorityQueue<>(new Comparator<boolean[]>() {
            @Override
            public int compare(boolean[] s1, boolean[] s2) {
                return Integer.compare(countCover(s1),countCover(s2));
            }
        });
    }

    public boolean solveDecisionProblem(int t,int verbose){
        this.t = t;
        this.agenda.clear();
        this.agenda.add( new boolean[this.intersections.length]);
        boolean[] currentState;
        while(this.agenda.size()>0){
            currentState = this.agenda.poll();
            if(isSolution(currentState)){
                if(verbose==1) System.out.println(getSolution(currentState));
                this.finalState = currentState;
                return true;
            }

            List<boolean[]> successors = getSuccessors(currentState);

            for(boolean[] successor : successors){
                if (isViable(currentState,successor)){
                    this.agenda.add(successor);
                }
            }
        }
        return false;
    }

    public int countElements(boolean[] state){
        int c=0;
        for(int i=0;i<state.length;i++){
            if(state[i]==true) c+=1;
        }
        return c;
    }

    public int countCover(boolean[] state){
        int c=0;
        for(Pair street: this.streets){
            if(state[this.intersectionsMap.get(street.element1)] || state[this.intersectionsMap.get(street.element2)]) c+=1;
        }
        return c;
    }

    public boolean isSolution(boolean[] state){
    
        boolean answer = countElements(state) <= this.t;
        if(!answer) return answer;

        for(Pair street: this.streets){
            answer = answer && (state[this.intersectionsMap.get(street.element1)] || state[this.intersectionsMap.get(street.element2)]);
        }
        return answer;
    }

    public List<boolean[]> getSuccessors(boolean[] state){
        List<boolean[]> successors = new LinkedList<boolean[]>();
        int maxtrue = 0;
        for(int i=0;i<state.length;i++){
            if(maxtrue<i && state[i]) maxtrue=i;
        }
        for(int i=maxtrue+1;i<state.length;i++){
            boolean[] newState = Arrays.copyOf(state, state.length);
            newState[i] = true;
            successors.add(newState);
        }
        return successors;
    }

    public boolean isViable(boolean[] state, boolean[] succState){
        int i = 0;
        while(state[i]==succState[i] && i<succState.length) i++;

        String x = intersections[i];
        
        for(Pair street: this.streets){
            if(street.element1.equals(x) && !state[intersectionsMap.get(street.element2)]) return true;
            if(street.element2.equals(x) && !state[intersectionsMap.get(street.element1)]) return true;
        }

        return false;
    }

    public String getSolution(boolean[] state){
        HashSet<String> s = new HashSet<String>();
        for(int i=0; i<state.length;i++){
            if(state[i]) s.add(intersections[i]);
        }

        return "Intersections with camera: "+s.toString();
    }
}
